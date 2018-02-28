package com.owr.so.scanner.dirtree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.owr.so.model.DirEntity;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;
import com.owr.so.utils.FileEntityUtil;

/**
 * @author Paulius Zubavicius
 *
 */
public class FileVisitorScanner extends SimpleFileVisitor<Path> {

	private IScanningLogEventsListener listener;
	private DirTreeEntity currentTree;
	private DirTreeEntity newTree;

	public FileVisitorScanner(DirTreeEntity newTree, DirTreeEntity currentTree, IScanningLogEventsListener listener) {
		this.listener = listener;
		this.currentTree = currentTree;
		this.newTree = newTree;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		String dirPathString = FileEntityUtil.getExcludeRootPath(dir, newTree.getDirTreeRootPath());

		newTree.getDirTree().put(dirPathString, new DirEntity(dirPathString));
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

			String excludedFilePath = FileEntityUtil.getExcludeRootPath(file.getParent(), newTree.getDirTreeRootPath());

			DirEntity dirEntity = newTree.getDirTree().get(excludedFilePath);
			assert null == dirEntity;
			entity.setDir(dirEntity);
			// Add it self
			dirEntity.getFiles().add(entity);

			FileEntity oldOneEntity = currentTree.getFilesByPath()
					.get(FileEntityUtil.getExcludeRootPath(file.toAbsolutePath(), newTree.getDirTreeRootPath()));

			boolean modifications = checkForModification(entity, oldOneEntity);
			if (modifications) {
				entity.setHashSum(FileEntityUtil.checkSum(file));
			} else {
				entity.setHashSum(oldOneEntity.getHashSum());
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
