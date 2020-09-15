package com.owr.so.diff.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoMetaData {

	/**
	 * Plain list of files: will be removed
	 */
	private List<FileEntityWrapper> files = new ArrayList<>();

	/**
	 * For searching by plain path
	 */
	private Map<String, FileEntityWrapper> filesByPath = new HashMap<>();

	/**
	 * For searching by content checksum
	 */
	private Map<String, List<FileEntityWrapper>> filesByChecksum = new HashMap<>();

	/**
	 * repo Id by checksum
	 */
//	private Map<String, String> repoIdByChecksum = new HashMap<>();
	private String repoName;

	public RepoMetaData(RepoData data, String repoName) {
		this.repoName = repoName;
//		data.getTree().forEach((relativeDirPath, dirContent) -> {
//			dirContent.forEach(fileEntity -> repoIdByChecksum.put(fileEntity.getChecksum(), repoName));
//		});

		data.getTree().forEach((relativeDirPath, dirContent) -> {
			dirContent.forEach(fileEntity -> initTransientFields(repoName, relativeDirPath, fileEntity));
		});
	}

//	public RepoMetaData(RepoData data) {
//		data.getTree().forEach((relativeDirPath, dirContent) -> {
//			dirContent.forEach(fileEntity -> initTransientFields(fileEntity, relativeDirPath));
//		});
//	}

	/**
	 * If sub directory is specifie. Could be value like ""; "/dir"; "/dir/dir"
	 * pattern for dir level: [/dirName]
	 */

	private void initTransientFields(String repoName, String dirPath, FileEntity entity) {
		FileEntityWrapper wrapper = new FileEntityWrapper(repoName, dirPath, entity);
		files.add(wrapper);
		filesByPath.put(dirPath + "/" + entity.getName(), wrapper);
		if (!filesByChecksum.containsKey(entity.getChecksum())) {
			filesByChecksum.put(entity.getChecksum(), new ArrayList<>());
		}
		filesByChecksum.get(entity.getChecksum()).add(wrapper);

	}

	public String getRepoName() {
		return repoName;
	}

	public List<FileEntityWrapper> getFiles() {
		return files;
	}

//	public void setFiles(List<FileEntity> files) {
//		this.files = files;
//	}

	public Map<String, FileEntityWrapper> getFilesByPath() {
		return filesByPath;
	}

//	public void setFilesByPath(Map<String, FileEntity> filesByPath) {
//		this.filesByPath = filesByPath;
//	}

	public Map<String, List<FileEntityWrapper>> getFilesByChecksum() {
		return filesByChecksum;
	}

//	public void setFilesByChecksum(Map<String, List<FilePathEntity>> filesByChecksum) {
//		this.filesByChecksum = filesByChecksum;
//	}

}
