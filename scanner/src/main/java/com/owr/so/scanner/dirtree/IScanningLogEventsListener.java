package com.owr.so.scanner.dirtree;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;

import org.apache.commons.cli.Options;

import com.owr.so.model.FileEntity;

/**
 * @author Paulius Zubavicius
 *
 */
public interface IScanningLogEventsListener {

	void skiping(Path file, BasicFileAttributes attrs);

	void readFailed(Path file, IOException exc);

	void readFileOk(FileEntity entity, boolean modifications);

	void businessCaseException(Exception e, Options options);

	void metaFileStatus(boolean metaFileExists, long lastTimeModified, String rootDir, boolean rootDirExists);

	void scanDone(Duration timeElapsed);

	void readDirOk(String dirPathString, Path dir, BasicFileAttributes attrs);

}
