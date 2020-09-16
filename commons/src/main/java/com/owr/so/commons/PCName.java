package com.owr.so.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Deprecated
public class PCName {

	public static String uname() {

		String line = null;
		BufferedReader br = null;

		try {
			br = read("uname -n");
			while ((line = br.readLine()) != null) {
				return line;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		throw new RuntimeException();
	}

	private static BufferedReader read(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command.split(" "));
			process.getOutputStream().close();
			return new BufferedReader(new InputStreamReader(process.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
