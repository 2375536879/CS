#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <float.h>

#define MAX_QUEUES 100


//时间片设置
int time_quantum[100];
void time_init(int time_quantum[]) {
    for (int i = 0; i < 100; i++) {
        time_quantum[i] = i + 1; // 第0级队列时间片为1，第1级为2，依此类推
    }
}

// 进程
typedef struct {
    int pid;         // 进程ID
    float arrive;    // 到达时间
    float run;       // 运行时间
    float start;     // 开始时间
    float finish;    // 完成时间
    float tat;       // 周转时间
    float wtat;      // 带权周转时间
    int queue_level; // 当前所在队列
    float executed;  // 已执行时间
} Process;

// 输入进程信息
void input_processes(Process proc[], int n) {
    for (int i = 0; i < n; i++) {
        printf("输入进程 %d 的到达时间和运行时间(空格分隔): ", i + 1);
        scanf("%f %f", &proc[i].arrive, &proc[i].run);
        proc[i].pid = i + 1;
        proc[i].queue_level = 0; // 初始都在最高优先级队列
        proc[i].start = 0;
        proc[i].finish = 0;
        proc[i].executed = 0;   // 初始化已执行时间为0
    }
}

// 多级反馈队列调度算法
void mfq(Process proc[], int n) {
    bool* completed = (bool*)calloc(n, sizeof(bool));
    float current_time = 0;
    int count = 0;

    while (count < n) {
        bool found = false;

        // 按照优先级从高到低遍历每个队列
        for (int q = 0; q < MAX_QUEUES; q++) {
            int next = -1;
            float earliest = FLT_MAX;

            // 找出当前队列中最早到达且未完成的进程
            for (int i = 0; i < n; i++) {
                if (!completed[i] && proc[i].queue_level == q && proc[i].arrive <= current_time) {
                    if (proc[i].arrive < earliest) {
                        earliest = proc[i].arrive;
                        next = i;
                    }
                }
            }

            if (next != -1) {
                found = true;
                float quantum = time_quantum[q]; // 当前队列的时间片
                float remaining = proc[next].run - proc[next].executed;

                // 如果是第一次运行该进程
                if (proc[next].start == 0 && proc[next].finish == 0) {
                    proc[next].start = (current_time > proc[next].arrive)
                        ? current_time : proc[next].arrive;
                }

                // 计算本次执行时间
                float exec_time = (remaining <= quantum) ? remaining : quantum;
                current_time += exec_time;
                proc[next].finish = current_time;

                // 更新已执行时间
                proc[next].executed += exec_time;

                // 如果还有剩余时间，降级
                if (proc[next].executed < proc[next].run && q < MAX_QUEUES - 1) {
                    proc[next].queue_level += 1;
                }
                else if (proc[next].executed >= proc[next].run) {
                    // 进程完成
                    proc[next].tat = proc[next].finish - proc[next].arrive;
                    proc[next].wtat = proc[next].tat / proc[next].run;
                    completed[next] = true;
                    count++;
                }

                break; // 本轮只执行一个进程
            }
        }

        // 如果当前没有可运行的进程，跳转到下一个进程到达时间
        if (!found) {
            float min_arrive = FLT_MAX;
            for (int i = 0; i < n; i++) {
                if (!completed[i] && proc[i].arrive < min_arrive) {
                    min_arrive = proc[i].arrive;
                }
            }
            current_time = min_arrive;
        }
    }

    free(completed);
}

// 输出结果
void print_results(Process proc[], int n) {
    printf("\n进程ID | 到达时间 | 运行时间 | 开始时间 | 完成时间 | 周转时间 | 带权周转时间\n");
    printf("-------+----------+----------+----------+----------+----------+------------\n");

    float total_tat = 0, total_wtat = 0;
    for (int i = 0; i < n; i++) {
        printf("%6d | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %10.2f\n",
            proc[i].pid,
            proc[i].arrive,
            proc[i].run,
            proc[i].start,
            proc[i].finish,
            proc[i].tat,
            proc[i].wtat);

        total_tat += proc[i].tat;
        total_wtat += proc[i].wtat;
    }

    printf("\n平均周转时间: %.2f\n", total_tat / n);
    printf("平均带权周转时间: %.2f\n", total_wtat / n);
}


int main() {
    int n;
    printf("输入进程数: ");
    scanf("%d", &n);

    Process* proc = (Process*)malloc(n * sizeof(Process));
    if (!proc) {
        printf("内存分配失败。\n");
        return 1;
    }

    time_init(time_quantum); 
    input_processes(proc, n); 
    mfq(proc, n); 
    print_results(proc, n); 

    free(proc);
    return 0;
}