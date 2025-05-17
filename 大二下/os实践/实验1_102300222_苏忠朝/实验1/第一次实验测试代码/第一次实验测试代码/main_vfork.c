#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(int argc, char **argv) {
    pid_t pid;
    int n = 1; // 从1开始

    pid = vfork();  // 使用 vfork 创建子进程

    if (pid == -1) {
        perror("vfork failed");
        exit(1); // 创建子进程失败
    } else if (pid == 0) {
        // 子进程
        while (1) {
            printf("子进程修改 n 为: %d\n", n);
            n++; // 子进程修改 n
            sleep(1);

            if (n > 10) {
                printf("子进程退出，n=%d\n", n);
                _exit(0); // 子进程退出
            }
        }
    } else {
        // 父进程
        while (1) {
            printf("父进程修改 n 为: %d\n", n);
            n++; // 父进程修改 n
            sleep(1);

            if (n > 10) {
                printf("父进程退出，n=%d\n", n);
                wait(NULL); // 父进程等待子进程结束
                exit(0); // 父进程退出
            }
        }
    }

    return 0;
}