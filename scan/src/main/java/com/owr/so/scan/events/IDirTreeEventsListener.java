package com.owr.so.scan.events;

import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;

/**
 * @author Paulius Zubavicius
 *
 */
public interface IDirTreeEventsListener {

	void skipped(Path file, BasicFileAttributes attrs);

	void readFailed(Path file, IOException exc);

	void readFileOk(FileEntity entity, boolean modifications);

	void readDirOk(String dirPathString, Path dir, BasicFileAttributes attrs);

}
