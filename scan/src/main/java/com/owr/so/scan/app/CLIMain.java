package com.owr.so.scan.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.owr.so.scan.events.EventsListenerImpl;
import com.owr.so.scan.scanner.RepoScanner;

/**
 * @author Paulius Zubavicius
 */
public class CLIMain {

    private static final String OPT_META_FILE = "repofile";
    private static final String OPT_SCAN_NEW_PATH = "path";
    private static final String OPT_STATUS = "status";

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

    private static void callDirTreeScanner(CommandLine line, Options options) {
        if (!line.hasOption(OPT_META_FILE)) {
            System.exit(handleError(new RuntimeException("Meta file should be specified"), options));
        }

        RepoScanner rs = new RepoScanner(new EventsListenerImpl());

        if (line.hasOption(OPT_STATUS)) {
            rs.readMetaFileStatus(line.getOptionValue(OPT_META_FILE));
            System.exit(0);
        }

        try {
            rs.scanPath(line.getOptionValue(OPT_META_FILE),
                    (line.hasOption(OPT_SCAN_NEW_PATH) ? line.getOptionValue(OPT_SCAN_NEW_PATH) : null));
        } catch (Exception e) {
            System.exit(handleError(e, options));
        }
    }

    private static Options creteArgsOptions() {
        Options options = new Options();

        options.addOption(Option
                .builder(OPT_META_FILE)
                .required()
                .hasArg()
                .valueSeparator()
                .desc("Meta file of scanning directory tree. If it isn't exist it will be created but <"
                        + OPT_SCAN_NEW_PATH + "> parameter must be specified.")
                .build());

        options.addOption(Option
                .builder(OPT_SCAN_NEW_PATH)
                .required(false)
                .hasArg()
                .valueSeparator()
                .desc("Use this to execute full scanPath for new synchronizing directory tree.")
                .build());

        options.addOption(Option
                .builder(OPT_STATUS)
                .required(false)
                .desc("Shows status of directory tree meta file.")
                .build());

        return options;
    }

    private static int handleError(Exception e, Options options) {
        if (e.getMessage() != null) {
            System.err.println(e.getMessage());
        } else {
            e.printStackTrace();
        }

        System.out.println();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar scanPath.jar", options, true);

        return 1;
    }

}
