package test;

import ADMS.PatientManageUI;
import javax.swing.SwingUtilities;

/**
 * 测试PatientManageUI的修复
 */
public class TestPatientManageUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 创建并显示患者管理界面
                PatientManageUI ui = new PatientManageUI();
                ui.setVisible(true);
                
                System.out.println("患者管理界面已启动");
                System.out.println("修复内容:");
                System.out.println("1. 修复了ClinicalVisitView中的ClassCastException问题");
                System.out.println("2. 添加了其他相关信息的具体内容，包括:");
                System.out.println("   - 实验室检查");
                System.out.println("   - 肺功能检查");
                System.out.println("   - 呼出气一氧化氮检测");
                System.out.println("   - 影像学检查");
                System.out.println("   - 疾病诊断");
                System.out.println("请在界面中选择一个患者，点击'查看详情'，然后查看'其他信息'标签页");
                
            } catch (Exception e) {
                System.err.println("启动患者管理界面时出错: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
