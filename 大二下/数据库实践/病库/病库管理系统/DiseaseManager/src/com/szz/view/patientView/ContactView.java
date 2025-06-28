package com.szz.view.patientView;

import com.szz.model.Contact;
import com.szz.service.ContactService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContactView extends JPanel {
    private final ContactService contactService = new ContactService();
    private final JTable contactTable;
    private final DefaultTableModel tableModel;
    private final int patientId;

    public ContactView(int patientId) {
        this.patientId = patientId;
        setLayout(new BorderLayout());

        // 创建表格模型
        String[] columnNames = {"联系人姓名", "联系电话", "与患者关系", "创建时间", "更新时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建表格
        contactTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(contactTable);
        add(scrollPane, BorderLayout.CENTER);

        // 加载数据
        loadContactData();
    }

    private void loadContactData() {
        try {
            List<Contact> contacts = contactService.getContactsByPatientId(patientId);
            updateTable(contacts);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "加载联系人数据失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Contact> contacts) {
        tableModel.setRowCount(0); // 清空表格
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Contact contact : contacts) {
            Object[] rowData = {
                    contact.getContactName(),
                    contact.getContactPhone(),
                    contact.getRelationship(),
                    dateFormat.format(contact.getCreatedAt()),
                    dateFormat.format(contact.getUpdatedAt())
            };
            tableModel.addRow(rowData);
        }
    }
}