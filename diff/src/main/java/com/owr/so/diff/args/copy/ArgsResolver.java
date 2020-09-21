package com.owr.so.diff.args.copy;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class ArgsResolver {

	private static final String OPT_SHORT_META1 = "f1";
	private static final String OPT_SHORT_META2 = "f2";

	public ArgsValues resolve(String[] args) {

		Options options = creteArgsOptions();

		CommandLine line = null;
		CommandLineParser parser = new DefaultParser();
		try {

			line = parser.parse(options, args);

		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println();
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar merge.jar", options, true);
			System.exit(0);
		}

		String metaFile1Path = line.getOptionValue(OPT_SHORT_META1);
		String metaFile2Path = line.getOptionValue(OPT_SHORT_META2);

		validateMetaFilesPaths(metaFile1Path, metaFile2Path);

		return new ArgsValues(metaFile1Path, metaFile2Path);
	}

	private static Options creteArgsOptions() {
		Options options = new Options();
		options.addOption(Option.builder(OPT_SHORT_META1).required().hasArg().valueSeparator().longOpt(OPT_SHORT_META1)
				.desc("Meta file (1) of scanned directory tree.").build());
		options.addOption(Option.builder(OPT_SHORT_META2).required().hasArg().valueSeparator().longOpt(OPT_SHORT_META2)
				.desc("Meta file (2) of scanned directory tree.").build());
		return options;
	}

	public void validateMetaFilesPaths(String metaFile1Path, String metaFile2Path) {

		if (StringUtils.isEmpty(metaFile1Path)) {
			throw new RuntimeException("MetaFile1 file path doesn't specified or empty: [" + metaFile1Path + "]");
		}

		if (StringUtils.isEmpty(metaFile2Path)) {
			throw new RuntimeException("MetaFile2 file path doesn't specified or empty: [" + metaFile2Path + "]");
		}

		File file1 = Path.of(metaFile1Path).toFile();
		if (!file1.exists() && !file1.isFile()) {
			throw new RuntimeException("MetaFile1 file path doesn't exists: [" + metaFile1Path + "]");
		}

		File file2 = Path.of(metaFile2Path).toFile();
		if (!file2.exists() && !file2.isFile()) {
			throw new RuntimeException("MetaFile2 file path doesn't exists: [" + metaFile2Path + "]");
		}
	}

}
