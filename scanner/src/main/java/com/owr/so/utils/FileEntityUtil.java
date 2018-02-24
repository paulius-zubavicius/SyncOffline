package com.owr.so.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Paulius Zubavicius
 *
 */
public class FileEntityUtil {

	public static String checkSum(Path path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path.toFile());
			return org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

		}
	}

	public static String getExcludeRootPath(Path dir, String rootPath) {
		String excludedPath = dir.toAbsolutePath().toString();
		return excludedPath.substring(rootPath.length());
	}

}
