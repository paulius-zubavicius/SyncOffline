package com.owr.so.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

import com.owr.so.model.DirEntity;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

public class DirTreeEntityLoader {

	public static DirTreeEntity load(String dirTreeMetaFileStr) {
		return load(dirTreeMetaFileStr, 0);
	}

	public static DirTreeEntity load(String dirTreeMetaFileStr, int repoId) {

		Storage<DirTreeEntity> str = new Storage<>();
		DirTreeEntity result = null;

		Path metaFilePath = Paths.get(dirTreeMetaFileStr);

		try {
			result = str.load(metaFilePath, DirTreeEntity.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Collection<DirEntity> dirs = result.getDirTree().values();
		List<FileEntity> fileEntity = null;
		for (DirEntity dirEntity : dirs) {

			dirEntity.setRepoId(repoId);

			fileEntity = dirEntity.getFiles();

			for (FileEntity fEntity : fileEntity) {

				fEntity.setRepoId(repoId);
				fEntity.setDir(dirEntity);
				result.initTransientFields(fEntity);
			}
		}

		result.setId(repoId);
		result.setMetaFilePath(dirTreeMetaFileStr);
		result.setMetaFileDuration(calcFileAge(metaFilePath));

		return result;
	}

	private static Duration calcFileAge(Path metaFilePath) {
		File file = metaFilePath.toFile();
		return Duration.ofMillis(System.currentTimeMillis() - file.lastModified());
	}
}
