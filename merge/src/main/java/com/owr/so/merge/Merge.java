package com.owr.so.merge;

import com.owr.so.merge.diff.DirNewDiff;
import com.owr.so.merge.diff.FileDuplicatesDiff;
import com.owr.so.merge.diff.FileModifiedDiff;
import com.owr.so.merge.diff.FileMovedDiff;
import com.owr.so.merge.diff.FileNewDiff;
import com.owr.so.merge.diff.ReposDifferences;
import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

public class Merge {

	public void mergeFlow(DirTreeEntity tree1, DirTreeEntity tree2, boolean guiMode) {

		ReposDifferences diff = new ReposDifferences();

		TreesDiffCollections diffCollection = diff.treesDifferences(tree1, tree2);

		printDebug(diffCollection);

		// Manual input

		// auto merge (renamed dir only)

		// output module

	}

	private void printDebug(TreesDiffCollections diffCollection) {

		if (!diffCollection.getModifiedFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Modified files");
			System.out.println("================================================================");

			for (FileModifiedDiff modFile : diffCollection.getModifiedFiles()) {

				System.out.println("(" + modFile.getFile1().getRepoId() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoId() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.isInConflict() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getMovedFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Moved files");
			System.out.println("================================================================");

			for (FileMovedDiff modFile : diffCollection.getMovedFiles()) {

				System.out.println("(" + modFile.getFile1().getRepoId() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoId() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.isInConflict() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getNewFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("New files");
			System.out.println("================================================================");

			for (FileNewDiff modFile : diffCollection.getNewFiles()) {
				System.out.println("(" + modFile.getFile1().getRepoId() + ")" + modFile.getFile1().getPath());
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getDuplicates().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Duplicates");
			System.out.println("================================================================");

			for (FileDuplicatesDiff modFile : diffCollection.getDuplicates()) {

				for (FileEntity fileEntity : modFile.getFiles1()) {
					System.out.println("(" + fileEntity.getRepoId() + ")" + fileEntity.getPath());
				}

				for (FileEntity fileEntity : modFile.getFiles2()) {
					System.out.println("(" + fileEntity.getRepoId() + ")" + fileEntity.getPath());
				}
			}
		}

		if (!diffCollection.getNewDirs().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("New dirs");
			System.out.println("================================================================");

			for (DirNewDiff modFile : diffCollection.getNewDirs()) {
				System.out.println("("+modFile.getDir1().getRepoId()+")" +modFile.getDir1().getPath());
				System.out.println("----------------------------------------------------------------");
			}
		}

	}

}
