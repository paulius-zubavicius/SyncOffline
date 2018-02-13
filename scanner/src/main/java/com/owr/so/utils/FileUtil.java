package com.owr.so.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

	public static Stream<Path> scanFnames(String sDir) throws IOException {
		return Files.find(Paths.get(sDir), 999, (p, bfa) -> bfa.isRegularFile());// .forEach(System.out::println);
	}

}
