package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class JFrameTest2 {

    private JFrame jf;
    private JTextField jtf;
    private double result = 0;
    private String expression = "";
    private double memory = 0; // 内存存储
    private boolean isRadianMode = true; // 角度/弧度模式切换，true为弧度模式

    public JFrameTest2() {

        jf = new JFrame();
        jf.setTitle("科学计算器");
        jf.setBounds(400, 300, 500, 600);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());

        // 1.菜单栏
        JMenuBar menuBar = new JMenuBar();

        JMenu checkMenu= new JMenu("查看(V)");
        JMenuItem tableItem = new JMenuItem("功能表");

        JMenu editMenu = new JMenu("编辑(E)");
        JMenuItem copyItem = new JMenuItem("复制(C)");
        JMenuItem pasteItem = new JMenuItem("粘贴(P)");

        JMenu helpMenu= new JMenu("帮助(H)");
        JMenuItem helpItem = new JMenuItem("???");

       //功能表的跳转实现
        tableItem.addActionListener(e -> {
            try {
                // 相对路径到 HTML 文件
                String relativePath = "src/main/resource/table.html";
                File htmlFile = new File(relativePath);

                if (htmlFile.exists()) {
                    JFrame htmlFrame = new JFrame("功能表");
                    htmlFrame.setSize(800, 600);
                    htmlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setEditable(false); // 只读
                    editorPane.setContentType("text/html"); // 设置内容类型为 HTML
                    editorPane.setPage(htmlFile.toURI().toURL()); // 加载 HTML 文件

                    // 添加滚动条支持
                    JScrollPane scrollPane = new JScrollPane(editorPane);
                    htmlFrame.add(scrollPane, BorderLayout.CENTER);

                    htmlFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(jf, "HTML 文件不存在！\n请检查路径：" + htmlFile.getAbsolutePath(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(jf, "无法加载 HTML 文件", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });


        //搜的，有点看不懂如何实现，大概是调用了系统的剪切板
        copyItem.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(jtf.getText());
            clipboard.setContents(selection, null);
        });

        pasteItem.addActionListener(e -> {
            try {
                String str=jtf.getText();
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                jtf.setText(str+data);
                expression = data;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jf, "无法粘贴", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        menuBar.add(editMenu);

        checkMenu.add(tableItem);
        menuBar.add(checkMenu);

        helpMenu.add(helpItem);
        menuBar.add(helpMenu);

        jf.setJMenuBar(menuBar);


        // 2.北方面板，边框布局，文本框在南边
        // 文本框
        jtf = new JTextField();
        jtf.setEditable(true);
        jtf.setFont(new Font("Arial", Font.BOLD, 24));
        jtf.setHorizontalAlignment(JTextField.RIGHT);
        jtf.setPreferredSize(new Dimension(400, 80));


        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(jtf, BorderLayout.SOUTH);

        jf.add(northPanel, BorderLayout.NORTH);


        // 3.中间面板(按钮面板),网格布局， 8行5列，间距为5
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(8, 5, 5, 5));
        jp1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "Rad", "MC", "MR", "MS", "M+",
                "deg", "sin", "cos", "tan", "M-",
                "x²", "√", "log", "ln", "e^x",
                "<--", "CE", "C", "%", "(",
                "7", "8", "9", "/", ")",
                "4", "5", "6", "*", "1/x",
                "1", "2", "3", "-", "π",
                "0", ".", "=", "+", "±"
        };

        for (String label : buttons) {
            JButton jb = new JButton(label);
            jb.addActionListener(new MyButtonListener());
            jb.setFont(new Font("Arial", Font.PLAIN, 16));

            if ("0123456789.".contains(label)) {
                jb.setBackground(new Color(232, 255, 232));
            } else if ("=".contains(label)) {
                jb.setBackground(new Color(230, 230, 250));
            } else if ("+-/*<--CE%".contains(label)) {
                jb.setBackground(new Color(255, 228, 225));
            } else {
                jb.setBackground(new Color(220, 220, 220));
            }
            
            jp1.add(jb);
        }

        jf.add(jp1, BorderLayout.CENTER);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new JFrameTest2();
    }

    // 事件处理
    private class MyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            try {
                switch (command) {
                    case "=":
                        //计算表达式(expression),下面用递归实现
                        result = evaluateExpression(expression);
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "C":
                    case "CE":
                        jtf.setText("");
                        expression = "";
                        break;
                    case "<--":
                        if (!expression.isEmpty()) {
                            expression = expression.substring(0, expression.length() - 1);
                            jtf.setText(expression);
                        }
                        break;
                    case "MC":
                        memory = 0;
                        break;
                    case "MR":
                        expression = String.valueOf(memory);
                        jtf.setText(expression);
                        break;
                    case "MS":
                        memory = Double.parseDouble(jtf.getText());
                        break;
                    case "M+":
                        memory += Double.parseDouble(jtf.getText());
                        break;
                    case "M-":
                        memory -= Double.parseDouble(jtf.getText());
                        break;
                    case "±":
                        if (!expression.isEmpty()) {
                            if (expression.startsWith("-")) {
                                expression = expression.substring(1);
                            } else {
                                expression = "-" + expression;
                            }
                            jtf.setText(expression);
                        }
                        break;
                    case "π":
                        expression += String.valueOf(Math.PI);
                        jtf.setText(expression);
                        break;
                    case "e^x":
                        result = Math.exp(Double.parseDouble(jtf.getText()));
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "sin":
                        double angle = Double.parseDouble(jtf.getText());
                        if (!isRadianMode) {
                            angle = Math.toRadians(angle);
                        }
                        result = Math.sin(angle);
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "cos":
                        angle = Double.parseDouble(jtf.getText());
                        if (!isRadianMode) {
                            angle = Math.toRadians(angle);
                        }
                        result = Math.cos(angle);
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "tan":
                        angle = Double.parseDouble(jtf.getText());
                        if (!isRadianMode) {
                            angle = Math.toRadians(angle);
                        }
                        result = Math.tan(angle);
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "log":
                        result = Math.log10(Double.parseDouble(jtf.getText()));
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "ln":
                        result = Math.log(Double.parseDouble(jtf.getText()));
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "x²":
                        result = Math.pow(Double.parseDouble(jtf.getText()), 2);
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "√":
                        result = Math.sqrt(Double.parseDouble(jtf.getText()));
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "1/x":
                        result = 1 / Double.parseDouble(jtf.getText());
                        jtf.setText(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;
                    case "Rad":
                    case "deg":
                        isRadianMode = !isRadianMode;
                        break;
                    default:
                        expression += command;
                        jtf.setText(expression);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jf, "计算错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 计算表达式的值
    private double evaluateExpression(String expression) throws Exception {
        return new ExpressionEvaluator().evaluate(expression);
    }


    // 表达式求值类
    private static class ExpressionEvaluator {
        public double evaluate(String expression) throws Exception {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                //开始递归
                double parse() {
                    nextChar();
                    double x = parse1();
                    if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }

                double parse1() {
                    double x = parse2();
                    for (; ; ) {
                        if (eat('+')) x += parse2(); // 加法
                        else if (eat('-')) x -= parse2(); // 减法
                        else return x;
                    }
                }

                double parse2() {
                    double x = parse3();
                    for (; ; ) {
                        if (eat('*')) x *= parse3(); // 乘法
                        else if (eat('/')) {
                            double d = parse3();
                            if (d == 0) throw new RuntimeException("除数不能为零");
                            x /= d; // 除法
                        } else if (eat('%')) {
                            double d = parse3();
                            if (d == 0) throw new RuntimeException("除数不能为零");
                            x %= d; // 取模
                        } else return x;
                    }
                }

                double parse3() {
                    if (eat('+')) return parse3(); // 正号
                    if (eat('-')) return -parse3(); // 负号

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) { // 圆括号
                        x = parse1();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // 数字
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(expression.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    return x;
                }
            }.parse();
        }
    }
}



