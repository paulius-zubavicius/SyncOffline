package com.owr.so.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class CheckSumUtil {

	public static String checkSum(Path path) throws IOException {
		FileInputStream fis = new FileInputStream(path.toFile());
		String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
		fis.close();
		return md5;
	}

}
