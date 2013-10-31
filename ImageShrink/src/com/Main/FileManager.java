package com.Main;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

  
public class FileManager extends JPanel {
	
 	  static JTextArea taskOutput;
	  static JFrame frame;
 	  static final int MY_MINIMUM=1;
	  static final int MY_MAXIMUM=2;
 
 public FileManager() {
	super(new BorderLayout());
 	 
     taskOutput = new JTextArea(5, 20);
     taskOutput.setMargin(new Insets(5,5,5,5));
     taskOutput.setEditable(false);

     JPanel panel = new JPanel();
     add(panel, BorderLayout.PAGE_START);
     add(new JScrollPane(taskOutput), BorderLayout.CENTER);
     
     setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     
  }

 public static void showErrorFiles(List fileList){
	    StringBuffer buffer=new StringBuffer();
	    frame = new JFrame(MainClass.copyrightTitle);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JComponent fileContentPane = new FileManager();
	    fileContentPane.setOpaque(true);  
	    frame.setContentPane(fileContentPane);
	    frame.pack();
	    frame.setBounds(250, 100, 700, 500);
	    frame.setVisible(true);
	    Iterator itr=fileList.iterator();
	    buffer.append("Below Files Failed in Compression ..\n\n");
	    while(itr.hasNext())
		 {
	    	 File file=(File)itr.next();
		 	 buffer.append(file.getAbsolutePath()+"\n");
		 }
	    taskOutput.append(buffer.toString());
	    
 }
public static void showFileManager(List fileList) {
    
	StringBuffer buffer=new StringBuffer();
    frame = new JFrame(MainClass.copyrightTitle);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JComponent fileContentPane = new FileManager();
    fileContentPane.setOpaque(true);  
    frame.setContentPane(fileContentPane);
    frame.pack();
    frame.setBounds(250, 100, 700, 500);
    frame.setVisible(true);
    Iterator itr=fileList.iterator();
    buffer.append("Files found other than Images ..\n\n");
    try{
    	//log.info("Files found other than Images ..");
    }catch(Exception ex){}
	 while(itr.hasNext())
	 {
		 FileCopyPOJO fil=(FileCopyPOJO)itr.next();
		 try{
			// log.info(fil.fromFile+"------>"+fil.toFile);
		 }catch(Exception ex){}
		 buffer.append(fil.fromFile+"------>"+fil.toFile+"\n");
	 }
    taskOutput.append(buffer.toString());

}

}
