#include <stdio.h>
#include <stdlib.h>

// 预定义的页目录表和页表（仅示例部分数据）
int page_directory[] = {1018, 1178, 1618};  // 页目录表项：索引0对应页表0，索引1对应页表1，依此类推
int page_table_0[] = {1, 3, 5};             // 页表0的页表项
int page_table_1[] = {114, 117, 121};       // 页表1的页表项
int page_table_2[] = {1430, 1455, 1470};    // 页表2的页表项

// 页表映射结构：将页目录项中的页框号映射到对应的页表
typedef struct {
    int pfn;         // 页框号（来自页目录项）
    int *page_table; // 对应的页表数组
    int length;      // 页表长度
} PageTableMap;

PageTableMap mappings[] = {
    {1018, page_table_0, sizeof(page_table_0)/sizeof(int)},
    {1178, page_table_1, sizeof(page_table_1)/sizeof(int)},
    {1618, page_table_2, sizeof(page_table_2)/sizeof(int)},
};

int main() {
    unsigned int virtual_addr;
    printf("请输入虚拟地址（十六进制，如0x1F326）: ");
    scanf("%x", &virtual_addr);

    // 分解逻辑地址：PDI（10位）、PTI（10位）、偏移（12位）
    unsigned int pdi = 0;  // 高10位
    unsigned int pti = 0;  // 中间10位
    unsigned int offset = 0;       // 低12位
    //pdi = ////补充代码块;  // 高10位
    //pti = ////补充代码块;  // 中间10位
    //offset = ////补充代码块;       // 低12位
    
    pdi = (virtual_addr >> 22)&0x3FF;//0x3FF  11 1111 1111
    pti = (virtual_addr >> 12) &0x3FF ;//  0x3FF
    offset = virtual_addr&0xFFF;
    printf("页内偏移量：0x%X\n", offset);

    // 步骤1：查找页目录项（PDE）
    if (pdi >= sizeof(page_directory)/sizeof(int)) {
        printf("错误：页目录索引 %d 超出范围！\n", pdi);
        return 1;
    }
    int pde_pfn = page_directory[pdi];  // 页目录项中存储的页表页框号
    printf("[PDE] 页目录索引 %d → 页表页框号 %d\n", pdi, pde_pfn);

    // 步骤2：查找页表项（PTE）
    int *pte_table = NULL;
    int pte_length = 0;
    for (int i = 0; i < sizeof(mappings)/sizeof(PageTableMap); i++) {
        if (mappings[i].pfn == pde_pfn) {

            //补充代码块
            pte_table = mappings[i].page_table;
            pte_length = mappings[i].length;
        }
    }
    if (pte_table == NULL) {
        printf("错误：未找到页框号 %d 对应的页表！\n", pde_pfn);
        return 1;
    }
    if (pti >= pte_length) {
        printf("错误：页表索引 %d 超出页表范围！\n", pti);
        return 1;
    }
    int pte_pfn = pte_table[pti];  // 页表项中存储的物理页框号
    printf("[PTE] 页表索引 %d → 物理页框号 %d\n", pti, pte_pfn);

    // 步骤3：生成物理地址
    unsigned int phys_addr =0;  // 页框号左移12位后拼接偏移
    //phys_addr = //补充代码块

    phys_addr = (pte_pfn << 12) | offset;



    printf("物理地址：0x%X\n", phys_addr);

    return 0;
}