#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <ctype.h>

#define VIRTUAL_PAGES 32
#define PHYSICAL_PAGES 16
#define PAGE_SIZE 1024

int main() {
    char va_hex[10];  // 扩展缓冲区以容纳0x前缀
    int N;
    int page_table[VIRTUAL_PAGES];
    memset(page_table, -1, sizeof(page_table)); // 初始化页表为-1（未分配）

    // 输入虚拟地址
    printf("请输入虚拟地址（16进制，例如0x1A3F）：");
    if (scanf("%9s", va_hex) != 1) {  // 读取包括0x前缀的输入
        printf("输入错误！\n");
        return 1;
    }

    // 验证并转换虚拟地址
    uint32_t va = 0;
    char *endptr;
    va = strtoul(va_hex, &endptr, 16);
    
    // 输入有效性检查
    if (*endptr != '\0' || va > 0x7FFF) {  // 检查是否全部转换且不超过15位
        printf("错误：无效的虚拟地址格式或超出15位范围！\n");
        return 1;
    }

    // 输入物理页分配信息
    printf("请输入已分配的物理页数量N：");
    if (scanf("%d", &N) != 1 || N < 0 || N > VIRTUAL_PAGES) {
        printf("错误：无效的N值！\n");
        return 1;
    }

    printf("请输入%d组虚拟页->物理页映射（格式：v p）：\n", N);
    for (int i = 0; i < N; i++) {
        int v_page, p_page;
        if (scanf("%d %d", &v_page, &p_page) != 2) {
            printf("输入格式错误！请重新输入。\n");
            while (getchar() != '\n'); // 清空输入缓冲区
            i--;  // 重新输入当前行
            continue;
        }
        
        // 验证输入有效性
        if (v_page < 0 || v_page >= VIRTUAL_PAGES || 
            p_page < 0 || p_page >= PHYSICAL_PAGES) {
            printf("警告：无效页号，跳过第%d组映射\n", i+1);
            continue;
        }
        page_table[v_page] = p_page;
    }

    // 地址转换计算
    int vpn = (va >> 10) & 0x1F;  // 虚拟页号（5位）
    int offset = va & 0x3FF;       // 页内偏移（10位）

    if (page_table[vpn] == -1) {
        printf("缺页中断！虚拟页%d未分配物理页\n", vpn);
    } else {
        uint32_t phy_addr = (page_table[vpn] << 10) | offset;
        printf("物理地址：0x%04X\n", phy_addr);
    }

    return 0;
}