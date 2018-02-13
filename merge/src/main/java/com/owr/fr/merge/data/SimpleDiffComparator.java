package com.owr.fr.merge.data;

import java.util.Comparator;

import com.owr.fr.merge.diff.RepoFile;
import com.owr.fr.merge.diff.SimpleDiff;

public class SimpleDiffComparator implements Comparator<SimpleDiff> {

	@Override
	public int compare(SimpleDiff o1, SimpleDiff o2) {

		if (o1 == o2) {
			return 0;
		}

		RepoFile rf1 = o1.getTarget();
		RepoFile rf2 = o2.getTarget();

		if (rf1 == null || rf1.getPath() == null) {
			return -1;
		}

		if (rf2 == null || rf2.getPath() == null) {
			return 1;
		}

		return rf1.getPath().compareTo(rf2.getPath());

	}

}
