#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <float.h>

#define MAX_QUEUES 100


//ʱ��Ƭ����
int time_quantum[100];
void time_init(int time_quantum[]) {
    for (int i = 0; i < 100; i++) {
        time_quantum[i] = i + 1; // ��0������ʱ��ƬΪ1����1��Ϊ2����������
    }
}

// ����
typedef struct {
    int pid;         // ����ID
    float arrive;    // ����ʱ��
    float run;       // ����ʱ��
    float start;     // ��ʼʱ��
    float finish;    // ���ʱ��
    float tat;       // ��תʱ��
    float wtat;      // ��Ȩ��תʱ��
    int queue_level; // ��ǰ���ڶ���
    float executed;  // ��ִ��ʱ��
} Process;

// ���������Ϣ
void input_processes(Process proc[], int n) {
    for (int i = 0; i < n; i++) {
        printf("������� %d �ĵ���ʱ�������ʱ��(�ո�ָ�): ", i + 1);
        scanf("%f %f", &proc[i].arrive, &proc[i].run);
        proc[i].pid = i + 1;
        proc[i].queue_level = 0; // ��ʼ����������ȼ�����
        proc[i].start = 0;
        proc[i].finish = 0;
        proc[i].executed = 0;   // ��ʼ����ִ��ʱ��Ϊ0
    }
}

// �༶�������е����㷨
void mfq(Process proc[], int n) {
    bool* completed = (bool*)calloc(n, sizeof(bool));
    float current_time = 0;
    int count = 0;

    while (count < n) {
        bool found = false;

        // �������ȼ��Ӹߵ��ͱ���ÿ������
        for (int q = 0; q < MAX_QUEUES; q++) {
            int next = -1;
            float earliest = FLT_MAX;

            // �ҳ���ǰ���������絽����δ��ɵĽ���
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
                float quantum = time_quantum[q]; // ��ǰ���е�ʱ��Ƭ
                float remaining = proc[next].run - proc[next].executed;

                // ����ǵ�һ�����иý���
                if (proc[next].start == 0 && proc[next].finish == 0) {
                    proc[next].start = (current_time > proc[next].arrive)
                        ? current_time : proc[next].arrive;
                }

                // ���㱾��ִ��ʱ��
                float exec_time = (remaining <= quantum) ? remaining : quantum;
                current_time += exec_time;
                proc[next].finish = current_time;

                // ������ִ��ʱ��
                proc[next].executed += exec_time;

                // �������ʣ��ʱ�䣬����
                if (proc[next].executed < proc[next].run && q < MAX_QUEUES - 1) {
                    proc[next].queue_level += 1;
                }
                else if (proc[next].executed >= proc[next].run) {
                    // �������
                    proc[next].tat = proc[next].finish - proc[next].arrive;
                    proc[next].wtat = proc[next].tat / proc[next].run;
                    completed[next] = true;
                    count++;
                }

                break; // ����ִֻ��һ������
            }
        }

        // �����ǰû�п����еĽ��̣���ת����һ�����̵���ʱ��
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

// ������
void print_results(Process proc[], int n) {
    printf("\n����ID | ����ʱ�� | ����ʱ�� | ��ʼʱ�� | ���ʱ�� | ��תʱ�� | ��Ȩ��תʱ��\n");
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

    printf("\nƽ����תʱ��: %.2f\n", total_tat / n);
    printf("ƽ����Ȩ��תʱ��: %.2f\n", total_wtat / n);
}


int main() {
    int n;
    printf("���������: ");
    scanf("%d", &n);

    Process* proc = (Process*)malloc(n * sizeof(Process));
    if (!proc) {
        printf("�ڴ����ʧ�ܡ�\n");
        return 1;
    }

    time_init(time_quantum); 
    input_processes(proc, n); 
    mfq(proc, n); 
    print_results(proc, n); 

    free(proc);
    return 0;
}