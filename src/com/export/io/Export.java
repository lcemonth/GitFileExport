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
	String exportPath;
	String gitPath;
	
	public Export() {
		
	}
	
	/**
	 * gitPath 專案位置
	 * exportPath 要匯出的地點
	 * */
	public Export(String gitPath,String exportPath) {
		this.gitPath = gitPath;
		this.exportPath = exportPath;
	}

	/**
	 * 
	 * */
	public void copyFile(String filePath) throws IOException {
		
		if(filePath.indexOf("/") > -1) {
			copyDirectory(filePath);
		}
		
	    Path inputPath = new File(gitPath+"/"+filePath).toPath();
	    File outputFile = new File(exportPath+"/"+filePath);
	    
		FileOutputStream fos = new FileOutputStream(outputFile);
		Files.copy(inputPath, fos);
		
	}
	public void copyDirectory(String filePath) {
		String[] filePathSpt=filePath.split("/");
		String fileMkdir = exportPath;
		
		for (int i = 0 ,length = (filePathSpt.length-1) ; i < length; i++) {
			
			fileMkdir+="/"+filePathSpt[i];
			
			File file = new File(fileMkdir);
			if(!file.isDirectory()) {
				if(!file.exists()) {
					file.mkdir();
				}
			}
		}
	}
}
