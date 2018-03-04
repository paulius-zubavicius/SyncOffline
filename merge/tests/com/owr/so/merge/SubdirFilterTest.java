package com.owr.so.merge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//TODO ReposDifferences tests
public class SubdirFilterTest {

	private static final String SUBDIR = "/subdir0";
	private static final String SUBDIR_EMPTY = "";
	private static final String SUBDIR_DEEPER = "/subdir0/subdir1";

	// @Test
	public void filterSuccessesTest() {
		assertTrue("".startsWith(SUBDIR_EMPTY));
		assertTrue("/dir".startsWith(SUBDIR_EMPTY));
		assertTrue("/d/f".startsWith(SUBDIR_EMPTY));

		assertTrue("/subdir0".startsWith(SUBDIR));
		assertTrue("/subdir0/dir".startsWith(SUBDIR));

		assertTrue("/subdir0/subdir1".startsWith(SUBDIR_DEEPER));
		assertTrue("/subdir0/subdir1/dir".startsWith(SUBDIR_DEEPER));
	}

	// @Test
	public void filterFailsTest() {

		assertFalse("".startsWith(SUBDIR));
		assertFalse("/dir/subdir0".startsWith(SUBDIR));

		assertFalse("/dir/subdir0/subdir1".startsWith(SUBDIR_DEEPER));
		assertFalse("/subdir0/dir".startsWith(SUBDIR_DEEPER));
	}

}
