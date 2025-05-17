#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t pid = fork(); // 创建子进程

    if (pid == -1) {
        perror("fork failed");
        exit(1); // 创建子进程失败，使用 exit() 退出
    } else if (pid == 0) {
        // 子进程
        printf("Hello from the child process!\n");
        _exit(0); // 子进程使用 _exit() 退出
    } else {
        // 父进程
        printf("Parent process is waiting for the child process to finish...\n");
        wait(NULL); // 父进程等待子进程结束
        printf("Child process has finished.\n");
        exit(0); // 父进程使用 exit() 退出
    }

    return 0; // 这一行实际上不会被执行，因为父进程已经调用了 exit()
}