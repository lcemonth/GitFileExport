package com.export.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class Export {
	Set<String> filePathSet = null;
	
	public Export() {
		
	}
	
	
	/**
	 * 
	 * 
	 * */
	public Export(Set<String> filePathSet) {
		this.filePathSet = filePathSet;
	}
	
	/**
	 * 
	 * @param String fileMkdir 匯出的位置
	 * @param String[] fileStr 
	 * 
	 * */
	public void copyDirectory(String fileMkdir,String[] fileStr) {
		for (int i = 0; i < (fileStr.length-1); i++) {
			
			fileMkdir+="/"+fileStr[i];
			
			File file = new File(fileMkdir);
			if(!file.isDirectory()) {
				if(!file.exists()) {
					file.mkdir();
				}
			}
		}
	}
	
	public void copyFile(Path inputPath,File outputFile) throws IOException {
//	    Path inputPath = new File(GITPATH+"/"+filePath).toPath();
//	    
//	    FileOutputStream fos=null;
//		try {
//			fos = new FileOutputStream(new File(EXPORT_PATH+"/"+filePath));
		FileOutputStream fos = new FileOutputStream(outputFile);
		Files.copy(inputPath, fos);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
}
