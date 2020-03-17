package com.export.gitcmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialArray;

public class GitCmd {
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
	public Set<String> getCommitID(String date) throws IOException {

        return  gitCmdExec("cmd /k git log --after=\""+date+"\" --pretty=%h ");
	}
	
	public Set<String> getUpdateFile(Set<String> commitIDSet) throws IOException{
		Set<String> updateFileSet = new HashSet<String>();
		Iterator<String> it = commitIDSet.iterator();
		
		while (it.hasNext()) {
			updateFileSet.addAll(gitCmdExec("cmd /k git diff-tree --no-commit-id --name-only -r "+it.next()+" "));
		}
		
//		System.out.println("updateFileSet = "+updateFileSet.toString());
		return updateFileSet;
	}
	
	
	private Set<String> gitCmdExec(String cmdExecStr) throws IOException {
		Set<String> cmdExecResultSet = new HashSet<String>();
		
		Process pro = Runtime.getRuntime().exec(cmdExecStr,null,gitFile);
		
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader( pro.getInputStream(),"BIG5"));
		line = br.readLine();
        while (line.length() != 0) {
        	cmdExecResultSet.add(line);
            line = br.readLine();
        }

		System.out.println("cmdExecStr = "+cmdExecStr);
		System.out.println("cmdExecResultSet = "+cmdExecResultSet.toString());
		
		return cmdExecResultSet;
	}
	
	
}
