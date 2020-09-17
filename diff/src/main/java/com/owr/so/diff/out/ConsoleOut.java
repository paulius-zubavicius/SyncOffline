package com.owr.so.diff.out;

import java.util.List;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class ConsoleOut implements IDiffOutput {

	@Override
	public void outModified(List<FileModifiedDiff> modifiedFiles) {
		if (!modifiedFiles.isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Modified files");
			System.out.println("================================================================");

			for (FileModifiedDiff modFile : modifiedFiles) {

				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.getAction() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}
	}

	@Override
	public void outMoved(List<FileMovedDiff> movedFiles) {
		if (!movedFiles.isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Moved files");
			System.out.println("================================================================");

			for (FileMovedDiff modFile : movedFiles) {

				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.getAction() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}
	}

	@Override
	public void outNew(List<FileNewDiff> newFiles) {
		if (!newFiles.isEmpty()) {
			System.out.println("================================================================");
			System.out.println("New files");
			System.out.println("================================================================");

			for (FileNewDiff modFile : newFiles) {
				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("----------------------------------------------------------------");
			}
		}
	}

	@Override
	public void outDuplicates(List<FileDuplicatesDiff> duplicates) {
		if (!duplicates.isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Duplicates");
			System.out.println("================================================================");

			System.out.println();

			for (FileDuplicatesDiff modFile : duplicates) {

				System.out.println("Duplicate: ");
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

}
