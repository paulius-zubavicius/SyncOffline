package com.owr.so.diff.out;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class ConsoleShellOut implements IDiffOutput {

	private static String GROUP = "@ Group: ";
	private static String OPT = "$[Option]: ";

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RepoDiff> void out(Class<T> type, List<T> data, Map<String, String> rootPathByRepoName) {

		if (type == FileModifiedDiff.class) {
			outModified((List<FileModifiedDiff>) data, rootPathByRepoName);
		} else if (type == FileMovedDiff.class) {
			outMoved((List<FileMovedDiff>) data, rootPathByRepoName);
		} else if (type == FileNewDiff.class) {
			outNew((List<FileNewDiff>) data, rootPathByRepoName);
		} else if (type == FileDuplicatesDiff.class) {
			outDuplicates((List<FileDuplicatesDiff>) data, rootPathByRepoName);
		}

	}

	public void outModified(List<FileModifiedDiff> modifiedFiles, Map<String, String> rootPathByRepoName) {

		int group = 0;
		for (FileModifiedDiff modFile : modifiedFiles) {

			int modifiedCase = Long.compare(modFile.getFile1().getFile().getModified(),
					modFile.getFile2().getFile().getModified());
			group++;
			if (modifiedCase < 0) {
				modifiedFile(group, modFile.getFile2(), modFile.getFile1(), "replace old one", "rollback",
						rootPathByRepoName);
			} else if (modifiedCase > 0) {
				modifiedFile(group, modFile.getFile1(), modFile.getFile2(), "replace old one", "rollback",
						rootPathByRepoName);
			} else {
				modifiedFile(group, modFile.getFile1(), modFile.getFile2(), "replace", "replace", rootPathByRepoName);
			}
		}

		System.out.println();

	}

	private void modifiedFile(int group, FileEntityWrapper newerFile, FileEntityWrapper olderFile, String newerStr,
			String olderString, Map<String, String> rootPathByRepoName) {
		String rootNewer1 = rootPathByRepoName.get(newerFile.getRepoName());
		String rootOlder2 = rootPathByRepoName.get(olderFile.getRepoName());

		System.out.println(GROUP + group);
		System.out.println();
		System.out.println(OPT + newerStr);
		System.out.println("rm '" + rootOlder2 + olderFile.getPath() + "'");
		System.out.println("cp '" + rootNewer1 + newerFile.getPath() + "' '" + rootOlder2 + "'");
		System.out.println();
		System.out.println(OPT + olderString);
		System.out.println("rm '" + rootNewer1 + newerFile.getPath() + "'");
		System.out.println("cp '" + rootOlder2 + olderFile.getPath() + "' '" + rootNewer1 + "'");
		System.out.println();
	}

	public void outMoved(List<FileMovedDiff> movedFiles, Map<String, String> rootPathByRepoName) {

		int group = 0;
		for (FileMovedDiff modFile : movedFiles) {

			int moveCase = Long.compare(modFile.getFile1().getFile().getAccessed(),
					modFile.getFile2().getFile().getAccessed());
			group++;
			if (moveCase < 0) {
				movedFile(group, modFile.getFile2(), modFile.getFile1(), "rename to as new one", "rollback to original",
						rootPathByRepoName);
			} else if (moveCase > 0) {
				movedFile(group, modFile.getFile1(), modFile.getFile2(), "rename to as new one", "rollback to original",
						rootPathByRepoName);
			} else {
				movedFile(group, modFile.getFile1(), modFile.getFile2(), "rename", "rename", rootPathByRepoName);
			}
		}

		System.out.println();

	}

	private void movedFile(int group, FileEntityWrapper newerFile, FileEntityWrapper olderFile, String newerStr,
			String olderString, Map<String, String> rootPathByRepoName) {
		String rootNewer1 = rootPathByRepoName.get(newerFile.getRepoName());
		String rootOlder2 = rootPathByRepoName.get(olderFile.getRepoName());

		System.out.println(GROUP + group);
		System.out.println();
		System.out.println(OPT + newerStr);
		System.out.println("mv '" + rootOlder2 + olderFile.getPath() + "' '" + rootOlder2 + newerFile.getPath());
		System.out.println();
		System.out.println(OPT + olderString);
		System.out.println("mv '" + rootNewer1 + newerFile.getPath() + "' '" + rootNewer1 + olderFile.getPath());
		System.out.println();
	}

	public void outNew(List<FileNewDiff> newFiles, Map<String, String> rootPathByRepoName) {

		for (FileNewDiff modFile : newFiles) {
			System.out.println(GROUP + modFile.getFile1().getPath());
			System.out.println();
			
			System.out.println(OPT + "copy to new repo");
			String rootPath = rootPathByRepoName.get(modFile.getFile1().getRepoName());
			String rootPathOther = rootPathByRepoName
					.get(opositeRepoName(rootPathByRepoName, modFile.getFile1().getRepoName()));
			System.out.println("mkdir -r '" + rootPathOther + "'");
			System.out.println("cp '" + rootPath + modFile.getFile1().getPath() + "' '" + rootPathOther
					+ modFile.getFile1().getPath() + "'");
			
			System.out.println();
			System.out.println(OPT + "rollback");
			System.out.println("rm '" + rootPath + modFile.getFile1().getPath() + "'");
			System.out.println();
		}

//		System.out.println();
//		System.out.println(OPT + "remove from original");
//		for (FileNewDiff modFile : newFiles) {
//			String repoPath = rootPathByRepoName.get(modFile.getFile1().getRepoName());
//			System.out.println("rm '" + repoPath + modFile.getFile1().getPath() + "'");
//		}
		

	}

	private String opositeRepoName(Map<String, String> rootPathByRepoName, String repoName) {
		Set<String> repoNames = new HashSet<>();
		repoNames.addAll(rootPathByRepoName.keySet());
		repoNames.remove(repoName);
		String otherRepoName = repoNames.iterator().next();
		return otherRepoName;
	}

	public void outDuplicates(List<FileDuplicatesDiff> duplicates, Map<String, String> rootPathByRepoName) {
		int group = 0;
		for (FileDuplicatesDiff modFile : duplicates) {
			group++;
			System.out.println(GROUP + group);
			System.out.println();
			System.out.println(OPT + "remove from repo: "
					+ (modFile.getFiles1() != null ? "[" + modFile.getFiles1().get(0).getRepoName() + "]" : ""));

			for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
				String rootPath = rootPathByRepoName.get(fileEntity.getRepoName());
				System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
			}

			System.out.println();
			System.out.println(OPT + "remove from repo: "
					+ (modFile.getFiles2() != null ? "[" + modFile.getFiles2().get(0).getRepoName() + "]" : ""));

			for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
				String rootPath = rootPathByRepoName.get(fileEntity.getRepoName());
				System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
			}

			System.out.println();
		}
	}

}
