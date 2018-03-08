package com.owr.so.merge.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

/**
 * @author Paulius Zubavicius
 *
 *         Finds and collects repositories tree differences
 */
public class ReposDifferences {

	private TreesDiffCollections diffs;

	/**
	 * @param tree1
	 * @param tree2
	 * @param subDir
	 *            - could be value like ""; "/dir"; "/dir/dir" pattern for dir
	 *            level: [/dirName]
	 */
	public TreesDiffCollections treesDifferences(DirTreeEntity tree1, DirTreeEntity tree2) {

		diffs = new TreesDiffCollections();

		treeOneSideDiff(tree1, tree2, false);
		treeOneSideDiff(tree2, tree1, true);

		return diffs;
	}

	private void treeOneSideDiff(DirTreeEntity tree1, DirTreeEntity tree2, boolean mirrorDiffScan) {

		// Files
		List<FileEntity> tree1Files = tree1.getFiles();
		for (FileEntity tree1File : tree1Files) {

			if (tree1File.getPath().startsWith(tree1.getSubDir())) {
				findDiffSubdir(tree1File, tree1, tree2, mirrorDiffScan);
			}
		}

		// Empty DIRs
		Set<String> tree1Dirs = tree1.getDirTree().keySet();

		for (String tree1Dir : tree1Dirs) {
			if (tree1Dir.startsWith(tree1.getSubDir())) {
				findNewEmptyDir(tree1Dir, tree1, tree2);
			}
		}
	}

	private void findNewEmptyDir(String tree1Dir, DirTreeEntity tree1, DirTreeEntity tree2) {
		boolean notExistsInTree2 = tree2.getDirTree().get(tree1Dir) == null;
		boolean emptyInTree1 = tree1.getDirTree().get(tree1Dir).getFiles().isEmpty();

		if (notExistsInTree2 && emptyInTree1) {
			diffs.getNewDirs().add(new DirNewDiff(tree1.getDirTree().get(tree1Dir)));
		}

	}

	private void findDiffSubdir(FileEntity tree1File, DirTreeEntity tree1, DirTreeEntity tree2,
			boolean mirrorDiffScan) {
		// Find by path
		FileEntity tree2File = tree2.getFilesByPath().get(tree1File.getPath());

		if (tree2File != null) {

			// Place the same
			filePathTheSame(tree1File, tree2File, mirrorDiffScan);
		} else {

			// New or moved
			filePathNotFound(tree1File, tree1, tree2, mirrorDiffScan);
		}
	}

	private void filePathTheSame(FileEntity tree1File, FileEntity tree2File, boolean mirrorDiffScan) {
		if (!mirrorDiffScan) {
			// is modified?
			if (!tree1File.getChecksum().equals(tree2File.getChecksum())) {

				// If modified time the same - it's conflict
				diffs.getModifiedFiles().add(
						new FileModifiedDiff(tree1File, tree2File, tree1File.getModified() == tree1File.getModified()));
			}
		}
	}

	private void filePathNotFound(FileEntity tree1File, DirTreeEntity tree1, DirTreeEntity tree2, boolean mirrorScan) {

		// "Not found" could mean 2 things: It's new or it's moved

		List<FileEntity> files1 = findByChecksum(tree1File, tree1);
		List<FileEntity> files2 = findByChecksum(tree1File, tree2);

		/*
		 * Removing with identical paths on both sides, cause it could be files copies
		 * but it doesn't mean a conflict
		 */
		removingNotConflictingFiles(files1, files2);

		// [1:1] Moved (renamed)
		if (files1.size() == 1 && files2.size() == 1) {

			// Two side effect
			if (!mirrorScan) {
				FileEntity tree2File = files2.get(0);

				diffs.getMovedFiles().add(
						new FileMovedDiff(tree1File, tree2File, tree1File.getAccessed() == tree2File.getAccessed()));
			}
		} else
		// [N:0] New (or deleted in "tree2")
		if (files2.isEmpty()) {

			diffs.getNewFiles().add(new FileNewDiff(tree1File));
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

	private void removingNotConflictingFiles(List<FileEntity> files1, List<FileEntity> files2) {

		if (files1.size() > 1 && files2.size() > 1) {

			for (int i1 = 0; i1 < files1.size(); i1++) {
				for (int i2 = 0; i2 < files2.size(); i2++) {
					if (files1.get(i1).getPath().equals(files2.get(i2).getPath())) {
						files1.remove(i1);
						files2.remove(i2);
						i1--;
						i2--;
					}
				}
			}
		}
	}

	private List<FileEntity> findByChecksum(FileEntity tree1File, DirTreeEntity tree1) {

		List<FileEntity> result = tree1.getFilesByChecksum().get(tree1File.getChecksum());
		if (result == null) {
			result = new ArrayList<>();
		}
		return result;
	}

}
