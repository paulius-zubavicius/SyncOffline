package com.owr.so.diff;

import java.io.IOException;

import com.owr.so.diff.args.ArgsResolver;

public class App {

	public static void main(String[] args) throws IOException {
		new Merge(new ArgsResolver().resolve(args));
	}

}
