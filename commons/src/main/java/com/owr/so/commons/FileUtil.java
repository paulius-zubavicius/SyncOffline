package com.owr.so.commons;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

	public static boolean pathExists(String pathStr) {
		Path path = Paths.get(pathStr);
		File file = path.toFile();
		return file.exists();
	}
}
