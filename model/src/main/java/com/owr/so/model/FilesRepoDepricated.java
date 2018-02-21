package com.owr.so.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


@Deprecated
public class FilesRepoDepricated implements Serializable {

	private static final long serialVersionUID = 8483924072968680391L;

	private HashMap<String, FileEntity> branchData = new HashMap<>();
	
	private transient HashMap<String, List<FileEntity>> branchDataByMd5 = new HashMap<>();

	private transient String name;

	public HashMap<String, FileEntity> getBranchData() {
		return branchData;
	}
	
	public HashMap<String, List<FileEntity>> getBranchDataByMd5() {
		return branchDataByMd5;
	}

	public void setBranchData(HashMap<String, FileEntity> branchData) {
		this.branchData = branchData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
