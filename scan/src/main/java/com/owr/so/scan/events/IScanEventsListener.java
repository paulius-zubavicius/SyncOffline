package com.owr.so.scan.events;

import com.owr.so.diff.model.DirTree;

import java.time.Duration;

/**
 * @author Paulius Zubavicius
 *
 */
public interface IScanEventsListener {

	// void businessCaseException(Exception e, Options options);
	void metaFileStatus(boolean metaFileExists, long lastTimeModified, String rootDir, boolean rootDirExists,
			String osCode);

	void scanDone(Duration timeElapsed, DirTree newDirTree);

}
