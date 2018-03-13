package com.owr.so.merge.edit.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public abstract class MenuController {

	protected String waitForInput(String waitingLineStr, List<String> possibleValues) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(waitingLineStr);
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("Wrong command" + e.getMessage());
		}

		for (String correctVal : possibleValues) {
			if (correctVal.equalsIgnoreCase(input)) {
				return correctVal;
			}
		}

		System.out.println(
				"Wrong command [" + input + "]. Available options: " + Arrays.toString(possibleValues.toArray()));

		return null;
	}
}
