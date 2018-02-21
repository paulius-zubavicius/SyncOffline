package com.owr.so.scanner.dirtree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import com.owr.so.model.DirEntity;
import com.owr.so.model.DirTreeEntity;
import com.owr.so.model.FileEntity;
import com.owr.so.utils.CheckSumUtil;

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

		String dirPathString = dir.toAbsolutePath().toString();
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

			DirEntity dirEntity = newTree.getDirTree().get(file.getParent().toString());
			assert null == dirEntity;
			entity.setDir(dirEntity);

			FileEntity oldOneEntity = currentTree.getFilesByPath().get(file.toAbsolutePath().toString());

			if (checkForModification(entity, oldOneEntity)) {
				entity.setHashSum(CheckSumUtil.checkSum(file));
			} else {
				entity.setHashSum(oldOneEntity.getHashSum());
			}

			// Adding to result
			addToTransiantMaps(newTree, entity);

			listener.readFileOk(entity);
		} else {
			listener.skiping(file, attrs);
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		listener.readFailed(file, exc);
		return FileVisitResult.CONTINUE;
	}

	private void addToTransiantMaps(DirTreeEntity result, FileEntity entity) {

		result.getFiles().add(entity);
		result.getFilesByPath().put(entity.getAbsolutePath(), entity);
		if (!result.getFilesByHashSum().containsKey(entity.getHashSum())) {
			result.getFilesByHashSum().put(entity.getHashSum(), new ArrayList<>());
		}
		result.getFilesByHashSum().get(entity.getHashSum()).add(entity);

		entity.getDir().getFiles().add(entity);

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
