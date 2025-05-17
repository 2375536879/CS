package main.java.case1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BallGame extends JFrame {

    private ExecutorService executorService;

   private JPanel controlPanel;
   private JSpinner ballNum;
   private JButton startButton;
 private JLabel timeLabel;

   private boolean gameRunning=false;
    private JPanel gamePanel;
 private ArrayList<Ball> balls;
 private Rectangle mouseRect=new Rectangle(0,0,20,20);
private boolean YiDong=false;
private long startTime;
private long endTime;
private long currentTime;

private Timer gameTimer;

//开始菜单
   // 游戏介绍
       //请设置小球的数量，点击开始游戏后，把鼠标放到红色方块后即可开始移动
       //难度1  难度2 难度3 --->设置速度



    public BallGame(){
        setTitle("球球大作战");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(1200, 900);


        controlPanel=new JPanel();
        ballNum=new JSpinner(  new SpinnerNumberModel(5,1,20,1));
         startButton=new JButton("开始游戏");
        startButton.addActionListener(e -> startGame());
        timeLabel=new JLabel("0");
        balls = new ArrayList<>();
       controlPanel.add(new JLabel("小球数量"));
       controlPanel.add(ballNum);
       controlPanel.add(timeLabel);
       controlPanel.add(startButton);

        gamePanel=new JPanel(){
          @Override
            public void paintComponent(Graphics g) {

            super.paintComponent(g);
            if(gameRunning){
            for(Ball ball:balls){
                 ball.draw(g);//传的是g,但是不影响Ball类内部对坐标进行随机,g只是为了执行绘制操作
}
             g.setColor(Color.red);
             g.fillRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
            }
          }
        };
          gamePanel.setBackground(Color.WHITE);

gamePanel.addMouseMotionListener(new MouseMotionAdapter(){
@Override
    public void mouseMoved(MouseEvent e) {
if(gameRunning){
     int X=gamePanel.getWidth()/2;
     int Y=gamePanel.getHeight()/2;
if(e.getX()>=X-mouseRect.width/2&&e.getX()<=X+mouseRect.width/2
        &&e.getY()>=Y-mouseRect.height/2&&e.getY()<=Y+mouseRect.height/2){
    YiDong=true;
}

 if(YiDong){
     mouseRect.setLocation(e.getX()-10,e.getY()-10);
gamePanel.repaint();
 }
}
}
});
add(controlPanel,BorderLayout.NORTH);
add(gamePanel,BorderLayout.CENTER);
        executorService= Executors.newFixedThreadPool(20);

   }




  public void startGame(){
gameRunning=true;
balls.clear();;



int count=(int)ballNum.getValue();
      int X=gamePanel.getWidth()/2;
      int Y=gamePanel.getHeight()/2;
      mouseRect.setLocation(X-mouseRect.width/2,Y-mouseRect.height/2);

        for(int i=0;i<count;i++){
            Ball ball=new Ball(gamePanel.getWidth(),gamePanel.getHeight(),this::gameOver);
            balls.add(ball);
         executorService.submit(ball);
        }

        startTime=System.currentTimeMillis();
       startButton.setEnabled(false);


       //
      gameTimer=new Timer(30, e->{
            if(!gameRunning)return;
            for(Ball ball:balls){
         if(ball.getBounds().intersects(mouseRect)){
             gameOver();
             return ;
         }
            }
if(mouseRect.x<=0||mouseRect.y<=0||mouseRect.x>=gamePanel.getWidth()||mouseRect.y>=gamePanel.getHeight()){
    gameOver();
    return;
}

          currentTime=System.currentTimeMillis();
          long ll=currentTime-startTime;
          double ss=ll/1000;
          timeLabel.setText(String.valueOf(ss));

SwingUtilities.invokeLater(()->gamePanel.repaint());
      });

      //初始化后启动定时器
      gameTimer.start();

  }


    public void gameOver(){
gameRunning=false;
gameTimer.stop();
balls.forEach(Ball::stop);

endTime=System.currentTimeMillis();
double seconds=(endTime-startTime)/1000;
JOptionPane.showMessageDialog(this,"游戏结束:\n你坚持了"+seconds+"秒","游戏结束",
        JOptionPane.INFORMATION_MESSAGE);
startButton.setEnabled(true);
YiDong=false;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BallGame game = new BallGame();
            game.setVisible(true);
        });
    }
}


