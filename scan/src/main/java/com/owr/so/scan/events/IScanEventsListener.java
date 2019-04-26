package com.owr.so.scan.events;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;

import org.apache.commons.cli.Options;

import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;

/**
 * @author Paulius Zubavicius
 *
 */
public interface IScanEventsListener {



	//void businessCaseException(Exception e, Options options);

	void metaFileStatus(boolean metaFileExists, long lastTimeModified, String rootDir, boolean rootDirExists, String osCode);

	void scanDone(Duration timeElapsed, DirTreeEntity newDirTreeEntity);



}
