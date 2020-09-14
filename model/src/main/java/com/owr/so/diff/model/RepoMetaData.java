package com.owr.so.diff.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoMetaData {

	/**
	 * Plain list of files: will be removed
	 */
	private List<FileEntity> files = new ArrayList<>();

	/**
	 * For searching by plain path
	 */
	private Map<String, FileEntity> filesByPath = new HashMap<>();

	/**
	 * For searching by content checksum
	 */
	private Map<String, List<FileEntity>> filesByChecksum = new HashMap<>();

	public RepoMetaData(RepoData data) {
		data.getTrees().forEach((treeName, tree) -> {
			tree.forEach((dirPath, dirContent) -> {
				dirContent.getFiles().forEach(fileEntity -> initTransientFields(fileEntity, dirPath));
			});
		});
	}

	/**
	 * If sub directory is specifie. Could be value like ""; "/dir"; "/dir/dir"
	 * pattern for dir level: [/dirName]
	 */

	private void initTransientFields(FileEntity entity, String dirPath) {

		files.add(entity);
		filesByPath.put(dirPath + "/" + entity.getName(), entity);
		if (!filesByChecksum.containsKey(entity.getChecksum())) {
			filesByChecksum.put(entity.getChecksum(), new ArrayList<>());
		}
		filesByChecksum.get(entity.getChecksum()).add(entity);

	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	public Map<String, FileEntity> getFilesByPath() {
		return filesByPath;
	}

	public void setFilesByPath(Map<String, FileEntity> filesByPath) {
		this.filesByPath = filesByPath;
	}

	public Map<String, List<FileEntity>> getFilesByChecksum() {
		return filesByChecksum;
	}

	public void setFilesByChecksum(Map<String, List<FileEntity>> filesByChecksum) {
		this.filesByChecksum = filesByChecksum;
	}

}
