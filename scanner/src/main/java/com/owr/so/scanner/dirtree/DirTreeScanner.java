package com.owr.so.scanner.dirtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import com.owr.so.model.DirTreeEntity;

/**
 * @author Paulius Zubavicius
 *
 */
public class DirTreeScanner {

	public DirTreeEntity scanNew(Path dirTreeMetaFile, Path dirTreePath, IScanningLogEventsListener listener) {
		return createNewTree(dirTreePath, new DirTreeEntity(), listener);
	}

	public DirTreeEntity scanUpdate(DirTreeEntity currentTree, Path dirTreePath, IScanningLogEventsListener listener) {
		return createNewTree(dirTreePath, currentTree, listener);

	}

	private DirTreeEntity createNewTree(Path dirTreePath, DirTreeEntity currentTree,
			IScanningLogEventsListener listener) {

		DirTreeEntity result = new DirTreeEntity();
		result.setDirTreeRootPath(dirTreePath.toString());
		FileVisitorScanner scanner = new FileVisitorScanner(result, currentTree, listener);

		try {
			Files.walkFileTree(dirTreePath, new HashSet<>(), Integer.MAX_VALUE, scanner);
		} catch (IOException e) {
			listener.readFailed(dirTreePath, e);
		}

		return result;
	}

}
