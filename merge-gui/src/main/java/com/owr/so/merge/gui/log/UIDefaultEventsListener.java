package com.owr.so.merge.gui.log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.merge.gui.args.ArgsValues;

public class UIDefaultEventsListener implements IUIEventsListener {

	@Override
	public void treesLoaded(ArgsValues argsValues) {
		long currentTime = System.currentTimeMillis();
		printMetaFileInfo(currentTime, argsValues.getRepoFilePath1());
		printMetaFileInfo(currentTime, argsValues.getRepoFilePath2());
	}

	@Override
	public void treesCompared(DirTreesDiffResult diffCollection) {
		// FIXME Romove later
		printDebug(diffCollection);

	}

	private void printMetaFileInfo(long currentTime, String metaFilePath) {
		Path path = Paths.get(metaFilePath);
		File file = path.toFile();
		Duration duration = Duration.ofMillis(currentTime - file.lastModified());

		System.out.println("Meta file : " + file.getPath());
		System.out.println("How old   : " + ConvertUtil.getTimeInHumanFormat(duration));
		System.out.println();
	}

	private void printDebug(DirTreesDiffResult diffCollection) {

		if (!diffCollection.getModifiedFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Modified files");
			System.out.println("================================================================");

			for (FileModifiedDiff modFile : diffCollection.getModifiedFiles()) {

				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.getAction() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getMovedFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Moved files");
			System.out.println("================================================================");

			for (FileMovedDiff modFile : diffCollection.getMovedFiles()) {

				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("(" + modFile.getFile2().getRepoName() + ")" + modFile.getFile2().getPath());
				System.out.println("Conflict: " + modFile.getAction() + "");
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getNewFiles().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("New files");
			System.out.println("================================================================");

			for (FileNewDiff modFile : diffCollection.getNewFiles()) {
				System.out.println("(" + modFile.getFile1().getRepoName() + ")" + modFile.getFile1().getPath());
				System.out.println("----------------------------------------------------------------");
			}
		}

		if (!diffCollection.getDuplicates().isEmpty()) {
			System.out.println("================================================================");
			System.out.println("Duplicates");
			System.out.println("================================================================");

			System.out.println();

			for (FileDuplicatesDiff modFile : diffCollection.getDuplicates()) {

				for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
					System.out.println("(" + fileEntity.getRepoName() + ")" + fileEntity.getPath());
				}

				for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
					System.out.println("(" + fileEntity.getRepoName() + ")" + fileEntity.getPath());
				}
			}

			System.out.println("----------------------------------------------------------------");

		}

//		if (!diffCollection.getNewDirs().isEmpty()) {
//			System.out.println("================================================================");
//			System.out.println("New dirs");
//			System.out.println("================================================================");
//
//			for (DirNewDiff modFile : diffCollection.getNewDirs()) {
//				System.out.println("(" + modFile.getDir1().getRepoId() + ")" + modFile.getDir1().getPath());
//				System.out.println("----------------------------------------------------------------");
//			}
//		}

	}

}
