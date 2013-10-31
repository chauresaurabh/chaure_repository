package com.Main; 
import java.io.*;

import javax.swing.filechooser.FileFilter;
 
public class ImageFilter extends FileFilter 
	{
	  private static final String[] okFileExtensions = new String[] {"jpg,jpge,bmp,tiff,gif,png"};

		public boolean accept(File name) {
       	 	for (String extension : okFileExtensions)
       	    {
       	      if (name.isDirectory()||name.getName().toLowerCase().endsWith("jpg")||name.getName().endsWith("jpeg")||name.getName().endsWith("bmp"))
       	      {
       	        return true;
       	      }   
       	    }
       		return false;
       		
       	}
	  
		public static boolean acceptFile(String name) {
      	 	   if (name.toLowerCase().endsWith("jpg")||name.endsWith("jpeg")||name.endsWith("bmp"))
       	      {
       	        return true;
       	      }   
       	   
       		return false;
       		
       	}
		
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
