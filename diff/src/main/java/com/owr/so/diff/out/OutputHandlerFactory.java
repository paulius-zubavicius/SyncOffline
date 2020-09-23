package com.owr.so.diff.out;

import com.owr.so.diff.out.backup.ForceBackup;
import com.owr.so.diff.out.options.OptionsOutput;

public class OutputHandlerFactory {

    public static IOutputHandler createInstance(String mode) {
        if ("forcebackup".equalsIgnoreCase(mode)) {
            return new ForceBackup();
        } else
            return new OptionsOutput();
    }

}
