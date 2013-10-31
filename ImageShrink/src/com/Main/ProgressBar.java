package com.Main;

import java.awt.*;
import javax.swing.*;

public class ProgressBar extends JPanel {
  static JProgressBar progressBar;
  static JTextArea taskOutput;
  static JLabel progressLabel;
  static JFrame frame;
  static final int MY_MINIMUM=1;
  static final int MY_MAXIMUM=2;
  static int max=0;
  
  public ProgressBar(int max) {
	super(new BorderLayout());
	this.max=max;
	 progressBar = new JProgressBar(0, max);
     progressBar.setValue(0);
     progressBar.setStringPainted(true);

     taskOutput = new JTextArea(5, 20);
     taskOutput.setMargin(new Insets(5,5,5,5));
     taskOutput.setEditable(false);
     
     progressLabel=new JLabel("Processing Images : ");
     
     JPanel panel = new JPanel();
     
     panel.add(progressLabel);
     panel.add(progressBar);
     
     add(panel, BorderLayout.PAGE_START);
     add(new JScrollPane(taskOutput), BorderLayout.CENTER);
     setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     
  }
  public static void createAndShowGUI(int num) {
      
      frame = new JFrame(MainClass.copyrightTitle);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JComponent newContentPane = new ProgressBar(num);
      newContentPane.setOpaque(true);  
      frame.setContentPane(newContentPane);
      frame.pack();
      frame.setBounds(250, 100, 500, 500);
      frame.setVisible(true);
     
  }
  public static void updateBar(int newValue,String filename) {
    progressBar.setValue(newValue);
    taskOutput.append(String.format("Compressed File %d - %s of %d \n", newValue,filename,max));
    
  }
  
}