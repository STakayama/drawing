import javax.swing.*;

import javax.swing.event.*;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import java.util.Arrays;
import java.awt.Graphics;
import java.awt.Graphics2D;//
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;
//import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;




class MyJPanel extends JPanel implements MouseInputListener,ComponentListener{

  Integer nibs[]=new Integer[10000];
  int nibsize;
  Color backcolor=Color.WHITE;
  MyJPanel(){
    for(int i=0;i<10000;i++){
      DataPlace.points[i]=new PointInformation();
    }
    super.setVisible(true);
    setBackground(Color.WHITE);
    addMouseMotionListener(this);
    addMouseListener(this);
    addComponentListener(this);
    repaint();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2;

    DataPlace.bufferedimg=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
    g2=(Graphics2D) DataPlace.bufferedimg.createGraphics();
    if(DataPlace.backgroundcolorflag!=0){
      backcolor=DataPlace.color;
    }
    g2.setBackground(backcolor);
    g2.clearRect(0, 0, getWidth(), getHeight());

    if(DataPlace.img!=null){
      g2.drawImage(DataPlace.img,0,0,DataPlace.imgwidth,DataPlace.imgheight,null);
    }
    g2.setColor(Color.BLACK);

    for(int i=0;i<10000;i++){
      if(DataPlace.points[i].point==null){//||DataPlace.points[i+1].point==null
	break;
      }
      try{
	nibsize=DataPlace.PenSizeSlider.getValue();
      }catch(Exception e){
	nibsize=1;
      }
      g2.setStroke(new BasicStroke(nibs[i]));
      g2.setColor(DataPlace.points[i].pointcolor);
      if(DataPlace.points[i].stampkind==0&&i>1){
	if(DataPlace.points[i].point.x<0||DataPlace.points[i-1].point.x<0){
	  continue;
	}
	if(DataPlace.points[i-1].stampkind==0){
	  g2.drawLine(DataPlace.points[i].point.x,DataPlace.points[i].point.y,DataPlace.points[i-1].point.x,DataPlace.points[i-1].point.y);
	}
      }


      if(DataPlace.stripeflag!=0){
	for(int n=0;n<DataPlace.framewidth;n=n+40){
	  g2.setColor(Color.BLACK);
	  g2.setStroke(new BasicStroke(16.0f));
	  g2.drawLine(n,0,n,DataPlace.frameheight);
	}
      }

      if(DataPlace.borderflag!=0){
	for(int n=0;n<DataPlace.frameheight;n=n+40){
	  g2.setColor(Color.BLACK);
	  g2.setStroke(new BasicStroke(16.0f));
	  g2.drawLine(0,n,DataPlace.framewidth,n);
	}
      }


      if(DataPlace.points[i].stampkind==1){
	g2.setColor(DataPlace.points[i].pointcolor);
	g2.fillOval(DataPlace.points[i].point.x-(20+nibs[i]),DataPlace.points[i].point.y-(20+nibs[i]),(20+nibs[i])*2,(20+nibs[i])*2);
      }
      else if(DataPlace.points[i].stampkind==2){
	g2.setColor(DataPlace.points[i].pointcolor);
	g2.fill(new Rectangle2D.Double(DataPlace.points[i].point.x-(25.0d+nibs[i])/2,DataPlace.points[i].point.y-(25.0d+nibs[i])/2,25.0d+nibs[i],25.0d+nibs[i]));
      } 
      else if(DataPlace.points[i].stampkind==3){
	drawStar(g2,DataPlace.points[i].point.x,DataPlace.points[i].point.y,20+nibs[i],90+nibs[i]);
      }
    }




    g.drawImage(DataPlace.bufferedimg,0,0,this);
  }

  void drawStar(Graphics g,int centerx,int centery,int r,int angle){
    int t,x,y;
    int sw=0;//switchだとダメなので、便宜的にsw
    Polygon p =new Polygon();
    for(t=angle;t<angle+360;t=t+360/10){
      if(sw==0){
	x=(int)(r*Math.cos(t*Math.PI/180)+centerx+0.5);
	y=(int)(-r*Math.sin(t*Math.PI/180)+centery+0.5);
	sw=1;
      }else{
	x=(int)(r*0.4*Math.cos(t*Math.PI/180)+centerx+0.5);
	y=(int)(-r*0.4*Math.sin(t*Math.PI/180)+centery+0.5);
	sw=0;
      }
      p.addPoint(x,y);
    }
    g.fillPolygon(p);
  } 


  @Override
  public void mouseDragged(MouseEvent e){
    if (DataPlace.eraserflag != 1) {
      DataPlace.points[DataPlace.number].stampkind = 0;
      DataPlace.points[DataPlace.number].point = e.getPoint();
      DataPlace.points[DataPlace.number].drawtimes = DataPlace.draggtimes;
    } else {
    }
    DataPlace.points[DataPlace.number].pointcolor = DataPlace.color;
    if (DataPlace.PenSizeSlider != null) {
      nibs[DataPlace.number] = nibsize;

    } else {
      nibs[DataPlace.number] = 1;
    }
    if(DataPlace.dotflag==1){
      if(DataPlace.number%6==3||DataPlace.number%6==4||DataPlace.number%6==5){
	DataPlace.points[DataPlace.number].point =new Point(-1, -1);
      }
    }
    DataPlace.number++;
    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (DataPlace.stampflag != 0) {
      DataPlace.draggtimes--;

      DataPlace.points[DataPlace.number].stampkind = DataPlace.stampflag;
      DataPlace.points[DataPlace.number].point = e.getPoint();
      DataPlace.points[DataPlace.number].pointcolor = DataPlace.color;
      DataPlace.points[DataPlace.number].drawtimes = DataPlace.draggtimes;
      if (DataPlace.PenSizeSlider != null) {
	nibs[DataPlace.number] = nibsize;
      } else {
	nibs[DataPlace.number] = 1;
      }
      DataPlace.draggtimes++;
      DataPlace.number++;
    }
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (DataPlace.points[DataPlace.number].stampkind == 0) {
      DataPlace.points[DataPlace.number].point = new Point(-1, -1);
      DataPlace.points[DataPlace.number].pointcolor = DataPlace.color;
    }
    if (DataPlace.PenSizeSlider != null) {
      nibs[DataPlace.number] = nibsize;
    } else {
      nibs[DataPlace.number] = 1;
    }
    DataPlace.draggtimes++;
    DataPlace.number++;
    repaint();
  }



  void resizeImage(int resizewidth,int resizeheight) {
    int resizeruler;
    if(resizewidth>resizeheight){
      resizeruler=resizeheight;
    }
    else{
      resizeruler=resizewidth;
    }
    if (DataPlace.imgheight < DataPlace.imgwidth) {
      DataPlace.imgheight = (int) ((double) DataPlace.imgheight * resizeruler / (double) DataPlace.imgwidth);
      DataPlace.imgwidth = resizeruler;
    } else {
      DataPlace.imgwidth = (int) ((double) DataPlace.imgwidth * resizeruler / (double) DataPlace.imgheight);
      DataPlace.imgheight = resizeruler;
    }
    repaint();	   
  }


  @Override
  public void componentResized(ComponentEvent e){
    resizeImage(getWidth(),getHeight());
    DataPlace.framewidth=getWidth();
    DataPlace.frameheight=getHeight();
  }

  @Override
  public void componentMoved(ComponentEvent e){}

  @Override
  public void componentShown(ComponentEvent e){}

  @Override
  public void componentHidden(ComponentEvent e){}
}

