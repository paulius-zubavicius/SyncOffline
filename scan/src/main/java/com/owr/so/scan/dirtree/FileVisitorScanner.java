package com.owr.so.scan.dirtree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.owr.so.diff.model.DirEntity;
import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.scan.events.IDirTreeEventsListener;
import com.owr.so.scan.utils.FileEntityUtil;

/**
 * @author Paulius Zubavicius
 *
 */
public class FileVisitorScanner extends SimpleFileVisitor<Path> {

	private IDirTreeEventsListener listener;
	private DirTree currentTree;
	private DirTree newTree;

	public FileVisitorScanner(DirTree newTree, DirTree currentTree, IDirTreeEventsListener listener) {
		this.listener = listener;
		this.currentTree = currentTree;
		this.newTree = newTree;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		String dirPathString = putToDirTree(dir);
		listener.readDirOk(dirPathString, dir, attrs);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		if (attrs.isRegularFile()) {
			FileEntity entity = new FileEntity();
			entity.setName(file.getFileName().toString());
			entity.setAccessed(attrs.lastAccessTime().toMillis());
			entity.setModified(attrs.lastModifiedTime().toMillis());
			entity.setSize(attrs.size());

			// FIXME move to separate method
			String excludedFilePath = FileEntityUtil.getExcludeRootPath(file.getParent(), newTree.getDirTreeRootPath());
			DirEntity dirEntity = newTree.getDirTree().get(excludedFilePath);

			if (dirEntity == null) {
				throw new RuntimeException();
			}
			entity.setDir(dirEntity);
			// Add it self
			dirEntity.getFiles().add(entity);

			FileEntity oldOneEntity = currentTree.getFilesByPath()
					.get(FileEntityUtil.getExcludeRootPath(file.toAbsolutePath(), newTree.getDirTreeRootPath()));

			boolean modifications = checkForModification(entity, oldOneEntity);
			if (modifications) {
				entity.setChecksum(FileEntityUtil.fileChecksum(file));
			} else {
				entity.setChecksum(oldOneEntity.getChecksum());
			}

			newTree.initTransientFields(entity);

			listener.readFileOk(entity, modifications);
		} else {
			listener.skipped(file, attrs);
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		listener.readFailed(file, exc);
		return FileVisitResult.CONTINUE;
	}

	private String putToDirTree(Path dir) {
		String dirPathString = FileEntityUtil.getExcludeRootPath(dir, newTree.getDirTreeRootPath());
		newTree.getDirTree().put(dirPathString, new DirEntity(dirPathString));
		return dirPathString;
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
