package com.owr.so.merge.app;

import java.io.IOException;

import com.owr.so.merge.Merge;
import com.owr.so.merge.args.ArgsResolver;
import com.owr.so.merge.log.IMergeLogEventsListener;
import com.owr.so.merge.log.MergeLogEventsListener;
import com.owr.so.model.DirTreeEntity;

public class Main {

	public static void main(String[] args) throws IOException {

		
		/**
		 * Log events listener
		 */
		IMergeLogEventsListener logEventsListener = new MergeLogEventsListener();
		
		ArgsResolver resolver = new ArgsResolver(args, logEventsListener);
		DirTreeEntity tree1 = resolver.getTree1();
		DirTreeEntity tree2 = resolver.getTree2();

		Merge merge = new Merge();
		merge.mergeFlow(tree1, tree2, resolver.isGuiMode(), logEventsListener);

	}

}
