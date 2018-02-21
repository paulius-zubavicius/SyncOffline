package com.owr.so.scanner.dirtree;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import com.owr.so.model.FileEntity;
import com.owr.so.utils.ConvertUtil;

/**
 * @author Paulius Zubavicius
 *
 */
public class LogEventsListenerImpl implements IScanningLogEventsListener {

	@Override
	public void skiping(Path file, BasicFileAttributes attrs) {
		System.err.println("XXX Skipping: " + file.toString());

	}

	@Override
	public void readFailed(Path file, IOException exc) {
		System.err.println("XXX Failed: " + file.toString());
	}

	@Override
	public void readDirOk(String dirPathString, Path dir, BasicFileAttributes attrs) {
		System.out.println(dirPathString);
	}

	@Override
	public void readFileOk(FileEntity entity) {
		System.out.println(String.format("   %-100s %s %s", entity.getName(),
				ConvertUtil.getSizeInHumanFormat(entity.getSize()), entity.getHashSum()));
	}

	@Override
	public void businessCaseException(Exception e, Options options) {
		System.err.println(e.getMessage());
		System.out.println();

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar scan.jar", options, true);

	}

	@Override
	public void metaFileStatus(boolean metaFileExists, long lastTimeModified, String rootDir, boolean rootDirExists) {
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

	}

	@Override
	public void scanDone(Duration timeElapsed) {
		System.out.println("Done. It took :" + ConvertUtil.getTimeInHumanFormat(timeElapsed));
	}

}
