#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h> // 包含 wait 函数

int main(int argc, char **argv) {
    pid_t pid;
    pid = fork();  // 创建进程

    int n = 1; // 从1开始

    if (pid == 0) {
        // 子进程
        while (1) {
            printf("我是子进程: %d\n", n);
            sleep(1);
            n++;
            if (n > 10) {
                printf("子进程退出，n=%d\n", n);
                _exit(0); // 子进程退出
            }
        }
    } else if (pid > 0) {
        // 父进程
        while (1) {
            printf("我是父进程: %d\n", n);
            sleep(1);
            n++;
            if (n > 10) {
                printf("父进程退出，n=%d\n", n);
                break; // 父进程退出循环
            }
        }

        // 等待子进程结束
        wait(NULL); // 父进程等待子进程退出
        printf("父进程已等待子进程结束\n");
    } else {
        perror("fork failed");
        exit(1); // 创建子进程失败
    }

    return 0;
}