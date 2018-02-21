package com.owr.so.console;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.owr.so.scanner.EnumActionByFlags;
import com.owr.so.scanner.Scanner;
import com.owr.so.scanner.dirtree.IScanningLogEventsListener;
import com.owr.so.scanner.dirtree.LogEventsListenerImpl;

/**
 * @author Paulius Zubavicius
 *
 */
public class Main {

	private static final String OPT_LONG_META = "metaFile";
	private static final String OPT_SHORT_META = "f";

	private static final String OPT_LONG_NEW_SCAN = "newScanDir";
	private static final String OPT_SHORT_NEW_SCAN = "d";

	private static final String OPT_STATUS = "status";

	public static void main(String[] args) {

		/**
		 * Options
		 */
		Options options = creteArgsOptions();

		/**
		 * Log events listener
		 */
		IScanningLogEventsListener logEventsListener = new LogEventsListenerImpl();

		/**
		 * Parse
		 */
		CommandLine line = null;
		CommandLineParser parser = new DefaultParser();
		try {

			line = parser.parse(options, args);

		} catch (ParseException e) {
			logEventsListener.businessCaseException(e, options);
			System.exit(0);
		}

		/**
		 * Actions
		 */

		boolean fMeta = line.hasOption(OPT_SHORT_META);
		boolean fNew = line.hasOption(OPT_SHORT_NEW_SCAN);
		boolean fStatus = line.hasOption(OPT_STATUS);

		String metaFileStr = line.getOptionValue(OPT_SHORT_META);
		String scanDirStr = line.getOptionValue(OPT_SHORT_NEW_SCAN);

		Scanner scanner = new Scanner(logEventsListener);

		EnumActionByFlags action = null;
		try {
			action = scanner.actionByFlags(fMeta, fNew, fStatus, metaFileStr, scanDirStr);
		} catch (Exception e) {
			logEventsListener.businessCaseException(e, options);
			System.exit(0);
		}

		switch (action) {
		case SHOW_STATUS:
			scanner.readMetaFileStatus(metaFileStr);
			break;
		case SCAN_FULL:
			scanner.scanFull(metaFileStr, scanDirStr);
			break;
		case SCAN_UPDATE:
			scanner.scanUpdate(metaFileStr, scanDirStr);
			break;
		default:
			throw new RuntimeException("Unexpected action: " + action);
		}

	}

	private static Options creteArgsOptions() {
		Options options = new Options();
		options.addOption(Option.builder(OPT_SHORT_META).longOpt(OPT_LONG_META).required().hasArg().valueSeparator()
				.desc("Meta file of scanning directory tree. If it isn't exist it will be created but <"
						+ OPT_LONG_NEW_SCAN + "> parameter must be specified.")
				.build());
		options.addOption(Option.builder(OPT_SHORT_NEW_SCAN).longOpt(OPT_LONG_NEW_SCAN).required(false).hasArg()
				.valueSeparator().desc("Use this to execute full scan for new synchronizing directory tree.").build());
		options.addOption(
				Option.builder(OPT_STATUS).required(false).desc("Shows status of directory tree meta file.").build());
		return options;
	}

}
