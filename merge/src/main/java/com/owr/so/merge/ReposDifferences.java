package com.owr.so.merge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.owr.so.merge.diff.DirNewDiff;
import com.owr.so.merge.diff.FileDuplicatesDiff;
import com.owr.so.merge.diff.FileModifiedDiff;
import com.owr.so.merge.diff.FileMovedDiff;
import com.owr.so.merge.diff.FileNewDiff;
import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

/**
 * @author Paulius Zubavicius
 *
 *         Class for repositories differences.
 * 
 * 
 *
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
	public TreesDiffCollections treesDifferences(DirTreeEntity tree1, DirTreeEntity tree2, String subDir) {

		diffs = new TreesDiffCollections();

		treeDiff(tree1, tree2, subDir, false);
		treeDiff(tree2, tree1, subDir, true);

		return diffs;
	}

	private void treeDiff(DirTreeEntity tree1, DirTreeEntity tree2, String subDir, boolean mirrorDiffScan) {

		// Files
		List<FileEntity> tree1Files = tree1.getFiles();
		for (FileEntity tree1File : tree1Files) {

			if (tree1File.getPath().startsWith(subDir)) {
				findDiffSubdir(tree1File, tree1, tree2, mirrorDiffScan);
			}
		}

		// Empty DIRs
		Set<String> tree1Dirs = tree1.getDirTree().keySet();

		for (String tree1Dir : tree1Dirs) {
			if (tree1Dir.startsWith(subDir)) {
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
			if (mirrorDiffScan) {
				filePathTheSame(tree1File, tree2File, mirrorDiffScan);
			}
		} else {

			// New or moved
			filePathNotFound(tree1File, tree1, tree2, mirrorDiffScan);
		}
	}

	private void filePathTheSame(FileEntity tree1File, FileEntity tree2File, boolean mirrorDiffScan) {

		// is content different?
		if (!tree1File.getChecksum().equals(tree2File.getChecksum())) {

			// If modified time the same - it's conflict
			diffs.getModifiedFiles().add(
					new FileModifiedDiff(tree1File, tree1File, tree1File.getModified() == tree1File.getModified()));
		}
	}

	private void filePathNotFound(FileEntity tree1File, DirTreeEntity tree1, DirTreeEntity tree2, boolean mirrorScan) {

		// "Not found" could mean 2 things: It's new or it's moved

		List<FileEntity> files1 = findByChecksum(tree1File, tree1);
		List<FileEntity> files2 = findByChecksum(tree1File, tree2);
		FileEntity tree2File;

		if (files1.size() == 1 && files2.size() == 1) {
			// Moved (renamed)

			tree2File = files2.get(0);

			diffs.getMovedFiles()
					.add(new FileMovedDiff(tree1File, tree2File, tree1File.getAccessed() == tree2File.getAccessed()));

		} else if (files2.isEmpty()) {

			// New (or deleted in "tree2")
			diffs.getNewFiles().add(new FileNewDiff(tree1File));
		} else {

			/**
			 * <pre>
			 *         / ? [abc]
			 *  [abc] - 
			 *         \ ? [abc]
			 *         
			 *         
			 *  [abc]  \ / [abc]
			 *          ?
			 *  [abc]  / \ [abc]
			 *  
			 *  
			 *  [abc] ? \  
			 *           - [abc]
			 *  [abc] ? /
			 * 
			 * </pre>
			 */

			if (!mirrorScan) {
				diffs.getDuplicates().add(new FileDuplicatesDiff(files1, files2));
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
