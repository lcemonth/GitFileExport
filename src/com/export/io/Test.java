package com.export.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


/**
 * @author USER David
 * 
 * */


class Employee {
    private String name ;
    private int age ;
    private String sex;
    private double salary ;
    
    public  Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", sex=" + sex + ", salary=" + salary + "]";
	}
	
}
public class Test {

	static String projectName ="LAF";
	
	static String path = new File("..").getAbsolutePath();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
        Employee employee01 = new Employee("bryan",30000);
        Employee employee02 = new Employee("joe",31000);
        employee02.setSex("N");
        Gson gson =  new Gson();
        String jsonStr1 = gson.toJson(employee01);
        String jsonStr2 = gson.toJson(employee01,Employee.class);
        System.out.println("jsonStr1:"+jsonStr1);
        System.out.println("jsonStr2:"+jsonStr2);
		
		
        Employee employee05  = gson.fromJson(jsonStr1, Employee.class);
        
        System.out.println(employee05.getName());
        System.out.println(employee05.getSalary());
        
		
//		String gitPath = path + "\\" +projectName;
//		File gitFile = new File(gitPath);
		
		
		//git log --pretty=format:%h
		
		// 先找出branch 第一次的時間點
		//$ git log --after="2019-12-23" --pretty=:%h

//		Process pro = Runtime.getRuntime().exec("cmd /k git log --after=\"2019-12-23\" --pretty=:%h ",null,gitFile);
		
		
		//取出GIT Commit 的異動檔案
		//git diff-tree --no-commit-id --name-only -r 3f20cc
//		Process pro = Runtime.getRuntime().exec("cmd /k git diff-tree --no-commit-id --name-only -r 3f20cc ",null,gitFile);
		
		
		String line = "";
//		List<String> list = new ArrayList<String>();
//		BufferedReader br = new BufferedReader(new InputStreamReader( pro.getInputStream(),"BIG5"));
//        while (((line = br.readLine())).length() != 0) {
//            System.out.println(line);
//            line = br.readLine();
//        }
        
        
        
//		List<String> list = new ArrayList<String>();
//		BufferedReader br = new BufferedReader(new InputStreamReader( pro.getInputStream(),"BIG5"));
//		line = br.readLine();
//        while (line.length() != 0) {
//            System.out.println(line);
//            line = br.readLine();
//        }
        
        
//        BufferedReader br = new BufferedReader(new InputStreamReader( pro.getInputStream(),"BIG5"));
//        
//        line = br.readLine();
//        
//        while( line.length() != 0) {
//        	System.out.println(line);
//        	line = br.readLine();
//        	System.out.println(line);
//        }
//        System.out.println("ss");
//        br.close();
        
//        line = buf.readLine();
//        while (line != null) {
//        	line = buf.readLine(); // 一次讀入一行資料
//        	System.out.println(line);
//        	System.out.println("1");
//        }
        
        
//
//        System.out.println("2123");
//        buf.close();
//        buf = null;
//        pro.destroy();

        
        
        //map.put("CRTCD", new String(crtcd.getBytes("ISO-8859-1"), "BIG5"));
		
		
		
//		System.out.println(pro.toString());
//		
//		File file=new File(".");
//		
//		System.out.println(file.getAbsolutePath());
//		
//		String ss[] =file.list();
//		
//		for (int i = 0; i < ss.length; i++) {
//			System.out.println(ss[i]);
//		}
//		
//		Runtime rt = Runtime.getRuntime();
//		Process p = rt.exec("cmd.exe");
//		
//		System.out.println(p.toString());
	}

}
