package com.owr.so.diff.out;

import java.util.List;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class ConsoleShellOut implements IDiffOutput {

	@Override
	public void outModified(List<FileModifiedDiff> modifiedFiles) {
		// TODO Auto-generated method stub

	}

	@Override
	public void outMoved(List<FileMovedDiff> movedFiles) {
		// TODO Auto-generated method stub

	}

	@Override
	public void outNew(List<FileNewDiff> newFiles) {
		// TODO Auto-generated method stub

	}

	@Override
	public void outDuplicates(List<FileDuplicatesDiff> duplicates) {
		int group = 0;
		for (FileDuplicatesDiff modFile : duplicates) {

			System.out.println("[Group: " + ++group + "]");

			System.out.println(modFile.getFiles1() != null ? "[" + modFile.getFiles1().get(0).getRepoName() + "]" : "");
			for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
				System.out.println("rm '" + fileEntity.getRepoRootDir() + fileEntity.getPath() + "'");
			}

			System.out.println(modFile.getFiles2() != null ? "[" + modFile.getFiles2().get(0).getRepoName() + "]" : "");
			for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
				System.out.println("rm '" + fileEntity.getRepoRootDir() + fileEntity.getPath() + "'");
			}
			System.out.println();
		}

	}

}
