package com.owr.so.scanner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import com.owr.so.console.ConsoleOutput;
import com.owr.so.model.FileMetaDepricated;
import com.owr.so.model.FilesRepoDepricated;
import com.owr.so.model.Repositories;
import com.owr.so.storage.RepoStorage;
import com.owr.so.storage.Storage;
import com.owr.so.utils.CheckSumUtil;
import com.owr.so.utils.ConvertUtil;
import com.owr.so.utils.FileUtil;

public class Scanner {

	public static final String REPO_SUFFIX = ".repo";

	private Repositories repositories = null;

	public Scanner() throws IOException {
		RepoStorage rst = new RepoStorage();
		repositories = rst.load();
	}

	public void scanFilesRepo(String repoName, boolean forceUpdate) throws IOException, URISyntaxException {

		Instant intant1 = Instant.now();

		String repoPath = repoName + REPO_SUFFIX;
		String repoPathOld = repoPath + ".fordelete";

		FilesRepoDepricated repoNew = new FilesRepoDepricated();
		FilesRepoDepricated repoOld = loadExistingData(repoPath, repoPathOld, forceUpdate);

		String repoRootDir = repositories.getPath(repoName);
		Stream<Path> paths = FileUtil.scanFnames(repoRootDir);

		paths.forEach(filePath -> {

			FileMetaDepricated f = new FileMetaDepricated();
			String checkSum = readMeta(f, filePath, repoRootDir, repoOld);
			repoNew.getBranchData().put(checkSum, f);

		});
		Storage<FilesRepoDepricated> str = new Storage<>();

		str.save(Paths.get(repoPath), repoNew);

		Instant intant2 = Instant.now();
		Duration timeElapsed = Duration.between(intant1, intant2);

		ConsoleOutput.printStatistics(repoNew, timeElapsed);

	}

	private FilesRepoDepricated loadExistingData(String repoPath, String repoPathOld, boolean forceUpdate) throws IOException {
		Path existingRepoPath = Paths.get(repoPath);
		File existingFile = existingRepoPath.toFile();
		FilesRepoDepricated repoOld = new FilesRepoDepricated();
		if (existingFile.exists()) {

			if (!forceUpdate) {
				// load old data
				Storage<FilesRepoDepricated> str = new Storage<>();
				repoOld = str.load(existingRepoPath, FilesRepoDepricated.class);
			}
			existingFile.delete();
		}
		return repoOld;
	}

	private String readMeta(final FileMetaDepricated fNew, Path filePath, String repoRootDir, FilesRepoDepricated repoOld) {
		String relativePath = filePath.toString().substring(repoRootDir.length());

		try {
			BasicFileAttributes file = Files.readAttributes(filePath, BasicFileAttributes.class);

			if (file.isRegularFile()) {
				FileMetaDepricated fOld = repoOld.getBranchData().get(relativePath);

				// updateFileMeta(fNew, filePath, file);
				fNew.setSize(file.size());
				fNew.setModified(file.lastModifiedTime().toMillis());
				fNew.setAccessed(file.lastAccessTime().toMillis());
				if (checkForModification(fNew, fOld)) {
					System.out.print("[" + ConvertUtil.getSizeInHumanFormat(file.size()) + "] >>  " + relativePath);
					fNew.setMd5(CheckSumUtil.checkSum(filePath));
					System.out.println(" >>[" + fNew.getMd5() + "]");
				} else {
					fNew.setMd5(fOld.getMd5());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return relativePath;

	}

	private boolean checkForModification(FileMetaDepricated fNew, FileMetaDepricated fOld) {

		// New file?
		if (fOld == null) {
			return true;
		}

		// Exist but was changed?
		if ((fOld.getModified() != fNew.getModified()) || (fOld.getSize() != fNew.getSize())) {
			return true;
		}

		return false;
	}

	public void addRepo(String repoName, String path) throws IOException {
		RepoStorage rst = new RepoStorage();
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		rst.addRepo(repositories, repoName, path);
	}

	public Repositories getRepolist() {
		return repositories;
	}

	public void removeRepo(String repoName) throws IOException {
		RepoStorage rst = new RepoStorage();
		rst.removeRepo(repositories, repoName);

	}

}
