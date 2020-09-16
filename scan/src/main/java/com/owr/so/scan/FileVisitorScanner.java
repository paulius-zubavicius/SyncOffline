package com.owr.so.scan;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.owr.so.commons.IScanEventsListener;
import com.owr.so.commons.ScanEvent;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.scan.utils.FileEntityUtil;

/**
 * @author Paulius Zubavicius
 *
 */
public class FileVisitorScanner extends SimpleFileVisitor<Path> {

	private IScanEventsListener listener;
	private Map<String, List<FileEntity>> dirTree;
	private List<String> excludes = new ArrayList<>();
	private String path;
	private RepoMetaData metaData;
	private String prevDir = "";

	public FileVisitorScanner(Map<String, List<FileEntity>> dirTree, RepoMetaData metaData, String path,
			List<String> excludes, IScanEventsListener listener) {
		this.listener = listener;
		this.dirTree = dirTree;
		this.excludes = excludes;
		this.path = path;
		this.metaData = metaData;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		
		if (skipPath(dir)) {
			listener.event(ScanEvent.SCAN_NEW_DIR_SKIPING, dir);
			return FileVisitResult.CONTINUE;
		}

		dirTree.put(FileEntityUtil.getExcludeRootPath(dir, path), new ArrayList<>());

//		listener.event(ScanEvent.SCAN_NEW_DIR, dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		if (skipPath(file)) {
			listener.event(ScanEvent.SCAN_NEW_DIR_SKIPING, file);
			return FileVisitResult.CONTINUE;
		}

		if (attrs.isRegularFile()) {

			if (!prevDir.equals(file.getParent().toAbsolutePath().toString())) {
				prevDir = file.getParent().toAbsolutePath().toString();
				listener.event(ScanEvent.SCAN_NEW_DIR, file.getParent());
			}

			String excludedFilePath = FileEntityUtil.getExcludeRootPath(file.getParent(), path);
			List<FileEntity> dirEntity = dirTree.get(excludedFilePath);

			if (dirEntity == null) {
				throw new RuntimeException();
			}

			FileEntity entity = createFileEntity(file, attrs);
			dirEntity.add(entity);

			String key = FileEntityUtil.getExcludeRootPath(file.toAbsolutePath(), path);
			FileEntity oldOneEntity = metaData.getFilesByPath().get(key) == null ? null
					: metaData.getFilesByPath().get(key).getFile();

			if (checkForModification(entity, oldOneEntity)) {
				entity.setChecksum(FileEntityUtil.fileChecksum(file));
				listener.event(ScanEvent.SCAN_FILE_OK_NEW, entity);
			} else {
				entity.setChecksum(oldOneEntity.getChecksum());
				listener.event(ScanEvent.SCAN_FILE_OK_OLD, entity);
			}

		} else {
			listener.event(ScanEvent.SCAN_FILE_SKIPPED, file, attrs);
		}

		return FileVisitResult.CONTINUE;
	}

	private boolean skipPath(Path file) {
		String dirPathString = FileEntityUtil.getExcludeRootPath(file, path);
		return excludes != null
				&& excludes.stream().anyMatch(excStr -> !dirPathString.isEmpty() && dirPathString.startsWith(excStr));
	}

	private FileEntity createFileEntity(Path file, BasicFileAttributes attrs) {
		FileEntity entity = new FileEntity();
		entity.setName(file.getFileName().toString());
		entity.setAccessed(attrs.lastAccessTime().toMillis());
		entity.setModified(attrs.lastModifiedTime().toMillis());
		entity.setSize(attrs.size());
		return entity;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		listener.event(ScanEvent.SCAN_FAILED, file, exc);
		return FileVisitResult.CONTINUE;
	}

	private boolean checkForModification(FileEntity fNew, FileEntity fOld) {

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

}
