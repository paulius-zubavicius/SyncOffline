package com.owr.so.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import com.owr.so.model.DirTreeEntity;
import com.owr.so.scanner.dirtree.DirTreeScanner;
import com.owr.so.scanner.dirtree.IScanningLogEventsListener;
import com.owr.so.storage.Storage;

/**
 * @author Paulius Zubavicius
 *
 */
public class Scanner {

	private IScanningLogEventsListener logEventsListener;

	public Scanner(IScanningLogEventsListener logEventsListener) {
		this.logEventsListener = logEventsListener;
	}

	public EnumActionByFlags actionByFlags(boolean fMeta, boolean fNew, boolean fStatus, String metaFileStr,
			String scanDirStr) throws Exception {

		if (fMeta) {

			if (fStatus) {
				return EnumActionByFlags.SHOW_STATUS;
			}

			if (fNew) {

				if (pathExists(scanDirStr)) {
					return EnumActionByFlags.SCAN_FULL;
				}

				throw new Exception("Scanning target directory path doesn't exists: [" + scanDirStr + "]");

			} else {

				if (pathExists(metaFileStr)) {
					DirTreeEntity currentTree = loadMetaFile(metaFileStr);

					if (pathExists(currentTree.getDirTreeRootPath())) {
						return EnumActionByFlags.SCAN_UPDATE;
					}

					throw new Exception("Scanning target directory path doesn't exists: ["
							+ currentTree.getDirTreeRootPath()
							+ "] Maybe target root directory was renamed or moved. Be sure that the meta file was created for current computer or disk.");

				}

				throw new Exception(
						"Meta file not found. Please specify exiting meta file or specify new scanning directory path");
			}
		}

		throw new Exception("Meta file should be specified");

	}

	public void readMetaFileStatus(String metaFileStr) {

		Path metaFilePath = Paths.get(metaFileStr);

		File file = metaFilePath.toFile();

		DirTreeEntity entity = new DirTreeEntity();

		boolean metaFileExists = file.exists();
		long lastTimeModified = 0;
		String rootDir = "";
		boolean rootDirExists = false;

		if (metaFileExists) {
			lastTimeModified = file.lastModified();
			entity = loadMetaFile(metaFileStr);
			rootDir = entity.getDirTreeRootPath();
			rootDirExists = pathExists(rootDir);
		}

		logEventsListener.metaFileStatus(metaFileExists, lastTimeModified, rootDir, rootDirExists);

	}

	public void scanFull(String metaFileStr, String scanDirStr) {
		scan(false, metaFileStr, scanDirStr);
	}

	public void scanUpdate(String metaFileStr, String scanDirStr) {
		scan(true, metaFileStr, scanDirStr);
	}

	private void scan(boolean updateMetaFile, String metaFileStr, String scanDirStr) {

		Instant intant1 = Instant.now();

		DirTreeScanner dirTreeScan = new DirTreeScanner();
		DirTreeEntity newDirTreeEntity = null;

		Path dirTreePath = Paths.get(scanDirStr);
		Path metaFilePath = Paths.get(metaFileStr);

		if (updateMetaFile) {
			newDirTreeEntity = dirTreeScan.scanUpdate(loadMetaFile(metaFileStr), dirTreePath, logEventsListener);
		} else {
			newDirTreeEntity = dirTreeScan.scanNew(metaFilePath, dirTreePath, logEventsListener);
		}

		// Save to file
		Storage<DirTreeEntity> str = new Storage<>();

		try {
			str.save(metaFilePath, newDirTreeEntity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Instant intant2 = Instant.now();
		Duration timeElapsed = Duration.between(intant1, intant2);

		logEventsListener.scanDone(timeElapsed);

	}

	private boolean pathExists(String pathStr) {
		Path path = Paths.get(pathStr);
		File file = path.toFile();
		return file.exists();
	}

	private DirTreeEntity loadMetaFile(String dirTreeMetaFileStr) {
		Path path = Paths.get(dirTreeMetaFileStr);
		Storage<DirTreeEntity> str = new Storage<>();
		try {
			return str.load(path, DirTreeEntity.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
