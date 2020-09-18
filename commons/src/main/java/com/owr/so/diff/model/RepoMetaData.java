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

	private String repoName;

	private String repoRootDir;

	public RepoMetaData(RepoData data, String repoRootDir, String repoName) {
		this.repoName = repoName;
		this.repoRootDir = repoRootDir;
		data.getTree().forEach((relativeDirPath, dirContent) -> {
			dirContent.forEach(fileEntity -> initTransientFields(repoName, relativeDirPath, fileEntity));
		});
	}

	/**
	 * If sub directory is specifie. Could be value like ""; "/dir"; "/dir/dir"
	 * pattern for dir level: [/dirName]
	 */

	private void initTransientFields(String repoName, String relativeDirPath, FileEntity entity) {
		FileEntityWrapper wrapper = new FileEntityWrapper(repoName, relativeDirPath, entity);
		files.add(wrapper);
		filesByPath.put(relativeDirPath + "/" + entity.getName(), wrapper);
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

	public Map<String, FileEntityWrapper> getFilesByPath() {
		return filesByPath;
	}

	public Map<String, List<FileEntityWrapper>> getFilesByChecksum() {
		return filesByChecksum;
	}

	public String getRepoRootDir() {
		return repoRootDir;
	}

}
