package com.owr.so.merge.gui.app;

import java.io.IOException;

import com.owr.so.merge.gui.args.ArgsResolver;
import com.owr.so.merge.gui.model.Merge;

public class Main {

	public static void main(String[] args) throws IOException {

		ArgsResolver resolver = new ArgsResolver();

		new Merge(resolver.resolve(args));

	}

}
