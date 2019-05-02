package com.owr.so.scan.scanner;

import com.owr.so.commons.DirTreeEntityLoader;
import com.owr.so.commons.FileUtil;
import com.owr.so.commons.OSType;
import com.owr.so.commons.Storage;
import com.owr.so.diff.model.DirTree;
import com.owr.so.scan.dirtree.DirTreeScanner;
import com.owr.so.scan.events.IScanEventsListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

/**
 * @author Paulius Zubavicius
 *
 */
public class RepoScanner {

	private IScanEventsListener evListener;

	public RepoScanner(IScanEventsListener evListener) {
		this.evListener = evListener;
	}

	public void scanPath(String metaFileStr, String scanDirPath) throws Exception {

			if (scanDirPath != null) {

				if (FileUtil.pathExists(scanDirPath)) {
					scanFull(metaFileStr, scanDirPath);
					return;
				}

				throw new Exception("Scanning target directory path doesn't exists: [" + scanDirPath + "]");

			} else {

				if (FileUtil.pathExists(metaFileStr)) {
					DirTree currentTree = DirTreeEntityLoader.load(metaFileStr);

					if (FileUtil.pathExists(currentTree.getDirTreeRootPath())) {
						scanUpdate(metaFileStr);
						return;
					}

					throw new Exception("Scanning target directory path doesn't exists: ["
							+ currentTree.getDirTreeRootPath()
							+ "] Maybe target root directory was renamed or moved. Be sure that the meta file was created for current computer or disk.");

				}

				throw new Exception(
						"Meta file not found. Please specify exiting meta file or specify new scanning directory path");
			}
	}

	public void readMetaFileStatus(String metaFileStr) {

		Path metaFilePath = Paths.get(metaFileStr);

		File file = metaFilePath.toFile();

		boolean metaFileExists = file.exists();
		long lastTimeModified = 0;
		String rootDir = "";
		String osType = "";
		boolean rootDirExists = false;

		if (metaFileExists) {
			lastTimeModified = file.lastModified();
			DirTree entity = DirTreeEntityLoader.load(metaFileStr);
			rootDir = entity.getDirTreeRootPath();
			rootDirExists = FileUtil.pathExists(rootDir);
			osType = entity.getOsCode() + " - " + OSType.getOSTypeByCode(entity.getOsCode());
		}

		evListener.metaFileStatus(metaFileExists, lastTimeModified, rootDir, rootDirExists, osType);
	}

    private void scanFull(String metaFileStr, String scanDirStr) {
		scanPath(false, metaFileStr, scanDirStr);
	}

    private void scanUpdate(String metaFileStr) {
		scanPath(true, metaFileStr, null);
	}

	private void scanPath(boolean updateMetaFile, String metaFileStr, String scanDirStr) {

		Instant instant1 = Instant.now();

		DirTreeScanner dirTreeScan = new DirTreeScanner(null);
		DirTree newDirTree;

		Path metaFilePath = Paths.get(metaFileStr);

		if (updateMetaFile) {
			DirTree dirTreeCurrent = DirTreeEntityLoader.load(metaFileStr);
			newDirTree = dirTreeScan.scanUpdate(dirTreeCurrent, Paths.get(dirTreeCurrent.getDirTreeRootPath()));
		} else {
			newDirTree = dirTreeScan.scanNew(metaFilePath, Paths.get(scanDirStr));
		}

		// Save to file
		saveResult(metaFilePath, newDirTree);

		// How long it was?
		Duration timeElapsed = Duration.between(instant1, Instant.now());

		evListener.scanDone(timeElapsed, newDirTree);

	}

	private void saveResult(Path metaFilePath, DirTree newDirTree) {
		Storage<DirTree> str = new Storage<>();

		try {
			str.save(metaFilePath, newDirTree);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
