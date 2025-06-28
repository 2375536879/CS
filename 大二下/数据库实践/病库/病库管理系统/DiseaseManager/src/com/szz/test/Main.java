package com.szz.test;

import com.szz.model.Patient;
import com.szz.service.PatientService;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        PatientService patientService = new PatientService();

        while (true) {
            System.out.println("=== 病库管理系统 ===");
            System.out.println("1. 添加患者");
            System.out.println("2. 查看所有患者");
            System.out.println("3. 退出");
            System.out.print("请输入选项：");

            int choice = scanner.nextInt();
            if (choice == 1) {
                // 添加患者逻辑
                Patient p = new Patient();
                System.out.print("就诊号："); p.setHospitalPatientId(scanner.next());
                System.out.print("姓名："); p.setName(scanner.next());
                System.out.print("性别（男/女/其他）："); p.setGender(scanner.next());
                System.out.print("出生日期（YYYY-MM-DD）："); p.setDateOfBirth(Date.valueOf(scanner.next()));
                System.out.print("家庭住址："); p.setHomeAddress(scanner.next());
                System.out.print("出生体重（kg）："); p.setBirthWeightKg(scanner.nextDouble());
                scanner.nextLine(); // 清空换行符
                System.out.print("生活方式备注："); p.setLifestyleNotes(scanner.nextLine());
                // 其他字段略...

                patientService.registerNewPatient(p);
                System.out.println("✅ 患者添加成功！");
            } else if (choice == 2) {
                for (Patient p : patientService.getAllPatients()) {
                    System.out.println(p.getName() + " - " + p.getGender());
                }
            } else if (choice == 3) {
                break;
            }
        }
        scanner.close();
    }
}