package com.owr.so.diff.out.options;

import java.util.Arrays;
import java.util.List;

import com.owr.so.diff.out.IDiffOutput;
import com.owr.so.diff.out.OutputHandler;

public class OptionsOutput extends OutputHandler {

	private List<IDiffOutput> outs = Arrays.asList(new TitlesOut(), new ListOut(), new ListShellOut());

	@Override
	public List<IDiffOutput> getDiffsOutputs() {
		return outs;
	}

}
