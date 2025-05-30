#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_FRAMES 100  // 最大内存块数
#define INPUT_BUFFER 1024  // 输入缓冲区大小

typedef struct {
    int pageNumber;     // 存储的页面号
    int timestamp;      // 加载时间戳（用于FIFO）
} MemoryBlock;

// 函数声明
void fifoPageReplacement(int pages[], int n, int frameCount);

int main() {
    char input[INPUT_BUFFER];
    int *pages = NULL;
    int frameCount;
    int pageCount = 0;

    // 获取页面走向输入
    printf("请输入页面走向（空格分隔的数字，回车结束）:\n");
    if (fgets(input, INPUT_BUFFER, stdin) == NULL) {
        printf("错误：未检测到输入！\n");
        return 1;
    }

    // 解析输入并转换为整数数组
    char *token = strtok(input, " \n");
    while (token != NULL) {
        // 验证输入是否为有效数字
        for (int i = 0; token[i] != '\0'; i++) {
            if (!isdigit(token[i]) && !(i == 0 && token[i] == '-')) {
                printf("错误：包含无效字符 '%c'！\n", token[i]);
                free(pages);
                return 1;
            }
        }

        // 动态扩展数组
        int *temp = realloc(pages, (pageCount + 1) * sizeof(int));
        if (temp == NULL) {
            printf("内存分配失败！\n");
            free(pages);
            return 1;
        }
        pages = temp;
        pages[pageCount++] = atoi(token);
        token = strtok(NULL, " \n");
    }

    if (pageCount == 0) {
        printf("错误：未输入有效页面序列！\n");
        free(pages);
        return 1;
    }

    // 获取内存块数量
    printf("请输入内存块数量（1-%d）:\n", MAX_FRAMES);
    if (scanf("%d", &frameCount) != 1 || frameCount < 1 || frameCount > MAX_FRAMES) {
        printf("错误：无效的内存块数量！\n");
        free(pages);
        return 1;
    }

    // 清除输入缓冲区
    while (getchar() != '\n');

    // 执行页面置换算法
    fifoPageReplacement(pages, pageCount, frameCount);

    // 释放动态分配的内存
    free(pages);
    return 0;
}

void fifoPageReplacement(int pages[], int n, int frameCount) {
    MemoryBlock frames[MAX_FRAMES]; // 物理内存块
    int pageFaults = 0;            // 缺页计数器
    int replacements = 0;          // 页面置换计数器
    int currentTime = 0;           // 时间戳计数器

    // 初始化内存块
    for (int i = 0; i < frameCount; i++) {
        frames[i].pageNumber = -1;  // -1表示空块
        frames[i].timestamp = 0;
    }

    printf("\n页面走向: ");
    for (int i = 0; i < n; i++) printf("%d ", pages[i]);
    printf("\n内存块数: %d\n", frameCount);

    // 模拟页面访问过程
    for (int i = 0; i < n; i++) {
        int currentPage = pages[i];
        int found = 0;

        // 检查页面是否已在内存中
        for (int j = 0; j < frameCount; j++) {
            if (frames[j].pageNumber == currentPage) {
                found = 1;
                break;
            }
        }

        // 显示访问结果
        if (found) {
            printf("\n命中页面 %d", currentPage);
        } else {
            pageFaults++;
            printf("\n缺页! 加载页面 %d", currentPage);
            
            // 查找空块或需要替换的块
            int oldestIndex = 0;
            int emptyIndex = -1;
            
            for (int j = 0; j < frameCount; j++) {
                if (frames[j].pageNumber == -1) {
                    emptyIndex = j;
                    break;
                }
                if (frames[j].timestamp < frames[oldestIndex].timestamp) {
                    oldestIndex = j;
                }
            }

            // 执行页面置换
            if (emptyIndex != -1) {
                frames[emptyIndex].pageNumber = currentPage;
                frames[emptyIndex].timestamp = ++currentTime;
            } else {
                replacements++;
                frames[oldestIndex].pageNumber = currentPage;
                frames[oldestIndex].timestamp = ++currentTime;
            }
        }

        // 统一显示当前内存状态
        printf("\n当前内存状态: ");
        for (int j = 0; j < frameCount; j++) {
            if (frames[j].pageNumber == -1) {
                printf("[空] ");
            } else {
                printf("[%d] ", frames[j].pageNumber);
            }
        }
    }

    // 计算并输出结果
    float faultRate = (float)pageFaults / n * 100;
    printf("\n\n实验结果:");
    printf("\n总访问次数: %d", n);
    printf("\n缺页次数: %d", pageFaults);
    printf("\n缺页率: %.2f%%", faultRate);
    printf("\n置换次数: %d\n", replacements);
}