package main.java.threadcase1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BallGame extends JFrame {

    private JPanel controlPanel;
    private JSpinner ballNum;
    private JButton startButton;
     private JLabel timeLabel;

    private JPanel gamePanel;
    private boolean gameRunning =false;
    private ArrayList<Ball> balls=new ArrayList<>();
   private Rectangle mouseRect =new Rectangle(0,0,20,20);

private long startTime;
private long endTime;
private long currentTime;
private Timer gameTimer;
    private ExecutorService executorService;


private boolean YiDong = false;

    public BallGame(){

        setTitle("球球大作战");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //设置小球个数
    controlPanel=new JPanel();
    ballNum=new JSpinner(new SpinnerNumberModel(5,1,20,1));//初始值value设为5
     startButton=new JButton("开始游戏");

     executorService = Executors.newFixedThreadPool(20);//线程池大小
     startButton.addActionListener(e -> startGame());
     timeLabel=new JLabel("0");
     controlPanel.add(new JLabel("小球数量"));
     controlPanel.add(ballNum);
     controlPanel.add(startButton);
     controlPanel.add(timeLabel);

     gamePanel=new JPanel(){
         @Override
         protected void paintComponent(Graphics g){
             //初始正方形
             super.paintComponent(g);
             if(gameRunning){
                 for(Ball ball:balls){
                     ball.draw(g);
                 }
                 g.setColor(Color.red);
                 g.fillRect(mouseRect.x,mouseRect.y,mouseRect.width,mouseRect.height);
             }
         }
     };

     gamePanel.setBackground(Color.WHITE);

     //鼠标监听
     gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseMoved(MouseEvent e) {
            if(gameRunning){

                int X1=gamePanel.getWidth()/2;
                int Y1=gamePanel.getHeight()/2;
                if(e.getX()>=X1-mouseRect.width/2&&e.getX()<=X1+mouseRect.width/2
                        &&e.getY()<=Y1+mouseRect.height/2&&e.getY()<=Y1+mouseRect.height/2){
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




    }




    public void startGame(){
    gameRunning =true;
     balls.clear();
     int count=(int)ballNum.getValue();

     int X1=gamePanel.getWidth()/2;
     int Y1=gamePanel.getHeight()/2;
     mouseRect.setLocation(X1-mouseRect.width/2,Y1-mouseRect.height);

     for(int i=0;i<count;i++){
         Ball ball =new Ball(gamePanel.getWidth(),gamePanel.getHeight(),this::gameOver);
         balls.add(ball);
         executorService.submit(ball);

//         Thread ballThread=new Thread(ball);
//        ballThread.start();
     }

     startTime=System.currentTimeMillis();
     startButton.setEnabled(false);


     //游戏主循环
        gameTimer = new Timer(30,e->{
            if(!gameRunning )return ;
            for(Ball ball : balls){
                if(ball.getBounds().intersects(mouseRect)){
                    gameOver();
                    return ;
                }
            }

            if(mouseRect.x<=0||mouseRect.y<=0||mouseRect.x>=gamePanel.getWidth()-mouseRect.width
                    ||mouseRect.y>=gamePanel.getHeight()-mouseRect.height){
                gameOver();
                return ;
            }

            currentTime=System.currentTimeMillis();
            long ll=currentTime-startTime;
            double ss=ll/1000;
            timeLabel.setText(String.valueOf(ss));



            SwingUtilities.invokeLater(() -> gamePanel.repaint());
        });

     gameTimer.start();

    }



    public void gameOver(){
gameRunning=false;
gameTimer.stop();
balls.forEach(Ball::stop);
 endTime=System.currentTimeMillis();
double seconds=(endTime-startTime)/1000;

JOptionPane.showMessageDialog(this,"游戏结束！\n你坚持了 " + seconds + " 秒",
        "游戏结束",JOptionPane.INFORMATION_MESSAGE);
startButton.setEnabled(true);
 YiDong=false;

       //关闭线程池
       // executorService.shutdown();
    }


}
