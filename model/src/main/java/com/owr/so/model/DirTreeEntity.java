package com.owr.so.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirTreeEntity implements Serializable {

	private static final long serialVersionUID = 9128449236788429925L;

	private String dirTreeRootPath;

	private HashMap<String, DirEntity> dirTree = new HashMap<>();

	/**
	 * Plain list of files: will be removed
	 */
	private transient List<FileEntity> files = new ArrayList<>();

	/**
	 * For searching by plain path
	 */
	private transient Map<String, FileEntity> filesByPath = new HashMap<>();

	/**
	 * For searching by content hashSum
	 */
	private transient Map<String, List<FileEntity>> filesByHashSum = new HashMap<>();

	public String getDirTreeRootPath() {
		return dirTreeRootPath;
	}

	public void setDirTreeRootPath(String dirTreeRootPath) {
		this.dirTreeRootPath = dirTreeRootPath;
	}

	public Map<String, DirEntity> getDirTree() {
		return dirTree;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	public void setFilesByPath(Map<String, FileEntity> filesByPath) {
		this.filesByPath = filesByPath;
	}

	public void setFilesByHashSum(Map<String, List<FileEntity>> filesByHashSum) {
		this.filesByHashSum = filesByHashSum;
	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public Map<String, FileEntity> getFilesByPath() {
		return filesByPath;
	}

	public Map<String, List<FileEntity>> getFilesByHashSum() {
		return filesByHashSum;
	}
	
	public void initTransientFields(FileEntity entity) {

		files.add(entity);
		filesByPath.put(entity.getAbsolutePath(), entity);
		if (!filesByHashSum.containsKey(entity.getHashSum())) {
			filesByHashSum.put(entity.getHashSum(), new ArrayList<>());
		}
		filesByHashSum.get(entity.getHashSum()).add(entity);

	}

}
