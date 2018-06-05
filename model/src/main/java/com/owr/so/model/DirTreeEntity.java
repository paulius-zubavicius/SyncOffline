package com.owr.so.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirTreeEntity implements Serializable {

	private static final long serialVersionUID = 9128449236788429925L;

	private String dirTreeRootPath;

	private String osCode;

	private int id;

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
	 * For searching by content checksum
	 */
	private transient Map<String, List<FileEntity>> filesByChecksum = new HashMap<>();

	/**
	 * If sub directory is specified
	 */
	private transient String subDir = "";

	private transient String metaFilePath = null;

	private transient Duration metaFileDuration = null;

	public String getDirTreeRootPath() {
		return dirTreeRootPath;
	}

	public void setDirTreeRootPath(String dirTreeRootPath) {
		this.dirTreeRootPath = dirTreeRootPath;
	}

	public String getOsCode() {
		return osCode;
	}

	public void setOsCode(String osCode) {
		this.osCode = osCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, DirEntity> getDirTree() {
		return dirTree;
	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public Map<String, FileEntity> getFilesByPath() {
		return filesByPath;
	}

	public Map<String, List<FileEntity>> getFilesByChecksum() {
		return filesByChecksum;
	}

	public String getSubDir() {
		return subDir;
	}

	public void setSubDir(String subDir) {
		this.subDir = subDir;
	}

	public String getMetaFilePath() {
		return metaFilePath;
	}

	public void setMetaFilePath(String metaFilePath) {
		this.metaFilePath = metaFilePath;
	}

	public Duration getMetaFileDuration() {
		return metaFileDuration;
	}

	public void setMetaFileDuration(Duration metaFileDuration) {
		this.metaFileDuration = metaFileDuration;
	}

	public void initTransientFields(FileEntity entity) {

		files.add(entity);
		filesByPath.put(entity.getPath().toString(), entity);
		if (!filesByChecksum.containsKey(entity.getChecksum())) {
			filesByChecksum.put(entity.getChecksum(), new ArrayList<>());
		}
		filesByChecksum.get(entity.getChecksum()).add(entity);

	}

}
