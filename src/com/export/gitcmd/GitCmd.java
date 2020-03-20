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

		List<String> gitJsonDataList = gitCmdExec("cmd /k git log --after=\""+date+"\" --pretty={\"commitID\":\"%h\",\"commitDate\":\"%ad\",\"commitTitle\":\"%s\"} --date=short ");
		
        return gitJsonDataList.stream()
				.map(jsonStr -> new Gson().fromJson(jsonStr, GitData.class))
				.filter(gitData-> checkCommitDate(gitData.getCommitDate(), date) <=0 )
				.collect(Collectors.toList());
	}
	
	public List<String> getUpdateFile(List<GitData> gitDataList) throws IOException{

		List<String> updateFileList = new ArrayList<String>();
		
		for (GitData gitData : gitDataList) {
			updateFileList.addAll(gitCmdExec("cmd /k git diff-tree --no-commit-id --name-only -r "+gitData.getCommitID()+" "));
		}

		return updateFileList;
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
