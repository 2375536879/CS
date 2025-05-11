package main.java.threadcase3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BallGame extends JFrame {
    private JPanel gamePanel;
    private JButton startButton;
    private JSpinner ballCountSpinner;
    private Timer gameTimer;
    private long startTime;
    private ArrayList<Ball> balls = new ArrayList<>();
    private Rectangle mouseRect = new Rectangle(0, 0, 20, 20);
    private boolean gameRunning = false;
    
    public BallGame() {
        setTitle("弹球游戏");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 初始化UI组件
        JPanel controlPanel = new JPanel();
        ballCountSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));
        startButton = new JButton("开始游戏");
        
        startButton.addActionListener(e -> startGame());
        controlPanel.add(new JLabel("小球数量:"));
        controlPanel.add(ballCountSpinner);
        controlPanel.add(startButton);
        
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gameRunning) {
                    for (Ball ball : balls) {
                        ball.draw(g);
                    }
                    g.setColor(Color.RED);
                    g.fillRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
                }
            }
        };
        
        gamePanel.setBackground(Color.WHITE);
        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (gameRunning) {
                    mouseRect.setLocation(e.getX() - 10, e.getY() - 10);
                    gamePanel.repaint();
                }
            }
        });
        
        add(controlPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
    }
    
    private void startGame() {
        gameRunning = true;
        balls.clear();
        int count = (int) ballCountSpinner.getValue();
        
        // 初始化鼠标位置到面板中央
        int centerX = gamePanel.getWidth()/2;
        int centerY = gamePanel.getHeight()/2;
        mouseRect.setLocation(centerX - mouseRect.width/2, centerY - mouseRect.height/2);
        
        // 生成指定数量的小球
        for (int i = 0; i < count; i++) {
            balls.add(new Ball(gamePanel.getWidth(), gamePanel.getHeight()));
        }
        
        System.out.println("游戏面板尺寸: " + gamePanel.getWidth() + "x" + gamePanel.getHeight());
        
        startTime = System.currentTimeMillis();
        startButton.setEnabled(false);
        
        // 游戏主循环
        gameTimer = new Timer(30, e -> {
            if (!gameRunning) return;
            
            // 移动所有小球
            for (Ball ball : balls) {
                ball.move(gamePanel.getWidth(), gamePanel.getHeight());
                
                // 检测小球与鼠标碰撞
                if (ball.getBounds().intersects(mouseRect)) {
                    gameOver();
                    return;
                }
            }
            
            // 检测鼠标与边界碰撞
            if (mouseRect.x <= 0 || mouseRect.y <= 0 ||
                mouseRect.x >= gamePanel.getWidth() - mouseRect.width ||
                mouseRect.y >= gamePanel.getHeight() - mouseRect.height) {
                gameOver();
                return;
            }
            
            gamePanel.repaint();
        });
        
        gameTimer.start();
    }
    
    private void gameOver() {
        gameRunning = false;
        gameTimer.stop();
        long endTime = System.currentTimeMillis();
        double seconds = (endTime - startTime) / 1000.0;
        
        JOptionPane.showMessageDialog(this,
            "游戏结束！\n你坚持了 " + seconds + " 秒",
            "游戏结束",
            JOptionPane.INFORMATION_MESSAGE);
            
        startButton.setEnabled(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BallGame game = new BallGame();
            game.setVisible(true);
        });
    }
}

class Ball {
    private int x, y;
    private int diameter = 30;
    private int speedX, speedY;
    private Color color;
    private Random random = new Random();
    
    public Ball(int width, int height) {
        this.x = random.nextInt(width - diameter);
        this.y = random.nextInt(height - diameter);
        this.speedX = random.nextInt(5) + 1;
        this.speedY = random.nextInt(5) + 1;
        this.color = new Color(
            random.nextInt(256),
            random.nextInt(256), 
            random.nextInt(256)
        );
        
        // 随机确定方向
        if (random.nextBoolean()) speedX = -speedX;
        if (random.nextBoolean()) speedY = -speedY;
    }
    
    public void move(int width, int height) {
        x += speedX;
        y += speedY;
        
        // 边界检测
        if (x <= 0 || x >= width - diameter) speedX = -speedX;
        if (y <= 0 || y >= height - diameter) speedY = -speedY;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }
}
