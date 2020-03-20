package com.export.vo;

public class GitData {
	private String commitID;
	private String commitDate;
	private String commitTitle;
	public String getCommitID() {
		return commitID;
	}
	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}
	public String getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
	public String getCommitTitle() {
		return commitTitle;
	}
	public void setCommitTitle(String commitTitle) {
		this.commitTitle = commitTitle;
	}
	@Override
	public String toString() {
		return "GitData [commitID=" + commitID + ", commitDate=" + commitDate + ", commitTitle=" + commitTitle + "]";
	}
}
