package com.owr.so.diff.filters;

import java.util.ArrayList;
import java.util.List;

import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class TreesDiffFilter implements DiffFilter {

	@Override
	public void apply(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult result) {

		treeOneSideDiff(tree1, tree2, result, false);
		treeOneSideDiff(tree2, tree1, result, true);

	}

	private void treeOneSideDiff(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffs,
			boolean mirrorDiffScan) {
		tree1.getFilesByPath().forEach((dirPath1, file1) -> {
			findDiffSubdir(file1, tree1, tree2, diffs, mirrorDiffScan);
		});
	}

	private void findDiffSubdir(FileEntityWrapper file1, RepoMetaData tree1, RepoMetaData tree2,
			DirTreesDiffResult diffs, boolean mirrorDiffScan) {
		// Find by path
		FileEntityWrapper tree2File = tree2.getFilesByPath().get(file1.getPath());

		if (tree2File != null) {

			// Place the same
			if (!mirrorDiffScan) {
				filePathTheSame(file1, tree2File, diffs);
			}
		} else {

			// New or moved
			filePathNotFound(file1, tree1, tree2, diffs, mirrorDiffScan);
		}
	}

	private void filePathTheSame(FileEntityWrapper file1, FileEntityWrapper file2, DirTreesDiffResult diffs) {
		// is modified?
		if (!file1.getFile().getChecksum().equals(file2.getFile().getChecksum())) {

			// If modified time the same - it's conflict
			diffs.getModifiedFiles().add(new FileModifiedDiff(file1, file2));
		}
	}

	private void filePathNotFound(FileEntityWrapper file1, RepoMetaData tree1, RepoMetaData tree2,
			DirTreesDiffResult diffs, boolean mirrorScan) {

		// "Not found" could mean 2 things: It's new or it's moved
		List<FileEntityWrapper> files1 = tree1.getFilesByChecksum().getOrDefault(file1.getFile().getChecksum(),
				new ArrayList<>());
		List<FileEntityWrapper> files2 = tree2.getFilesByChecksum().getOrDefault(file1.getFile().getChecksum(),
				new ArrayList<>());

		/*
		 * Removing with identical paths on both sides, cause it could be files copies
		 * but it doesn't mean a conflict
		 */
		removingNonConflictingFiles(files1, files2);

		// [1:1] Moved (renamed)
		if (files1.size() == 1 && files2.size() == 1) {

			// Two side effect
			if (!mirrorScan) {
				FileEntityWrapper tree2File = files2.get(0);

				diffs.getMovedFiles().add(new FileMovedDiff(file1, tree2File));
			}
		} else
		// [N:0] New (or deleted in "tree2")
		if (files2.isEmpty()) {

			diffs.getNewFiles().add(new FileNewDiff(file1));
		} else
		// [N:N] Duplicates conflict
		if (files1.size() > 1 && files2.size() > 1) {

			/**
			 * <pre>
			 *  [abc]  \ / [abc]
			 *          ?
			 *  [abc]  / \ [abc]
			 * </pre>
			 */

			// Two side effects
			if (!mirrorScan) {
				diffs.getDuplicates().add(new FileDuplicatesDiff(files1, files2));
			}
		} else
		// [1:N] [N:1]
		if ((files1.size() == 1 && files2.size() > 1) || (files1.size() > 1 && files2.size() == 1)) {
			/**
			 * <pre>
			 *         / ? [abc]
			 *  [abc] -
			 *         \ ? [abc]
			 *
			 *
			 *  [abc] ? \
			 *           - [abc]
			 *  [abc] ? /
			 *
			 * </pre>
			 */

			// One side effect
			diffs.getDuplicates().add(new FileDuplicatesDiff(files1, files2));
		} else {
			throw new RuntimeException("Unexpected case");
		}
	}

	private void removingNonConflictingFiles(List<FileEntityWrapper> files1, List<FileEntityWrapper> files2) {

		if (files1.size() > 1 && files2.size() > 1) {

			for (int i1 = 0; i1 < files1.size(); i1++) {
				for (int i2 = 0; i2 < files2.size(); i2++) {

					if (files1.get(i1).getPath().equals(files2.get(i2).getPath())) {
						files1.remove(i1);
						files2.remove(i2);

						i1--;
						i2--;

						if (i1 < 0) {
							i1 = 0;
						}

						if (i2 < 0) {
							i2 = 0;
						}
					}
				}
			}
		}
	}

}
