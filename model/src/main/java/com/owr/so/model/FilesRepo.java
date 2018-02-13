package com.owr.so.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class FilesRepo implements Serializable {

	private static final long serialVersionUID = 8483924072968680391L;

	private HashMap<String, FileMeta> branchData = new HashMap<>();
	
	private transient HashMap<String, List<FileMeta>> branchDataByMd5 = new HashMap<>();

	private transient String name;

	public HashMap<String, FileMeta> getBranchData() {
		return branchData;
	}
	
	public HashMap<String, List<FileMeta>> getBranchDataByMd5() {
		return branchDataByMd5;
	}

	public void setBranchData(HashMap<String, FileMeta> branchData) {
		this.branchData = branchData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
