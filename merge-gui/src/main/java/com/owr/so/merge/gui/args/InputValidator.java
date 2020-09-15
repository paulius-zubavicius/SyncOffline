package com.owr.so.merge.gui.args;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

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

		File file1 = Path.of(metaFile1Path).toFile();
		if (!file1.exists() && !file1.isFile()) {
			throw new RuntimeException("MetaFile1 file path doesn't exists: [" + metaFile1Path + "]");
		}

		File file2 = Path.of(metaFile2Path).toFile();
		if (!file2.exists() && !file2.isFile()) {
			throw new RuntimeException("MetaFile2 file path doesn't exists: [" + metaFile2Path + "]");
		}

//		if (OSType.WIN.equals(OSType.getOSTypeCurrent())) {
//
//			if (metaFile1Path.equalsIgnoreCase(metaFile2Path)) {
//				throw new RuntimeException(
//						"MetaFile1 and MetaFile2 is the same file: [" + metaFile1Path + "] [" + metaFile2Path + "]");
//			}
//
//		} else {
//			if (metaFile1Path.equals(metaFile2Path)) {
//				throw new RuntimeException(
//						"MetaFile1 and MetaFile2 is the same file: [" + metaFile1Path + "] [" + metaFile2Path + "]");
//			}
//		}
	}

//	public static void validateSubDirBeforeLoading(String metaFilePath, String subdir) {
//
//		if (StringUtils.isEmpty(subdir)) {
//			throw new RuntimeException("Sub directory path doesn't specified or empty: [" + subdir + "]");
//		}
//
//	}

//	public static void validateSubDirBeforeLoading(String subdir, int parameterNo) {
//
//		String separator = OSType.getOSFileSeparator();
//
//		if (!subdir.startsWith(separator)) {
//			throw new RuntimeException("Sub directory path should start with: [" + separator + "]");
//		}
//
//		if (subdir.length() <= 1) {
//			throw new RuntimeException(
//					"It's not a sub directory: [" + separator + "] Sub directory should looks like: [" + separator
//							+ "subdir] [" + separator + "subdir" + separator + "subsubdir]");
//		}
//
//		if (subdir.endsWith(separator)) {
//			throw new RuntimeException("Sub directory path shouldn't end with: [" + separator + "]");
//		}
//
//	}

//	public static void validateSubDirAfterLoading(DirTree tree, String subdir, int repoId) {
//
//		boolean caseSensitive = !OSType.isTheSame(tree.getOsCode(), OSType.WIN);
//
//		if (!isItSubdirOfTree(tree, subdir, caseSensitive)) {
//			throw new RuntimeException(
//					"Sub directory (" + repoId + ") [" + subdir + "] doesn't exist in tree (" + repoId + ")");
//		}
//
//	}

//	private static boolean isItSubdirOfTree(DirTree tree1, String subdir, boolean caseSensitivePaths) {
//		Set<String> dirs = tree1.getDirTree().keySet();
//		for (String dirStr : dirs) {
//
//			if (caseSensitivePaths) {
//				if (dirStr.startsWith(subdir)) {
//					return true;
//				}
//			} else {
//				if (dirStr.toLowerCase().startsWith(subdir.toLowerCase())) {
//					return true;
//				}
//			}
//
//		}
//		return false;
//	}

}
