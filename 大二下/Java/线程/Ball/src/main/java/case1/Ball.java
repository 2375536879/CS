package main.java.case1;


import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Ball implements Runnable{
    private int x,y;
    private int d=30;
    private int speedX,speedY;
    private Color color;
   private int maxWidth,maxHeight;
   private Runnable ht;
   private boolean running =true;

   private Random random=new Random();

public Ball(int width,int height,Runnable ht){
    this.maxWidth=width;
    this.maxHeight=height;
    this.ht=ht;
    resetPosition();
}

//初始位置，初始速度，随机方向，初始颜色
public void resetPosition(){
    //初始位置
  this.x=random.nextInt(maxWidth-d);
  this.y=random.nextInt(maxHeight-d);
    //初始速度
    this.speedX=random.nextInt(5)+1;
    this.speedY=random.nextInt(5)+1;
    //随机方向
    if(random.nextBoolean()){speedX=-speedX;}
    if(random.nextBoolean()){speedY=-speedY;}

    //初始颜色
    this.color=new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
}

public void draw(Graphics g){
    g.setColor(color);
    g.fillOval(x,y,d,d);//填充椭圆区域
}

public Rectangle getBounds() {return new Rectangle(x,y,d,d);}

    public void move(){
   if(!running )return ;
   x+=speedX;
   y+=speedY;
   if(x<0||x>maxWidth){speedX=-speedX;}
   if(y<0||y>maxHeight){speedY=-speedY;}

    }

    @Override
    public void run(){
       while(running){
           move();
           try {
               sleep(30);
           } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
               break;
           }
       }
    }

    public void stop(){
    running =false;
    }
}
