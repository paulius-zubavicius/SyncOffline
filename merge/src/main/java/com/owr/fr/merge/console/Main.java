package com.owr.fr.merge.console;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.owr.fr.merge.Merge;
import com.owr.fr.merge.data.MergedRepos;
import com.owr.frs.model.FileMeta;
import com.owr.frs.model.FilesRepo;
import com.owr.frs.model.Repositories;
import com.owr.frs.model.RepositoryMeta;
import com.owr.frs.storage.RepoStorage;
import com.owr.frs.storage.Storage;

public class Main {

	private static final int MERGE_MAX = 2;

	public static void main(String[] args) throws IOException {

		if (args.length == MERGE_MAX) {

			// Read repositories
			RepoStorage rst = new RepoStorage();
			Repositories repos = rst.load();
			List<RepositoryMeta> reposForMerge = getExistingReposByName(repos, args);

			if (reposForMerge.size() != MERGE_MAX) {
				System.err.println("Fat fingers... sorry... Magic repos!");
				return;
			}

			if (reposForMerge.get(0).getName().equalsIgnoreCase(reposForMerge.get(1).getName())) {
				System.err.println("Merging it with it self?");
				return;
			}

			// Load repos
			FilesRepo filesRepo1 = loadRepoMetaData(reposForMerge.get(0));
			FilesRepo filesRepo2 = loadRepoMetaData(reposForMerge.get(1));

			// Merge repos
			Merge merge = new Merge();
			MergedRepos result = new MergedRepos();
			merge.merge(filesRepo1, filesRepo2, result);

			// Save result
			Storage<MergedRepos> strResult = new Storage<>();
			String resultFileName = makeFileName(reposForMerge.get(0).getName(), reposForMerge.get(1).getName(),
					"merged");
			strResult.save(Paths.get(resultFileName), result);

			// Output repos
			ConsoleOutput.resultPrint(result);

			return;
		}
		ConsoleOutput.printConsoleHelp();

	}

	private static String makeFileName(String name1, String name2, String ext) {

		int comp = name1.compareTo(name2);
		String name = "";
		if (comp > 0) {
			name = name1 + "-" + name2;
		} else if (comp < 0) {
			name = name2 + "-" + name1;
		} else {
			throw new AssertionError();
		}

		return name + "." + ext;
	}

	private static FilesRepo loadRepoMetaData(RepositoryMeta repositoryMeta) throws IOException {

		Storage<FilesRepo> str = new Storage<>();
		Path repoFile = Paths.get(repositoryMeta.getName()+".repo");

		FilesRepo filesRepo = str.load(repoFile, FilesRepo.class);

		Set<String> keys = filesRepo.getBranchData().keySet();

		FileMeta fm = null;

		for (String key : keys) {

			fm = filesRepo.getBranchData().get(key);

			fm.setRepo(repositoryMeta.getName());
			fm.setPath(key);

			if (!filesRepo.getBranchDataByMd5().containsKey(fm.getMd5())) {
				filesRepo.getBranchDataByMd5().put(fm.getMd5(), new ArrayList<>());
			}

			filesRepo.getBranchDataByMd5().get(fm.getMd5()).add(fm);
		}

		filesRepo.setName(repositoryMeta.getName());

		return filesRepo;
	}

	private static List<RepositoryMeta> getExistingReposByName(Repositories repos, String... repoNames) {
		List<RepositoryMeta> result = new ArrayList<>();
		for (String repoName : repoNames) {
			if (repos.isItExist(repoName)) {
				result.add(repos.getRepoMeta(repoName));
			} else {
				ConsoleOutput.repoNotFound(repoName);
			}
		}

		return result;
	}

}
