package planegame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import static planegame.GameUtil.*;

public class planeframe extends  Frame {
    Image bgImg=GameUtil.getImage("images/background.png");
    Image planeImg=GameUtil.getImage("images/life.png");
    Plane plane=new Plane(planeImg,200,200,5);
    Shell[] shells=new Shell[50];
    Explode explode;
    Date starTime =new Date();
    Date endTime;
    private int period;
    //初始化窗口
    public void lanchuFrame(){
        this.setTitle("飞机大战");
        this.setVisible(true);//窗口默认不可见
        this.setSize(500,500);
        this.setLocation(300,300);
        //关闭窗口功能
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //启动窗口绘制
        new PaintThread().start();
        //启动键盘监听
        this.addKeyListener(new KeyMonitor());
        //初始化炮弹
        for(int i=0;i<shells.length;i++){
            shells[i]=new Shell();
        }
    }

    //键盘监听内部类
    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }

    //绘图
    @Override
    public void paint(Graphics g) {
            g.drawImage(bgImg,0,0,FRAME_WIDTH,FRAME_HEIGHT,null);
            plane.drawMySelf(g);
            for(int i=0;i< shells.length;i++){
                if(shells[i]!=null){
                     shells[i].drawMySelf(g);
                     if(!plane.live){continue;}
                     boolean peng=shells[i].getRec().intersects(plane.getRec());
                     if(peng){
                         plane.live=false;
                         endTime=new Date();
                         period =(int) ((endTime.getTime()- starTime.getTime()) / 1000);
                         if(explode==null){
                             explode=new Explode(plane.x,plane.y);
                         }
                         explode.draw(g);
                     }
                }
            }
            if(!plane.live){
                printInfo(g,"游戏时间"+period+"秒",20,200,200,Color.WHITE);
            }
    }

    //打印相关信息
    public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Font oldFont=g.getFont();
        Color oldcolor=g.getColor();

        Font f=new Font("宋体",Font.BOLD,size);
        g.setFont(f);
        g.setColor(color);
        g.drawString(str,x,y);

        g.setFont(oldFont);
        g.setColor(oldcolor);
    }
    //刷新
   class PaintThread extends Thread{
       @Override
       public void run() {
           while (true){
               repaint();
               try {
                   Thread.sleep(40);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       }
   }
   //双缓冲避免闪烁
   private  Image offScreenImage =null;
    public void update(Graphics g){
        if(offScreenImage==null){
            offScreenImage=this.createImage(FRAME_WIDTH,FRAME_HEIGHT);
        }
        Graphics g0ff= offScreenImage.getGraphics();
        paint(g0ff);
        g.drawImage(offScreenImage,0,0,null);
    }
}
