package main.java.threadcase1;

import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Ball  implements Runnable{
     private int x,y;
     private int diameter=30; //直径
     private int speedX,speedY;
     private Color color;
     private int maxWidth,maxHeight;
     private Runnable ht;
     private Random random=new Random();
     private boolean running = true;

     public Ball(int width,int height,Runnable HuiTiao){
         this.maxWidth=width;
         this.maxHeight=height;
         this.ht=HuiTiao;
         resetPosition();

     }

private void resetPosition(){

    this.x=random.nextInt(maxWidth-diameter);
   this.y=random.nextInt(maxHeight-diameter);
    this.speedX=random.nextInt(5)+1;
    this.speedY=random.nextInt(5)+1;
    this.color=new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));

    if(random.nextBoolean())speedX=-speedX;
    if(random.nextBoolean())speedY=-speedY;
}

    public void draw(Graphics g){
           g.setColor(color);
           g.fillOval(x,y,diameter,diameter);//填充椭圆区域
    }

    //玩家正方形
    public Rectangle getBounds(){return new Rectangle(x,y,diameter,diameter);}

   public void move(){
         if(!running)return;

         x+=speedX;
         y+=speedY;

         if(x<0||x>=maxWidth-diameter)speedX=-speedX;
         if(y<0||y>=maxHeight-diameter)speedY=-speedY;
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
         running=false;
  }


}


