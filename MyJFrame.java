import javax.swing.*;
import javax.imageio.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;



class MyJFrame extends JFrame implements ActionListener, ChangeListener{

  MyJPanel myJPanel;
  JMenu tool, file, stamps;
  JMenuBar menubar;
  JDesktopPane desktop;
  JColorChooser colorchooser;
  JInternalFrame PaintSpace, ColorChooser, PenSize;

  JTabbedPane tabbedpane;
    
 


  MyJFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 700, 700);
    setPreferredSize(new Dimension(1100, 700));
    setBackground(Color.WHITE);
    setContentPane(desktop = makeDesktopPane());
    DataPlace.paintspace = new MyJPanel();
    PaintSpace = makeInternalFrame("PaintSpace", DataPlace.paintspace, desktop);
    PaintSpace.setResizable(true);
    PaintSpace.setSize(500, 500);
    DataPlace.framewidth=500;
    DataPlace.frameheight=500;
	
    PaintSpace.setLocation(0, 0);
    initMenu();
    setVisible(true);
  }

  void initMenu() {
    menubar = new JMenuBar();
    file = new JMenu("File");
    makeJMenuItem("openfile", "ctrl O", file);
    makeJMenuItem("savefile", "ctrl S", file);
    menubar.add(file);

    tool = new JMenu("Tool");
    makeJMenuItem("color palette", "ctrl C", tool);
    makeJMenuItem("background color", "ctrl B", tool);
    makeJMenuItem("pen size", "ctrl P", tool);
    makeJMenuItem("dot line","ctrl L",tool);
    makeJMenuItem("stripe","ctrl Q",tool);
    makeJMenuItem("border","ctrl W",tool);
    makeJMenuItem("undo", "ctrl Z", tool);
    makeJMenuItem("draw reset", "ctrl D", tool);
    makeJMenuItem("image reset", "ctrl I", tool);
    menubar.add(tool);

    stamps = new JMenu("Stamps");
    makeJMenuItem("circle", "shift C", stamps);
    makeJMenuItem("square", "shift Q", stamps);
    makeJMenuItem("star", "shift S", stamps);
    menubar.add(stamps);

    pack();
    this.setJMenuBar(menubar);
  }//メニューバーの設置


  JMenuItem makeJMenuItem(final String s, final String Stroke, final JMenu m) {
    JMenuItem menuitem = new JMenuItem(s);
    menuitem.setAccelerator(KeyStroke.getKeyStroke(Stroke));
    menuitem.addActionListener(this);
    menuitem.setActionCommand(s);
    m.add(menuitem);
    return menuitem;
  }//メニューバーの内容に合わせアイテムセット

  JMenuBar makeMenuBar(final JMenu m) {
    JMenuBar menubar = new JMenuBar();
    menubar.add(m);
    return menubar;
  }//メニューバー設置

  JColorChooser makeColorChooser() {
    JColorChooser chosencolor = new JColorChooser();
    chosencolor.getSelectionModel().addChangeListener(this);
    return chosencolor;
  }//色選択

  JSlider makeSlider(final int min, final int max, final int value) {
    JSlider s = new JSlider(min, max, value);
    s.setMajorTickSpacing(10);
    s.setMinorTickSpacing(1);
    s.setPaintTicks(true);
    return s;
  }//ペンの太さのスライダー

  public void stateChanged(ChangeEvent e) {
    DataPlace.color = colorchooser.getColor();
  }

  public void actionPerformed(ActionEvent e) {
    JFileChooser filechooser = new JFileChooser();
    if (e.getActionCommand().equals("color palette")) {
      ColorChooser = makeInternalFrame("ColorChooser", colorchooser = makeColorChooser(), desktop);
      ColorChooser.setResizable(true);
      ColorChooser.setIconifiable(true);
      ColorChooser.setClosable(true);
      ColorChooser.setSize(600, 300);
      ColorChooser.setLocation(500, 0);
      DataPlace.paintspace.setBackground(Color.WHITE);
    } 

    else if (e.getActionCommand().equals("background color")) {
      if(DataPlace.backgroundcolorflag==0){
	DataPlace.backgroundcolorflag=1;
      }
      else{
	DataPlace.backgroundcolorflag=0;
      }
    }

    else if (e.getActionCommand().equals("pen size")) {
      PenSize = makeInternalFrame("PenSize", DataPlace.PenSizeSlider = makeSlider(1, 100, 3), desktop);
      PenSize.setResizable(true);
      PenSize.setSize(300, 100);
      PenSize.setLocation(0, 500);
    } 

    else if (e.getActionCommand().equals("dot line")) {
      if(DataPlace.dotflag!=0){
	DataPlace.dotflag=0;
      }
      else{
	DataPlace.dotflag=1;
      }

    }
    else if (e.getActionCommand().equals("stripe")) {
      if(DataPlace.stripeflag!=0){
	DataPlace.stripeflag=0;
      }
      else{
	DataPlace.stripeflag=1;
      }
      repaint();
    }

    else if (e.getActionCommand().equals("border")) {
      if(DataPlace.borderflag!=0){
	DataPlace.borderflag=0;
      }
      else{
	DataPlace.borderflag=1;
      }
      repaint();
    }


    else if (e.getActionCommand().equals("undo")) {
      if (DataPlace.draggtimes != 0) {
	DataPlace.draggtimes--;
	for (int i = 0; i < 10000; i++) { 
	  if (DataPlace.points[i].point == null) {
	    continue;
	  }
	  if (DataPlace.points[i].drawtimes >= DataPlace.draggtimes) {
	    DataPlace.points[i] = new PointInformation();  
	    DataPlace.points[i].point = new Point(-1, -1);
	    DataPlace.points[i].pointcolor = DataPlace.color;
	    if (DataPlace.points[i].drawtimes == DataPlace.draggtimes) {
	      DataPlace.number = i;
	    }
	  } else {
	  }
	}
	repaint();
      }
    } else if (e.getActionCommand().equals("draw reset")) {
      DataPlace.number = 0;
      DataPlace.stampflag = 0;
      for (int i = 0; i < 10000; i++) {
	DataPlace.points[i] = new PointInformation();
      }
      DataPlace.draggtimes = 0;
      repaint();
    } else if (e.getActionCommand().equals("image reset")) {
      DataPlace.img = null;
      repaint();
    }

    if ((e.getActionCommand().equals("openfile"))) {
      if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	File file = filechooser.getSelectedFile();
	try {
	  DataPlace.img = null;
	  DataPlace.img = ImageIO.read(file); 
	  DataPlace.imgwidth=DataPlace.img.getWidth();
	  DataPlace.imgheight=DataPlace.img.getHeight();
	  resizeImage(DataPlace.framewidth,DataPlace.frameheight);
	  repaint();
	} catch (IOException ex) {
	  ex.printStackTrace();
	}
      }
    } else if (e.getActionCommand().equals("savefile")) {
      FileFilter filter = new FileNameExtensionFilter("PNGファイル(*.png)", "png");
      filechooser.setFileFilter(filter);
      if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	File fChoosen = filechooser.getSelectedFile();
	try {
	  ImageIO.write(DataPlace.bufferedimg, "png", fChoosen);
	} catch (IOException ex) {
	  ex.printStackTrace();
	}
      }
    }

    if (e.getActionCommand().equals("circle")) {
      if (DataPlace.stampflag != 1) {
	DataPlace.stampflag = 1;
      } else {
	DataPlace.stampflag = 0;
      }
    } else if (e.getActionCommand().equals("square")) {
      if (DataPlace.stampflag != 2) {
	DataPlace.stampflag = 2;
      } else {
	DataPlace.stampflag = 0;
      }
    } else if (e.getActionCommand().equals("star")) {
      if (DataPlace.stampflag != 3) {
	DataPlace.stampflag = 3;
      } else {
	DataPlace.stampflag = 0;
      }
    }

  }

  void resizeImage(int resizewidth,int resizeheight){
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

  JDesktopPane makeDesktopPane() {
    JDesktopPane dkp = new JDesktopPane();
    dkp.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    return dkp;
  }//デスクトップ部の設置

  JInternalFrame makeInternalFrame(final String s, final JComponent j, final JDesktopPane dp) {
    JInternalFrame jif = new JInternalFrame(s);
    jif.add(j);
    jif.setVisible(true);
    jif.setMaximizable(true);
    jif.setIconifiable(true);
    jif.setResizable(true);
    dp.add(jif);
    return jif;
  }//デスクトップ内にフレーム


}
