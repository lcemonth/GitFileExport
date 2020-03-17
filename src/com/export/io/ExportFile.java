package com.export.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.export.gitcmd.GitCmd;

public class ExportFile {


	static final String EXPORT_PATH ="C:\\Users\\USER\\Desktop\\David\\GitFile";	//要匯出的地點
	static final String EXPORT_DATE ="2020-03-09";	//專案開始日期
	static final String PROJECT_NAME ="LAF";		//要匯出的專案
		
	static final String PATH = new File("..").getAbsolutePath();
	static final String GITPATH = PATH + "/" +PROJECT_NAME;

	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File gitFile = new File(GITPATH);

		GitCmd gitCmd = new GitCmd(gitFile);
		
		Set<String> commitIDSet = gitCmd.getCommitID(EXPORT_DATE);
		Set<String> updateFilePathSet = gitCmd.getUpdateFile(commitIDSet);
		
		
		Export export = new Export();
		
		updateFilePathSet.stream().forEach(filePath ->{
			
			if(filePath.indexOf("/") > -1) {
				String[] filePathSpt=filePath.split("/");
				export.copyDirectory(EXPORT_PATH, filePathSpt);
			}

		    Path inputPath = new File(GITPATH+"/"+filePath).toPath();
		    File outputFile = new File(EXPORT_PATH+"/"+filePath);
		    try {
				export.copyFile(inputPath, outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    
//		    FileOutputStream fos=null;
//			try {
//				fos = new FileOutputStream(new File(EXPORT_PATH+"/"+filePath));
//				Files.copy(inputPath, fos);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		    
		});
	}

	
	
	
}
