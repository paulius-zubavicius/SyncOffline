package com.owr.so.merge.log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.model.DirTreeEntity;

public class MergeLogEventsListener implements IMergeLogEventsListener {

	@Override
	public void dataRead(DirTreeEntity tree1, DirTreeEntity tree2, String metaFile1Path, String metaFile2Path) {
		long currentTime = System.currentTimeMillis();
		printMetaFileInfo(currentTime, metaFile1Path);
		printMetaFileInfo(currentTime, metaFile2Path);
	}

	private void printMetaFileInfo(long currentTime, String metaFilePath) {
		Path path = Paths.get(metaFilePath);
		File file = path.toFile();
		Duration duration = Duration.ofMillis(currentTime - file.lastModified());

		System.out.println("Meta file : " + file.getPath());
		System.out.println("How old   : " + ConvertUtil.getTimeInHumanFormat(duration));
		System.out.println();
	}

}
