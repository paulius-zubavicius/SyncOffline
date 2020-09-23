package com.owr.so.diff.out.backup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.owr.so.diff.out.IDiffOutput;
import com.owr.so.diff.out.OutputHandler;

public class ForceBackup extends OutputHandler {

    private List<IDiffOutput> outs = Collections.singletonList(new ForceBackupShellOut());

    @Override
    public List<IDiffOutput> getDiffsOutputs() {
        return outs;
    }

}
