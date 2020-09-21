package com.owr.so.scan.app;

import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.owr.so.scan.EventsListenerImpl;
import com.owr.so.scan.RepoScanner;

/**
 * @author Paulius Zubavicius
 */
public class App {

	private static final String OPT_META_FILE = "f";

	public static void main(String[] args) {

		Options options = creteArgsOptions();

		CommandLine line = null;
		try {
			line = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			handleError(e, options);
			return;
		}

		callDirTreeScanner(line, options);
	}

	private static Options creteArgsOptions() {
		Options options = new Options();

		options.addOption(Option.builder(OPT_META_FILE).required().hasArg().valueSeparator()
				.desc("Existing repository file of directory trees for scan.").build());

		return options;
	}

	private static void callDirTreeScanner(CommandLine line, Options options) {
		if (!line.hasOption(OPT_META_FILE)) {
			System.exit(handleError(new RuntimeException("Repository file should be specified"), options));
		}

		RepoScanner rs = new RepoScanner();

		try {
			rs.scan(Path.of(line.getOptionValue(OPT_META_FILE)), new EventsListenerImpl());
		} catch (Exception e) {
			System.exit(handleError(e, options));
		}
	}

	private static int handleError(Exception e, Options options) {
//		if (e.getMessage() != null) {
//			System.err.println(e.getMessage());
//		} else {
//			e.printStackTrace();
//		}
		e.printStackTrace();
		
		System.out.println();

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar scanPath.jar", options, true);

		return 1;
	}

}
