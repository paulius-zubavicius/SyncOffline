package com.owr.so.storage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import com.owr.so.model.DirEntity;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

public class DirTreeEntityLoader {

	public static DirTreeEntity load(String dirTreeMetaFileStr) {

		Storage<DirTreeEntity> str = new Storage<>();
		DirTreeEntity result = null;

		try {
			result = str.load(Paths.get(dirTreeMetaFileStr), DirTreeEntity.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Collection<DirEntity> dirs = result.getDirTree().values();
		List<FileEntity> fileEntity = null;
		for (DirEntity dirEntity : dirs) {

			fileEntity = dirEntity.getFiles();

			for (FileEntity fEntity : fileEntity) {

				fEntity.setDir(dirEntity);
				result.initTransientFields(fEntity);
			}
		}

		return result;
	}
}
