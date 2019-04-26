package com.owr.so.scan.dirtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.owr.so.commons.OSType;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.scan.events.IDirTreeEventsListener;

/**
 * @author Paulius Zubavicius
 */
public class DirTreeScanner {

	private IDirTreeEventsListener listener;
	
    public DirTreeScanner(IDirTreeEventsListener listener) {
		this.listener = listener;
	}

	public DirTreeEntity scanNew(Path dirTreeMetaFile, Path dirTreePath) {
        return createNewTree(dirTreePath, new DirTreeEntity());
    }

    public DirTreeEntity scanUpdate(DirTreeEntity currentTree, Path dirTreePath) {
        return createNewTree(dirTreePath, currentTree);
    }

    private DirTreeEntity createNewTree(Path dirTreePath, DirTreeEntity currentTree) {

        DirTreeEntity result = new DirTreeEntity();
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
