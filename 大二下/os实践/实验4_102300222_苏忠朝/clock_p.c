#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_FRAMES 100      // 最大内存块数
#define INPUT_BUFFER 1024   // 输入缓冲区大小

/* 内存块结构体（对应算法中的页面队列） */
typedef struct {
    int pageNumber;         // 存储的页面号（-1表示空块）
    int reference;          // 访问位（R位，0/1）
} MemoryBlock;

void clockPageReplacement(int pages[], int n, int frameCount);

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
    clockPageReplacement(pages, pageCount, frameCount);

    // 释放动态分配的内存
    free(pages);
    return 0;
}

void clockPageReplacement(int pages[], int n, int frameCount) {
    MemoryBlock frames[MAX_FRAMES]; // 物理内存块（页面队列）
    int pageFaults = 0;            // 缺页计数器
    int replacements = 0;          // 页面置换计数器
    int hand = 0;                  // 时钟指针（指向当前检查的页面）

    /* 1. 初始化阶段 */
    for (int i = 0; i < frameCount; i++) {
        frames[i].pageNumber = -1;  // 初始化所有内存块为空
        frames[i].reference = 0;    // 所有页面的R位初始化为0
    }

    printf("\n页面走向: ");
    for (int i = 0; i < n; i++) printf("%d ", pages[i]);
    printf("\n内存块数: %d\n", frameCount);

    // 模拟页面访问过程
    for (int i = 0; i < n; i++) {
        int currentPage = pages[i];
        int found = 0;

        /* 页面存在性检查（命中检测） */
        for (int j = 0; j < frameCount; j++) {
            if (frames[j].pageNumber == currentPage) {
                found = 1;  //知识点1：补充代码处1

                /* 2. 页面命中处理：设置R位为1 */
                frames[j].reference = 1;  //知识点1：补充代码处2
                break;
            }
        }

        // 显示访问结果
        if (found) {
            printf("\n命中页面 %d", currentPage);
        } else {
            pageFaults++;
            printf("\n缺页! 加载页面 %d", currentPage);
            
            /* 3. 页面缺失处理逻辑 */
            int emptyIndex = -1;
            int victimIndex = -1;
            int scanned = 0;
            int fullPass = 0;

            while (scanned < frameCount) {
                // 遇到已扫描过的帧且未找到替换页面时，重置扫描计数器
                if (fullPass && scanned >= frameCount) {
                    scanned = 0;
                    fullPass = 0;
                }

                // 检查当前时钟指针指向的页面
                if (frames[hand].pageNumber == -1) {  // 空块检查
                    emptyIndex = hand;
                    break;
                }

                /* 4. 检查R位并处理 */
                if (frames[hand].reference == 0) {    // R位为0，找到替换候选
                    victimIndex = hand;
                    break;
                } else {                              // R位为1的处理
                    frames[hand].reference = 0;       // 清除R位（给予第二次机会）
                    /* 5. 移动时钟指针（循环队列） */
                    hand =(hand+1) % frameCount; //知识点2：补充代码处1
                    scanned++;
                    fullPass = 1;
                }
            }

            // 处理全扫描后未找到替换页面的情况（第二次强制扫描）
            if (victimIndex == -1 && emptyIndex == -1) {
                scanned = 0;
                while (scanned < frameCount) {
                    if (frames[hand].reference == 0) {  // 第二次扫描保证找到替换页面
                        victimIndex = hand;
                        break;
                    } else {
                        frames[hand].reference = 0;     // 强制清除R位
                        /* 继续移动时钟指针 */
                        hand = (hand + 1) % frameCount;
                        scanned++;
                    }
                }
            }

            // 执行页面置换或加载
            if (emptyIndex != -1) {
                // 加载到空块
                frames[emptyIndex].pageNumber = currentPage;
                frames[emptyIndex].reference = 1;  // 新页面R位设为1
                printf(" → 加载到空块[%d]", emptyIndex);
                /* 更新指针位置 */
                hand =(emptyIndex+1)%frameCount; //知识点3：补充代码处1
            } else {
                // 执行页面置换
                replacements++;
                printf(" → 置换页面 %d", frames[victimIndex].pageNumber);
                frames[victimIndex].pageNumber = currentPage;
                frames[victimIndex].reference = 1;  // 新页面R位设为1
                /* 更新指针位置 */
                hand =(victimIndex +1)%frameCount; //知识点3：补充代码处2
            }
        }

        // 显示当前内存状态
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