package com.owr.so.merge.app;

import java.io.IOException;

import com.owr.so.merge.Merge;
import com.owr.so.merge.args.ArgsResolver;

public class Main {

	public static void main(String[] args) throws IOException {

		ArgsResolver resolver = new ArgsResolver();

		new Merge(resolver.resolve(args));

	}

}
