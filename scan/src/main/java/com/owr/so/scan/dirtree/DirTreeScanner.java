package com.owr.so.scan.dirtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import com.owr.so.commons.OSType;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.scan.log.IScanLogEventsListener;

/**
 * @author Paulius Zubavicius
 *
 */
public class DirTreeScanner {

	public DirTreeEntity scanNew(Path dirTreeMetaFile, Path dirTreePath, IScanLogEventsListener listener) {
		return createNewTree(dirTreePath, new DirTreeEntity(), listener);
	}

	public DirTreeEntity scanUpdate(DirTreeEntity currentTree, Path dirTreePath, IScanLogEventsListener listener) {
		return createNewTree(dirTreePath, currentTree, listener);

	}

	private DirTreeEntity createNewTree(Path dirTreePath, DirTreeEntity currentTree,
			IScanLogEventsListener listener) {

		DirTreeEntity result = new DirTreeEntity();
		result.setDirTreeRootPath(dirTreePath.toString());
		result.setOsCode(OSType.getOSTypeCurrentStr());
		FileVisitorScanner scanner = new FileVisitorScanner(result, currentTree, listener);

		try {
			Files.walkFileTree(dirTreePath, new HashSet<>(), Integer.MAX_VALUE, scanner);
		} catch (IOException e) {
			listener.readFailed(dirTreePath, e);
		}

		return result;
	}

}
