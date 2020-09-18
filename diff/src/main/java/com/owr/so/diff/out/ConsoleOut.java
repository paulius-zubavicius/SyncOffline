package com.owr.so.diff.out;

import java.util.List;
import java.util.Map;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class ConsoleOut implements IDiffOutput {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RepoDiff> void out(Class<T> type, List<T> data, Map<String, String> rootPathByRepoName) {

		if (type == FileModifiedDiff.class) {
			outModified((List<FileModifiedDiff>) data);
		} else if (type == FileMovedDiff.class) {
			outMoved((List<FileMovedDiff>) data);
		} else if (type == DirMovedDiff.class) {
			outMovedDir((List<DirMovedDiff>) data);
		} else if (type == FileNewDiff.class) {
			outNew((List<FileNewDiff>) data);
		} else if (type == FileDuplicatesDiff.class) {
			outDuplicates((List<FileDuplicatesDiff>) data);
		}

	}

	private void outModified(List<FileModifiedDiff> modifiedFiles) {
		for (FileModifiedDiff modFile : modifiedFiles) {
			System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
			System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
			System.out.println("----------------------------------------------------------------");
		}
	}

	private void outMoved(List<FileMovedDiff> movedFiles) {
		for (FileMovedDiff modFile : movedFiles) {
			System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
			System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
			System.out.println("----------------------------------------------------------------");
		}
	}

	private void outMovedDir(List<DirMovedDiff> movedDirs) {
		for (DirMovedDiff modDir : movedDirs) {
			System.out.println("(" + modDir.getDir1().getRepoName() + ")" + modDir.getDir1().getRelativeDirPath());
			System.out.println("(" + modDir.getDir2().getRepoName() + ")" + modDir.getDir2().getRelativeDirPath());
			System.out.println("----------------------------------------------------------------");
		}

	}

	private void outNew(List<FileNewDiff> newFiles) {
		for (FileNewDiff modFile : newFiles) {
			System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
		}
		System.out.println("----------------------------------------------------------------");
	}

	private void outDuplicates(List<FileDuplicatesDiff> duplicates) {
		for (FileDuplicatesDiff modFile : duplicates) {
			System.out.println("Duplicates group: " + modFile.getChecksum());
			for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
				System.out.println("(" + fileEntity.getRepoName() + ")" + fileEntity.getPath());
			}
			for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
				System.out.println("(" + fileEntity.getRepoName() + ")" + fileEntity.getPath());
			}
		}
		System.out.println("----------------------------------------------------------------");
	}

}
