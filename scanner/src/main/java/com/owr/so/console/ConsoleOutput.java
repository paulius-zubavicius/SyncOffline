package com.owr.so.console;

import static com.owr.so.console.Const.ANSI_RESET;
import static com.owr.so.console.Const.ANSI_WHITE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.Set;

import com.owr.so.model.FilesRepoDepricated;
import com.owr.so.model.Repositories;
import com.owr.so.model.RepositoryMeta;
import com.owr.so.model.Version;
import com.owr.so.scanner.Scanner;
import com.owr.so.utils.ConvertUtil;

public class ConsoleOutput {

	public static void printConsoleHelp() {
		System.out.println();
		System.out.println(" Files Repo Scanner (data model version: " + Version.version + ")");
		System.out.println();
		System.out.println(" Usage:");
		System.out.println(ANSI_WHITE + "      LIST   " + ANSI_RESET + ": java -jar scanner.jar list");
		System.out.println(
				ANSI_WHITE + "      ADD    " + ANSI_RESET + ": java -jar scanner.jar add repoName /path/to/repo");
		System.out.println(ANSI_WHITE + "      REMOVE " + ANSI_RESET + ": java -jar scanner.jar remove repoName");
		System.out.println(ANSI_WHITE + "      SCAN   " + ANSI_RESET + ": java -jar scanner.jar scan repoName");
		System.out.println(ANSI_WHITE + "      UPDATE " + ANSI_RESET + ": java -jar scanner.jar update repoName");
		System.out.println();
	}

	public static void printDone(String action) {
		System.out.println();
		System.out.println(" 【✔】 DONE  " + action);
		System.out.println();
	}

	public static void printStatistics(FilesRepoDepricated branch, Duration timeElapsed) {

		long size = 0;
		Set<String> keys = branch.getBranchData().keySet();
		for (String checkSum : keys) {
			size += branch.getBranchData().get(checkSum).getSize();
		}

		System.out.println("Count    : " + branch.getBranchData().size());
		System.out.println("Size     : " + ConvertUtil.getSizeInHumanFormat(size));
		System.out.println("Duration : " + ConvertUtil.getTimeInHumanFormat(timeElapsed));
	}

	public static void repoListOutput(Repositories repolist) throws IOException {

		if (repolist == null || repolist.getRepos().isEmpty()) {
			System.out.println("No repositories added");

		} else {
			for (RepositoryMeta rm : repolist.getRepos()) {
				Path path = Paths.get(rm.getName() + Scanner.REPO_SUFFIX);
				String lastScan = "?";
				if (path.toFile().exists()) {
					BasicFileAttributes file = Files.readAttributes(path, BasicFileAttributes.class);
					lastScan = file.lastModifiedTime().toString();
				}

				System.out.println(rm.getName() + " : " + rm.getPath() + " : " + lastScan);
			}
		}
	}
}
