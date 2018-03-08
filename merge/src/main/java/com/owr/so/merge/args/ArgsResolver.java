package com.owr.so.merge.args;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.owr.so.commons.DirTreeEntityLoader;
import com.owr.so.model.DirTreeEntity;

public class ArgsResolver {

	private static final String OPT_LONG_META1 = "metafile1";
	private static final String OPT_SHORT_META1 = "f1";

	private static final String OPT_LONG_META2 = "metafile2";
	private static final String OPT_SHORT_META2 = "f2";

	private static final String OPT_LONG_SUBDIR1 = "subdir1";
	private static final String OPT_SHORT_SUBDIR1 = "s1";

	private static final String OPT_LONG_SUBDIR2 = "subdir2";
	private static final String OPT_SHORT_SUBDIR2 = "s2";

	private static final String OPT_GUI = "gui";

	private DirTreeEntity tree1 = null;
	private DirTreeEntity tree2 = null;
	private boolean guiMode;

	public ArgsResolver(String[] args) {

		/**
		 * Options
		 */
		Options options = creteArgsOptions();

		/**
		 * Parse
		 */
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

		/**
		 * Actions
		 */

		String metaFile1Path = line.getOptionValue(OPT_SHORT_META1);
		String metaFile2Path = line.getOptionValue(OPT_SHORT_META2);

		String subdir1 = line.getOptionValue(OPT_SHORT_SUBDIR1);
		String subdir2 = line.getOptionValue(OPT_SHORT_SUBDIR2);

		try {

			InputValidator.validateMetaFilesPaths(metaFile1Path, metaFile2Path);

			if (line.hasOption(OPT_SHORT_SUBDIR1)) {
				InputValidator.validateSubDirBeforeLoading(metaFile1Path, subdir1);
			}

			if (line.hasOption(OPT_SHORT_SUBDIR2)) {
				InputValidator.validateSubDirBeforeLoading(metaFile1Path, subdir2);
			}

			/**
			 * Loading trees
			 */
			tree1 = DirTreeEntityLoader.load(metaFile1Path, 1);
			if (line.hasOption(OPT_SHORT_SUBDIR1)) {
				InputValidator.validateSubDirAfterLoading(tree1, subdir1, 1);
				tree1.setSubDir(subdir1);
			}

			tree2 = DirTreeEntityLoader.load(metaFile2Path, 2);
			if (line.hasOption(OPT_SHORT_SUBDIR2)) {
				InputValidator.validateSubDirAfterLoading(tree2, subdir2, 2);
				tree2.setSubDir(subdir2);
			}

		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println();

			System.exit(0);
		}

		guiMode = line.hasOption(OPT_GUI);
	}

	public DirTreeEntity getTree1() {
		return tree1;
	}

	public DirTreeEntity getTree2() {
		return tree2;
	}

	public boolean isGuiMode() {
		return guiMode;
	}

	private static Options creteArgsOptions() {
		Options options = new Options();
		options.addOption(Option.builder(OPT_SHORT_META1).required().hasArg().valueSeparator().longOpt(OPT_SHORT_META1)
				.desc("Meta file (1) of scanned directory tree.").build());
		options.addOption(Option.builder(OPT_SHORT_META2).required().hasArg().valueSeparator().longOpt(OPT_SHORT_META2)
				.desc("Meta file (2) of scanned directory tree.").build());

		options.addOption(Option.builder(OPT_SHORT_SUBDIR1).longOpt(OPT_LONG_SUBDIR1).required(false).hasArg()
				.valueSeparator()
				.desc("Merge sub directory only. It should existing in meta file (1). Subdir should be without root part of path.")
				.build());

		options.addOption(Option.builder(OPT_SHORT_SUBDIR2).longOpt(OPT_LONG_SUBDIR2).required(false).hasArg()
				.valueSeparator()
				.desc("Merge sub directory only. It should existing in meta file (2). Subdir should be without root part of path.")
				.build());

		options.addOption(Option.builder(OPT_GUI).required(false)
				.desc("Use GUI for file merging. (Not implemented and will be ignored)").build());
		return options;
	}

}
