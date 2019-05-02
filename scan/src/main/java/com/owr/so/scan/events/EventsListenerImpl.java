package com.owr.so.scan.events;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.FileEntity;

/**
 * @author Paulius Zubavicius
 *
 */
public class EventsListenerImpl implements IScanEventsListener, IDirTreeEventsListener {

	// Modified/new files
	private int statModNewFiles;

	// Modified/new size
	private int statModNewSize;

	private int statSkipSymLinks;
	private int statSkipOthers;
	private int statSkipDirs;

	@Override
	public void skipped(Path file, BasicFileAttributes attrs) {

		String reason = "";

		if (attrs.isSymbolicLink()) {
			reason += "SymbolicLink ";
			statSkipSymLinks++;
		}

		if (attrs.isDirectory()) {
			reason += "Dir ";
			statSkipDirs++;
		}

		if (attrs.isOther()) {
			reason += "Other ";
			statSkipOthers++;
		}

		System.err.println(reason + ": " + file.toString() + " - Skipped");

	}

	@Override
	public void readFailed(Path file, IOException exc) {
		System.err.println("!!! Failed: " + file.toString());
	}

	@Override
	public void readDirOk(String dirPathString, Path dir, BasicFileAttributes attrs) {
		System.out.println("[" + dirPathString + "]");
	}

	@Override
	public void readFileOk(FileEntity entity, boolean modifications) {
		if (modifications) {
			statModNewFiles++;
			statModNewSize += entity.getSize();
			System.out.println(String.format("   %-100s %s %s", entity.getName(),
					ConvertUtil.getSizeInHumanFormat(entity.getSize()), entity.getChecksum()));
		}
	}

	@Override
	public void metaFileStatus(boolean metaFileExists, long lastTimeModified, String rootDir, boolean rootDirExists,
			String osCode) {
		String lastModifiedStr = "<?>";
		if (metaFileExists) {
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastTimeModified),
					ZoneId.systemDefault());

			LocalDateTime today = LocalDateTime.now();
			LocalDateTime lastScan = Instant.ofEpochMilli(lastTimeModified).atZone(ZoneId.systemDefault())
					.toLocalDateTime();

			Duration p = Duration.between(lastScan, today);
			lastModifiedStr = "" + date.toString() + " (" + p.toHours() + " hours ago" + ")";
		}

		System.out.println("Meta file exists : " + (metaFileExists ? "Yes" : "No"));
		System.out.println("Last modified    : " + lastModifiedStr);
		System.out.println("Scanned root dir : " + (metaFileExists ? "[" + rootDir + "]" : "<?>"));
		System.out.println("Root dir exists  : " + (metaFileExists ? (rootDirExists ? "Yes" : "No") : "<?>"));
		System.out.println("OS code - type   : " + osCode);

	}

	@Override
	public void scanDone(Duration timeElapsed, DirTree newDirTree) {

		System.out.println();
		// Some statistics
		// All files:
		System.out.println("Read files       : " + newDirTree.getFiles().size());
		long readSize = 0;
		for (FileEntity ent : newDirTree.getFiles()) {
			readSize += ent.getSize();
		}
		System.out.println("Read size        : " + ConvertUtil.getSizeInHumanFormat(readSize));
		// Scanned files:
		System.out.println("Scanned/new files: " + statModNewFiles);
		// Read bytes:
		System.out.println("Scanned/new size : " + ConvertUtil.getSizeInHumanFormat(statModNewSize));
		// Skipped files:
		System.out.println("Skipped SymLinks : " + statSkipSymLinks);
		System.out.println("Skipped dirs     : " + statSkipDirs);
		System.out.println("Skipped other    : " + statSkipOthers);

		System.out.println();
		System.out.println("Done in " + ConvertUtil.getTimeInHumanFormat(timeElapsed));
	}

}
