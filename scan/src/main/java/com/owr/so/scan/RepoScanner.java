package com.owr.so.scan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owr.so.commons.DataLoader;
import com.owr.so.commons.IScanEventsListener;
import com.owr.so.commons.ScanEvent;
import com.owr.so.commons.Storage;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.RepoFile;
import com.owr.so.diff.model.RepoMetaData;

/**
 * @author Paulius Zubavicius
 *
 */
public class RepoScanner {

	public void scan(Path repoPath, IScanEventsListener listener) {

		DataLoader dl = new DataLoader(repoPath, listener);

		RepoData newDataTree = scanTree(dl.getRepoFile(), dl.getRepoData(), dl.getMeta(), listener);
		Storage.save(dl.getDataFilePath(), newDataTree);
	}

	private RepoData scanTree(RepoFile repo, RepoData data, RepoMetaData meta, IScanEventsListener listener) {
		Instant instant1 = Instant.now();
		RepoData result = new RepoData();

		try {
			Map<String, List<FileEntity>> dirTree = new HashMap<>();
			Files.walkFileTree(Path.of(repo.getPath()),
					new FileVisitorScanner(dirTree, meta, repo.getPath(), repo.getExcludes(), listener));
			result.getTree().putAll(dirTree);
		} catch (IOException e) {
			e.printStackTrace();
			listener.event(ScanEvent.DONE_FAIL, e);
		}

		result.setLastScan(LocalDateTime.now());

		listener.event(ScanEvent.DONE_SUCCESS, Duration.between(instant1, Instant.now()), result);
		return result;
	}

}
