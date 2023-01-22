package planegame;

import javax.imageio.ImageIO;
import java.awt.*;
import  java.io.IOException;
import java.net.URL;

public class GameUtil {
    public static  final  int FRAME_WIDTH =500;
    public static final int FRAME_HEIGHT=500;
    //私有构造器，防止外部创建对象
    private GameUtil(){}
    public static Image getImage(String path){
        Image img=null;
        URL url=GameUtil.class.getClassLoader().getResource(path);
        try {
            img= ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

}
