package com.owr.so.scan.dirtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.owr.so.commons.OSType;
import com.owr.so.diff.model.DirTree;
import com.owr.so.scan.events.IDirTreeEventsListener;

/**
 * @author Paulius Zubavicius
 */
public class DirTreeScanner {

	private final IDirTreeEventsListener listener;

	public DirTreeScanner(IDirTreeEventsListener listener) {
		this.listener = listener;
	}

	public DirTree scanNew(Path dirTreeMetaFile, Path dirTreePath) {
		return createNewTree(dirTreePath, new DirTree());
	}

	public DirTree scanUpdate(DirTree currentTree, Path dirTreePath) {
		return createNewTree(dirTreePath, currentTree);
	}

	private DirTree createNewTree(Path dirTreePath, DirTree currentTree) {

		DirTree result = new DirTree();
		result.setDirTreeRootPath(dirTreePath.toString());
		result.setOsCode(OSType.getOSTypeCurrentStr());

		try {
			Files.walkFileTree(dirTreePath, new FileVisitorScanner(result, currentTree, listener));
		} catch (IOException e) {
			listener.readFailed(dirTreePath, e);
		}

		return result;
	}

}
