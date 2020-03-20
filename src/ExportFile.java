

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.export.gitcmd.GitCmd;
import com.export.io.Export;
import com.export.vo.GitData;

public class ExportFile {


	static final String EXPORT_PATH ="C:\\Users\\USER\\Desktop\\David\\GitFile";	//要匯出的地點
	static final String EXPORT_DATE ="2020-03-18";	//匯出這日期之後的檔案
	static final String PROJECT_NAME ="LAF";		//要匯出的專案
		
	static final String PATH = new File("..").getAbsolutePath();
	static final String GITPATH = PATH + "/" +PROJECT_NAME;

	public static void main(String[] args) throws IOException {
		
		GitCmd gitCmd = new GitCmd(new File(GITPATH));
		
		List<GitData> gitDataList = gitCmd.getCommitData(EXPORT_DATE);
		List<String> updateFilePathList = gitCmd.getUpdateFile(gitDataList);
		
		Export export = new Export(GITPATH,EXPORT_PATH);
		
		for (String filePath : updateFilePathList) {
			export.copyFile(filePath);
		}

		System.out.println("完成");

	}

	
	
	
}
