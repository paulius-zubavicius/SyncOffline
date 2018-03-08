package com.owr.so.merge.args;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.owr.so.commons.FileUtil;
import com.owr.so.commons.OSType;
import com.owr.so.model.DirTreeEntity;

public class InputValidator {

	// private static String[] UNALOWED_UNIX = { "/" };
	// private static String[] UNALOWED_WIN = { "<", ">", ":", "\"", "/", "\\", "|",
	// "?", "*" };

	// private static String SEP_LINUX = "/";
	// private static String SEP_WIN = "\\";

	public static void validateMetaFilesPaths(String metaFile1Path, String metaFile2Path) {

		if (StringUtils.isEmpty(metaFile1Path)) {
			throw new RuntimeException("MetaFile1 file path doesn't specified or empty: [" + metaFile1Path + "]");
		}

		if (StringUtils.isEmpty(metaFile2Path)) {
			throw new RuntimeException("MetaFile2 file path doesn't specified or empty: [" + metaFile2Path + "]");
		}

		if (!FileUtil.pathExists(metaFile1Path)) {
			throw new RuntimeException("MetaFile1 file path doesn't exists: [" + metaFile1Path + "]");
		}

		if (!FileUtil.pathExists(metaFile1Path)) {
			throw new RuntimeException("MetaFile2 file path doesn't exists: [" + metaFile2Path + "]");
		}

		if (OSType.WIN.equals(OSType.getOSTypeCurrent())) {

			if (metaFile1Path.equalsIgnoreCase(metaFile2Path)) {
				throw new RuntimeException(
						"MetaFile1 and MetaFile2 is the same file: [" + metaFile1Path + "] [" + metaFile2Path + "]");
			}

		} else {
			if (metaFile1Path.equals(metaFile2Path)) {
				throw new RuntimeException(
						"MetaFile1 and MetaFile2 is the same file: [" + metaFile1Path + "] [" + metaFile2Path + "]");
			}
		}
	}

	public static void validateSubDirBeforeLoading(String metaFilePath, String subdir) {

		if (StringUtils.isEmpty(subdir)) {
			throw new RuntimeException("Sub directory path doesn't specified or empty: [" + subdir + "]");
		}

	}

	public static void validateSubDirAfterLoading(DirTreeEntity tree, String subdir, int parameterNo) {

		String separator = OSType.getOSFileSeparator();

		if (!subdir.startsWith(separator)) {
			throw new RuntimeException("Sub directory path should start with: [" + separator + "]");
		}

		if (subdir.length() <= 1) {
			throw new RuntimeException(
					"It's not a sub directory: [" + separator + "] Sub directory should looks like: [" + separator
							+ "subdir] [" + separator + "subdir" + separator + "subsubdir]");
		}

		if (subdir.endsWith(separator)) {
			throw new RuntimeException("Sub directory path shouldn't end with: [" + separator + "]");
		}

		boolean caseSensitive = !OSType.isTheSame(tree.getOsCode(), OSType.WIN);

		if (!isItSubdirOfTree(tree, subdir, caseSensitive)) {
			throw new RuntimeException(
					"Sub directory (" + parameterNo + ") [" + subdir + "] doesn't exist in tree (" + parameterNo + ")");
		}

	}

	private static boolean isItSubdirOfTree(DirTreeEntity tree1, String subdir, boolean caseSensitivePaths) {
		Set<String> dirs = tree1.getDirTree().keySet();
		for (String dirStr : dirs) {

			if (caseSensitivePaths) {
				if (dirStr.startsWith(subdir)) {
					return true;
				}
			} else {
				if (dirStr.toLowerCase().startsWith(subdir.toLowerCase())) {
					return true;
				}
			}

		}
		return false;
	}

}
