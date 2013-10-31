package com.Main;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.RenderedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


import javax.imageio.ImageIO;
import javax.swing.*;

 
 public class MainClass {
	static int i=0;
	static int response=0;
	static int fileChooseResponse = 0;
	static File dirName[]=null;
 	static File masterFile=null;
	static int fileCount=0,dirCount=0,fileCntr=0,dirCntr=0;
	static String version="ImageShrink v3.0";
	static File fileList[]=null;
	static File dirList[]=null;
	static public String copyrightTitle=version+" © ScorpionSolutions";
	static String displayStr;
	static String sizeIndicator;
	static DecimalFormat df = new DecimalFormat("#.##");   
	static List list=new ArrayList();
 	 static double testsize=0; 
	  static int totalFolder=0;
	  static int totalFile=0;
	  static double totalSIZE=0; 
	 static double aftersize=0;
	 static int timeSecs=0;
	 static int timeMins=0;
	 static int timeHour=0;
	 static String timeStr="";
 	 static boolean fileChooseFlag = false;
	 static boolean firstTimeFlag = true;
	 static List errorFiles=new ArrayList();
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
   "".contains("");
		try{
			File propFile = new File("C:\\log4j.properties");
			if(!propFile.exists())
			{
				Properties prop = new Properties();
		    	try {
		    		//set the properties value
		    		prop.setProperty("log4j.rootLogger", "INFO, R");
		    		prop.setProperty("log4j.appender.R", "org.apache.log4j.RollingFileAppender");
		    		prop.setProperty("log4j.appender.R.File", "C:\\ImageShrink.log");
		    		prop.setProperty("log4j.appender.R.layout", "org.apache.log4j.PatternLayout");
		    	 	prop.setProperty("log4j.appender.R.MaxFileSize", "2MB");
 		    		prop.setProperty("log4j.appender.R.MaxBackupIndex", "10");
		    		prop.setProperty("log4j.appender.R.layout.ConversionPattern", "%d [%t] %-5p %c - %m%n");
		    		//save properties to project root folder
		    		prop.store(new FileOutputStream("C:\\log4j.properties"), null);
		 
		    	} catch (IOException ex) {  }
	 	 
			}
		}catch(Exception e){}
		
		/*PropertyConfigurator.configure("C:\\log4j.properties");
		log = Logger.getLogger("<MainClass>");
		*/
        try
        {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e)
        {
        	try{
        	//log.info("Exception while setting Look and Feel");
        	}catch(Exception ex){}
        }
       
        try{
        while(response!=1)
        {
        	if( firstTimeFlag )
        	{
        		masterFile = openFileChooser();
        		firstTimeFlag = false;
        		if (masterFile==null)
        		{
        	        JOptionPane.showMessageDialog(null,"No Location Selected..Thank You for using "+version,copyrightTitle,JOptionPane.INFORMATION_MESSAGE);
        			System.exit(1);
        		}
        	}
        	else{
        		fileChooseResponse = JOptionPane.showConfirmDialog(null,"Select New Location ?",copyrightTitle,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        		if(fileChooseResponse == JOptionPane.YES_OPTION )
        		{
        			masterFile = openFileChooser();
        			if (masterFile==null)
        			{
            	        JOptionPane.showMessageDialog(null,"No Location Selected..Thank You for using "+version,copyrightTitle,JOptionPane.INFORMATION_MESSAGE);
        				System.exit(1);
        			}
        		}
        	}
        i=0;
       dirName=openFrame();
       if(dirName==null ||dirName.length ==0 ){
           JOptionPane.showMessageDialog(null,"No Files Selected..Thank You for using "+version,copyrightTitle,JOptionPane.INFORMATION_MESSAGE);
           System.exit(1);
       }
       try{
      // log.info("Starting Compression ...");
       }catch(Exception ex){}
       long start=System.currentTimeMillis();
        for (File file : dirName) {
    	   countFiles(file);
        }
        try{
        // log.info("Total Image Files : "+fileCount);
        }catch(Exception ex){}
        testsize=testsize/1024; // size in KB
        
     if(dirName!=null)
       { 
    	   fileList=new File[fileCount];
    	   dirList=new File[dirCount];
    	for (File file : dirName) {
    		if(file.isFile())
    		{
    			if(ImageFilter.acceptFile(file.getName()))
    				fileList[fileCntr++]=file;
    		}
    		else
    		{
    			dirList[dirCntr++]=file;
    		}
 
		}
        masterFile.mkdir();
       ProgressBar.createAndShowGUI(fileCount);
       ProgressBar.frame.setCursor(Cursor.WAIT_CURSOR);
       
       if(fileCntr>0){
       for (int k=0;k<fileCntr;k++) {
    	  	 	 createDir(fileList[k],masterFile);
        	}
       }
       if(dirCntr>0)
       {
      		   for(int j=0;j<dirCntr;j++){
    			 createDir(dirList[j],masterFile);
    		 }	
    	  
       }
        long end=System.currentTimeMillis();
       timeSecs=(int)(end-start)/1000;
       timeStr="Time Taken : "+getTimeStr(timeSecs);
        aftersize=aftersize/1024;
 		 displayStr="Original Size : "+setSizeIndicator(testsize)+"\n"+
		 "Compressed Size : "+setSizeIndicator(aftersize)+"\n"+timeStr;
 		 try{
 			/* log.info("Original Size   : "+setSizeIndicator(testsize));
 			 log.info("Compressed Size : "+setSizeIndicator(aftersize));
 			 log.info(timeStr);
 			 log.info(i+" Files Compressed Successfully ...");*/
 		 }catch(Exception ex){}
        Toolkit.getDefaultToolkit().beep();
  	 	JOptionPane.showMessageDialog(ProgressBar.frame,i+" Files Compressed Successfully\n"+displayStr,copyrightTitle,JOptionPane.INFORMATION_MESSAGE);
  	 	if(list.size()!=0)
		 {
  			ProgressBar.frame.dispose();
  			FileManager.showFileManager(list);
  			response=JOptionPane.showConfirmDialog(null,"Do you want to Copy Files ?",copyrightTitle,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
  			if(response==JOptionPane.YES_OPTION)
  			{
  				Iterator itr=list.iterator();
  				try{
  				//log.info("Copying Files to Compressed Folder...");
  				}catch(Exception ex){}
  				FileManager.taskOutput.append("\nCopying Files to Compressed Folder...\n\n");
  				FileManager.frame.setCursor(Cursor.WAIT_CURSOR);
  				 while(itr.hasNext())
  				 {
  					 FileCopyPOJO fil=(FileCopyPOJO)itr.next();
  					 try{
  					// log.info(fil.fromFile+" ------> "+fil.toFile);
  					 }catch(Exception ex){}
  					 FileManager.taskOutput.append(fil.fromFile+" ------> "+fil.toFile+"\n");
   	  				 copy(new File(fil.fromFile), new File(fil.toFile));
  				 }
   				FileManager.frame.setCursor(Cursor.DEFAULT_CURSOR);
    				//leave this here .. incase u want to dispose the window later
   					// use .. FileManager.frame.dispose();
    		        Toolkit.getDefaultToolkit().beep();
    		        try{
    		        //log.info("Files have been copied...");
    		        }catch(Exception ex){}
  	  			response=JOptionPane.showConfirmDialog(null,"Files have been copied..Do you want to Convert more Pics?",copyrightTitle,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
  			}else{
  				FileManager.frame.dispose();
  			response=JOptionPane.showConfirmDialog(null,"Do you want to Convert more Pics?",copyrightTitle,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
  			}
  			 
  	  	 	//response=1;
		 } 	
  	 	else{
		response=JOptionPane.showConfirmDialog(null,"Do you want to Convert more Pics?",copyrightTitle,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
  	 	}
		fileCount=0;
		dirCount=0;
		fileCntr=0;
		dirCntr=0;
		testsize=0;
		timeHour=0;
		timeMins=0;
		timeSecs=0;
		i=0;
		list.clear();
		try{
		//log.info("====================== "+version+" ====================================");
		}catch(Exception ex){}

		 ProgressBar.frame.dispose();
		
       }
     
       //response=1;
 	}
	}catch(Exception e){
		try{
		// log.error("<Exception>",e);
		}catch(Exception ex){}
		 ///ProgressBar.frame.dispose();
		 System.exit(1);	
	}
	 	if(errorFiles.size()!=0)
	 	{
	 		try{
	 		//log.info("Files not compressed :"+errorFiles.size());
	 		}catch(Exception ex){}
	 		FileManager.showErrorFiles(errorFiles);
	 	}
	 	try{
        //log.info(" ***** Thank You for using "+copyrightTitle +" *****");
	 	}catch(Exception ex){}
        JOptionPane.showMessageDialog(null,"Thank You for using "+version,copyrightTitle,JOptionPane.INFORMATION_MESSAGE);
        if(dirName!=null)
        {
        	if(Desktop.isDesktopSupported())
        	{
        		Desktop.getDesktop().open(new File(masterFile.getAbsolutePath()));
        	}
        }
	}
	 
	public static File[] openFrame()
	{
		 JFileChooser fc=null;
		 final JFrame frame=null;
		 fc= new JFileChooser();
		 fc.setDialogTitle("Select Pics to Convert | "+copyrightTitle);
		 fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         fc.setMultiSelectionEnabled(true);
         fc.setDragEnabled(true);
         fc.setFileFilter(new ImageFilter());
	 	 int status=fc.showOpenDialog(frame);  

	 	 if(!(status==JFileChooser.CANCEL_OPTION))
	 	 {
	 		File file[]= fc.getSelectedFiles();
		  	 return file;
	 	 }
	 	 return null;
	}
	public static File openFileChooser()
	{
		 JFileChooser fc=null;
		 final JFrame frame=new JFrame();
		 fc= new JFileChooser();
		 fc.setDialogTitle("Select Location to Store Converted Images | "+copyrightTitle);
		 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         fc.setMultiSelectionEnabled(false);
         fc.setDragEnabled(true);
       
         int status=fc.showOpenDialog(frame);
	 
	 	 if(!(status==JFileChooser.CANCEL_OPTION))
	 	 {
	 		File file = fc.getSelectedFile();
	 		JOptionPane.showMessageDialog(fc,"Storing Converted Images At : "+file.getAbsolutePath(),copyrightTitle,JOptionPane.INFORMATION_MESSAGE);

		  	 return file;
	 	 }
	 	 return null;
	}
	public static void createDir(File submaster,File masterfile) throws IOException,Exception
	{
		Image image=null;
		if(submaster.isFile())
		{
 			if(ImageFilter.acceptFile(submaster.getName()))
			{
 				try{
 				//	log.info("From : "+submaster.getAbsolutePath()+"----> To : "+masterfile.getAbsolutePath());
 				}catch(Exception ex){}
		 	 try{
		 		 image=ImageIO.read(submaster);
		 		
		 	 final File compressedFile=new File(masterfile.getAbsolutePath()+"\\"+submaster.getName());  
			 ImageIO.write((RenderedImage) image,"jpg",compressedFile); 
			 aftersize+=compressedFile.length();
			 i++;
			 SwingUtilities.invokeLater(new Runnable() {
	             public void run() {
	            	 ProgressBar.updateBar(i,compressedFile.getName());
	             }
	         });
			}catch(Exception e)
		 	 {
				try{
		 		// log.info("Error Converting File..Check input File : "+submaster.getAbsolutePath(),e);
				}catch(Exception ex){}
		 		 errorFiles.add(submaster);
		 	 }
 			}
 			else
 			{
  				list.add(new FileCopyPOJO(submaster.getAbsolutePath(),masterfile.getAbsolutePath()+"\\"+submaster.getName()));
  			}
 			
			
		}else if(submaster.isDirectory())
		{
			 
			 File subdir=new File(masterfile.getAbsolutePath()+"\\"+submaster.getName());
			 subdir.mkdir();
 		      File filelist[]=submaster.listFiles();
  		     for (int m = 0; m < filelist.length; m++) {
				createDir(filelist[m], subdir);
			}
			 
		}
	}
	
	static int countFiles(File submaster1)
	{
		if(submaster1.isFile())
		{ 
 			if(ImageFilter.acceptFile(submaster1.getName()))
			{
 				fileCount++;
 				testsize+=submaster1.length();
 				//getFileSize(submaster1);
			}
		}else if(submaster1.isDirectory())
		{
			dirCount++;
		      File filelist[]=submaster1.listFiles();
		      for (File file : filelist) {
		    	  countFiles(file);
			}
		}
 		return fileCount;
	}
 	 
	static String setSizeIndicator(double size)
	{

	if((int)size/(1024*1024)!=0)
	{  
			size=size/(1024*1024);
		sizeIndicator="GB";
     }else if((int)size/(1024)!=0)  
    {
    	size=size/1024;
    	sizeIndicator="MB";
     }
	else{
		sizeIndicator="KB";
	}
 		return df.format(size)+" "+sizeIndicator;
	}
	
	static String getTimeStr(int time)
	{
		if(time/(60*60)!=0){
			timeHour=time/3600;
			timeMins=(time-(3600*timeHour))/60;
			timeSecs=(time-(3600*timeHour)-(60*timeMins));
 			return timeHour+"hr "+timeMins+"min "+timeSecs+"secs";
			
		}
		else if(time/60!=0){
			timeMins=time/60;
			timeSecs=time-(timeMins*60);
 			return timeMins+"min "+timeSecs+"secs";
		}
		else{
 			return time+" secs";			
		}
		
	}
	static void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

 	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	   // Thread.sleep(500);
	}
	  
}
