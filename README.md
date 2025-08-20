ExportFile.java

```=java


import java.io.File;
import java.io.IOException;
import java.util.List;

import com.export.gitcmd.GitCmd;
import com.export.io.Export;
import com.export.vo.GitData;

public class ExportFile {


	static final String EXPORT_PATH ="C:\\Users\\USER\\Desktop\\David\\GitFile2";	//要匯出的地點
	static final String EXPORT_DATE ="2021-03-29";	//匯出這日期之後的檔案
//	static final String PROJECT_NAME ="lafcspapi";		//要匯出的專案
	static final String PROJECT_NAME ="LAF";		//要匯出的專案
//	static final String PROJECT_NAME ="lafcsp";		//要匯出的專案
		
	static final String PATH = new File("..").getAbsolutePath();
	static final String GITPATH = PATH + "/" +PROJECT_NAME;

	static final String[] notExportId = {
			//"185a103","f285163","9a0d7b9","026d7b8","de6dd21","6780bc6"
//			"3d25662"
//			"772cccb"
//			"c05a1c6"
//			"1a23140",
//			"9ea006d"
			"8d029f6"
			
	};
	
	public static void main(String[] args) throws IOException {

		GitCmd gitCmd = new GitCmd(new File(GITPATH));
		
		List<GitData> gitDataList = gitCmd.getCommitData(EXPORT_DATE);
		List<String> updateFilePathList = gitCmd.getUpdateFile(gitDataList,notExportId);
		
		

		deleteAll(new File(EXPORT_PATH));
		init(new File(EXPORT_PATH));
		
		
		Export export = new Export(GITPATH,EXPORT_PATH);
		
		for (String filePath : updateFilePathList) {
			export.copyFile(filePath);
		}

		System.out.println("完成");

	}

	
	public static void init(File f) {
        if (f.mkdir()) {
            System.out.println("建立成功");
        } else {
            System.out.println("建立失敗");
        }
	}
	
	 public static void deleteAll(File path) {
	        if (!path.exists()) {
	            return;
	        }
	        if (path.isFile()) {
	            path.delete();
	            return;
	        }
	        File[] files = path.listFiles();
	        for (int i = 0; i < files.length; i++) {
	            deleteAll(files[i]);
	        }
	        path.delete();
	    }
	
}

```


ExportFile.java
```=java
package com.export.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.export.gitcmd.GitCmd;
import com.export.vo.GitData;

public class ExportFile {


	static final String EXPORT_PATH ="C:\\Users\\USER\\Desktop\\David\\GitFile";	//要匯出的地點
	static final String EXPORT_DATE ="2020-07-29";	//匯出這日期之後的檔案
	static final String PROJECT_NAME ="LAF";		//要匯出的專案
		
	static final String PATH = new File("..").getAbsolutePath();
	static final String GITPATH = PATH + "/" +PROJECT_NAME;
	
	static String[] notExportId = {
		""
	};
	
	public static void main(String[] args) throws IOException {
		
		GitCmd gitCmd = new GitCmd(new File(GITPATH));
		
		List<GitData> gitDataList = gitCmd.getCommitData(EXPORT_DATE);
		List<String> updateFilePathList = gitCmd.getUpdateFile(gitDataList,notExportId);
		
		Export export = new Export(GITPATH,EXPORT_PATH);
		
		for (String filePath : updateFilePathList) {
			export.copyFile(filePath);
		}

		System.out.println("完成");

	}
	
}
```



GitCmd.java

```=java
package com.export.gitcmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialArray;

import com.export.vo.GitData;
import com.google.gson.Gson;

public class GitCmd<E> {
	File gitFile = null;
	
	public GitCmd(){
		
	}
	
	public GitCmd(File file){
		gitFile = file;
	}
	
	
	/**
	 * @param String date 日期
	 * @throws IOException 
	 * */
	public List<GitData> getCommitData(String date) throws IOException {
		String minusDays = minusDays(date);
		List<String> gitJsonDataList = gitCmdExec("cmd /k git log --after=\""+minusDays+"\" --pretty={\"commitID\":'%h',\"commitDate\":'%ad',\"commitTitle\":'%s'} --date=short ");
		
        return gitJsonDataList.stream()
				.map(jsonStr -> new Gson().fromJson(jsonStr, GitData.class))
				.filter(gitData-> checkCommitDate(gitData.getCommitDate(), date) <=0 )
				.collect(Collectors.toList());
	}
	
	public List<String> getUpdateFile(List<GitData> gitDataList,String[] notExportId) throws IOException{

		List<String> updateFileList = new ArrayList<String>();

		for (GitData gitData : gitDataList) {
			boolean	notExport = true;
			for (int i = 0; i < notExportId.length; i++) {
				if(gitData.getCommitID().equals(notExportId[i])) {
					notExport= false;
				} 
			}
			
			if(notExport) {
				updateFileList.addAll(gitCmdExec("cmd /k git diff-tree --no-commit-id --name-only -r "+gitData.getCommitID()+" "));
			}
		}

		return updateFileList;
	}
	
	/**
	 * 收尋不到當天commit 紀錄的問題，故減少一天 ，其他天用程式排除
	 * 
	 * */
	private String minusDays(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime minusDate = LocalDateTime.parse(date+" 00:00:00", formatter);
		
		return minusDate.minusDays(1)
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	private int checkCommitDate(String gitDate,String checkDate){
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime gitDateLDT = LocalDateTime.parse(gitDate+" 00:00:00", formatter);
		LocalDateTime checkDateLDT = LocalDateTime.parse(checkDate+" 00:00:00", formatter);

		return checkDateLDT.compareTo(gitDateLDT);
	}
	
	
	private List<String> gitCmdExec(String cmdExecStr) throws IOException {
		List<String> cmdExecResultList = new ArrayList<String>();
		
		Process pro = Runtime.getRuntime().exec(cmdExecStr,null,gitFile);
		
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader( pro.getInputStream(),"UTF8"));
		
		line = br.readLine();
		while (line.length() != 0) {
        	cmdExecResultList.add(line);
            line = br.readLine();
        }

		System.out.println("cmdExecStr = "+cmdExecStr);
		System.out.println("cmdExecResultList = "+cmdExecResultList.toString());

		return cmdExecResultList;
	}
	
	
}

```

