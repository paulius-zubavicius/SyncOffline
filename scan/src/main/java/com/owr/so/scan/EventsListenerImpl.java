package com.owr.so.scan;

import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.commons.IScanEventsListener;
import com.owr.so.commons.ScanEvent;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.RepoMetaData;

/**
 * @author Paulius Zubavicius
 *
 */
public class EventsListenerImpl implements IScanEventsListener {

	private int statModNewFiles = 0;
	private int statModNewSize = 0;
	private int statSkipSymLinks = 0;
	private int statSkipOthers = 0;
	private int statSkipDirs = 0;
	private int statExcDirs = 0;

	@Override
	public void event(ScanEvent event, Object... data) {

		switch (event) {
		case SCAN_FILE_OK_OLD:
			break;

		case VALIDATION_MOUNTED_DISC:
		case VALIDATION_OTHER_PCS_REPO:
		case VALIDATION_BLANK_PATH:
		case VALIDATION_NOT_A_REPO_FILE:
		case VALIDATION_REPO_FILE_NOT_EXIST:
			System.err.println("Warn: " + printObjs(data));
			break;
		case DONE_SUCCESS:
			summary((Duration) data[0], ((RepoData) data[1]));
			break;
		case DONE_FAIL:
			System.err.println("FAIL. Cause: " + data[0]);
			break;
		case SCAN_FILE_OK_NEW:
			FileEntity fe = ((FileEntity) data[0]);
			statModNewFiles++;
			statModNewSize += fe.getSize();
			System.out.println(String.format("   %-100s %s %s", fe.getName(),
					ConvertUtil.getSizeInHumanFormat(fe.getSize()), fe.getChecksum()));

			break;
		case SCAN_NEW_DIR:
			System.out.println("[" + data[0] + "]");
			break;
		case SCAN_NEW_DIR_SKIPING:
			statExcDirs++;
			break;

		case SCAN_FILE_SKIPPED:
			BasicFileAttributes attrs = (BasicFileAttributes) data[1];
			if (attrs.isSymbolicLink()) {
				statSkipSymLinks++;
			}

			if (attrs.isDirectory()) {
				statSkipDirs++;
			}

			if (attrs.isOther()) {
				statSkipOthers++;
			}
			break;
		case SCAN_FAILED:
			System.err.println("!!! Failed: " + data[0] + " -> " + data[1]);
			break;
		default:
			System.out.println(event.toString() + " : " + printObjs(data));
		}
	}

	private String printObjs(Object... data) {
		String str = "";
		for (Object obj : data) {
			str += obj + "\t";
		}
		return str;
	}

	private void summary(Duration timeElapsed, RepoData newDirTree) {

		System.out.println();
		// All files:

		RepoMetaData rmd = new RepoMetaData(newDirTree, null, null);
		System.out.println("Read files       : " + rmd.getFiles().size());
		long readSize = 0;
		for (FileEntityWrapper ent : rmd.getFiles()) {
			readSize += ent.getFile().getSize();
		}
		System.out.println("Read size        : " + ConvertUtil.getSizeInHumanFormat(readSize));
		// Scanned files:
		System.out.println("Scanned/new files: " + statModNewFiles);
		// Read bytes:
		System.out.println("Scanned/new size : " + ConvertUtil.getSizeInHumanFormat(statModNewSize));
		// Skipped files:

		System.out.println("Excluded dirs    : " + statExcDirs);
		System.out.println("Skipped SymLinks : " + statSkipSymLinks);
		System.out.println("Skipped dirs     : " + statSkipDirs);
		System.out.println("Skipped other    : " + statSkipOthers);

		System.out.println();
		System.out.println("Done in " + ConvertUtil.getTimeInHumanFormat(timeElapsed));
	}

}
