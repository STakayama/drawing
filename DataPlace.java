import javax.swing. *;
import java.awt.image.BufferedImage;
import java.awt.Color;
//import java.awt.Graphics;
import java.awt.Graphics2D;



class DataPlace{

  static BufferedImage img,bufferedimg;
  static Graphics2D bufferGraphics=null;
  static MyJPanel paintspace;
  static Color color;
  static JSlider PenSizeSlider;
  static int imgheight,imgwidth;
  static int eraserflag=0;
  static int backgroundcolorflag=0;
  static int stripeflag=0;
  static int borderflag=0;
  
  
  //MyJFrame
  static PointInformation points[]=new PointInformation[10000];
  static int stampflag=0;//1:circle 2:triangle 3:square
  static int stampnumber=0;
  static int number=0;
  static int draggtimes=0;
  static int dotflag=0;

  static int framewidth=0;
  static int frameheight=0;

  // static int paintspacewidth,paintspaceheight,paintspaceruler;
}
