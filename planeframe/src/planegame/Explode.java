package planegame;

import java.awt.*;

public class Explode {
    double x;
    double y;
    static Image[] imgs=new Image[4];

    static {
        for(int i=0;i<4;i++){
            imgs[i]=GameUtil.getImage("images/me_destroy_"+(i+1)+".png");
            imgs[i].getWidth(null);
        }
    }
    int count;
    boolean live=true;
    public void draw(Graphics g){
        if(!live){
            return ;
        }
        if(count<4){
            g.drawImage(imgs[count],(int)x,(int)y,null );
            count++;
        }
        else{
            live=false;
        }

    }

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
