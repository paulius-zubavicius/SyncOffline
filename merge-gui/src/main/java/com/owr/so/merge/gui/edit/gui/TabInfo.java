package com.owr.so.merge.gui.edit.gui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.merge.gui.DirTrees;
import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.FileEntity;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class TabInfo extends Tab implements ReloadableTab{

	public TabInfo(DirTrees dirTrees) {
		super("Info");

		AnchorPane anchorPane = new AnchorPane();
		setContent(anchorPane);

		GridPane gridPane = new GridPane();

		AnchorPane.setLeftAnchor(gridPane, 9d);
		AnchorPane.setRightAnchor(gridPane, 9d);
		AnchorPane.setTopAnchor(gridPane, 12d);

		anchorPane.getChildren().add(gridPane);

		ColumnConstraints colTitles = new ColumnConstraints();
		colTitles.setFillWidth(false);
		colTitles.setHgrow(Priority.NEVER);
		colTitles.setPercentWidth(-1);
		colTitles.setMinWidth(171);
		colTitles.setMaxWidth(180);
		gridPane.getColumnConstraints().add(colTitles);

		ColumnConstraints colTree1 = new ColumnConstraints();
		colTree1.setFillWidth(true);
		colTree1.setHgrow(Priority.ALWAYS);
		colTree1.setPercentWidth(-1);
		gridPane.getColumnConstraints().add(colTree1);

		ColumnConstraints colTree2 = new ColumnConstraints();
		colTree2.setFillWidth(true);
		colTree2.setHgrow(Priority.ALWAYS);
		colTree2.setPercentWidth(-1);
		gridPane.getColumnConstraints().add(colTree2);

//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//		gridPane.getRowConstraints().add(createRowConstraint());
//
//		createMetaFileSection(gridPane, dirTrees);
//
//		createSyncRootSection(gridPane, dirTrees);
//
//		createSyncSubDirSection(gridPane, dirTrees);

	}
	
	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

//	private void createSyncSubDirSection(GridPane gridPane, DirTrees dirTrees) {
//		Label label = null;
//		TextField textFiled = null;
//
//		label = new Label("Synchronizing subdir");
//		label.setFont(new Font("System Bold", 13));
//		GridPane.setConstraints(label, 0, 10);
//		GridPane.setMargin(label, new Insets(3, 5, 3, 5));
//		gridPane.getChildren().add(label);
//
//		label = new Label("Files count");
//		GridPane.setConstraints(label, 0, 11);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label("Dirs count");
//		GridPane.setConstraints(label, 0, 12);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label("Size");
//		GridPane.setConstraints(label, 0, 13);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		String subDir1 = dirTrees.getTree1().getSubDir();
//		if (!subDir1.isEmpty()) {
//			textFiled = new TextField(subDir1);
//			textFiled.setEditable(false);
//			GridPane.setConstraints(textFiled, 1, 10);
//			gridPane.getChildren().add(textFiled);
//
//			label = new Label("" + countFilesInSubDir(subDir1, dirTrees.getTree1()));
//			GridPane.setConstraints(label, 1, 11);
//			gridPane.getChildren().add(label);
//
//			label = new Label("" + countDirsInSubDir(subDir1, dirTrees.getTree1()));
//			GridPane.setConstraints(label, 1, 12);
//			gridPane.getChildren().add(label);
//
//			label = new Label(ConvertUtil.getSizeInHumanFormat(calcSizeOfSubdir(subDir1, dirTrees.getTree1())));
//			GridPane.setConstraints(label, 1, 13);
//			gridPane.getChildren().add(label);
//		} else {
//			label = new Label("N/A");
//			GridPane.setConstraints(label, 1, 10);
//			gridPane.getChildren().add(label);
//		}
//
//		String subDir2 = dirTrees.getTree2().getSubDir();
//		if (!subDir2.isEmpty()) {
//
//			textFiled = new TextField(subDir2);
//			textFiled.setEditable(false);
//			GridPane.setConstraints(textFiled, 2, 10);
//			gridPane.getChildren().add(textFiled);
//
//			label = new Label("" + countFilesInSubDir(subDir2, dirTrees.getTree2()));
//			GridPane.setConstraints(label, 2, 11);
//			gridPane.getChildren().add(label);
//
//			label = new Label("" + countDirsInSubDir(subDir2, dirTrees.getTree2()));
//			GridPane.setConstraints(label, 2, 12);
//			gridPane.getChildren().add(label);
//
//			label = new Label(ConvertUtil.getSizeInHumanFormat(calcSizeOfSubdir(subDir2, dirTrees.getTree2())));
//			GridPane.setConstraints(label, 2, 13);
//			gridPane.getChildren().add(label);
//
//		} else {
//			label = new Label("N/A");
//			GridPane.setConstraints(label, 2, 10);
//			gridPane.getChildren().add(label);
//		}
//
//	}
//
//	private long calcSizeOfSubdir(String subDir, DirTree tree) {
//		int result = 0;
//		Map<String, FileEntity> map = tree.getFilesByPath();
//		for (Entry<String, FileEntity> entry : map.entrySet()) {
//			//FIXME comparing OS insensitive
//			if (entry.getKey().startsWith(subDir)) {
//				result += entry.getValue().getSize();
//			}
//		}
//
//		return result;
//	}
//
//	private int countDirsInSubDir(String subDir, DirTree tree) {
//		int result = 0;
//		Set<String> paths = tree.getDirTree().keySet();
//		for (String path : paths) {
//			//FIXME comparing OS insensitive
//			if (path.startsWith(subDir)) {
//				result++;
//			}
//		}
//		
//		return result - 1;
//	}
//
//	private int countFilesInSubDir(String subDir, DirTree tree) {
//
//		int result = 0;
//		Set<String> paths = tree.getFilesByPath().keySet();
//		for (String path : paths) {
//			//FIXME comparing OS insensitive
//			if (path.startsWith(subDir)) {
//				result++;
//			}
//		}
//
//		return result;
//	}
//
//	private void createSyncRootSection(GridPane gridPane, DirTrees dirTrees) {
//		Label label = null;
//
//		label = new Label("Synchronizing root");
//		label.setFont(new Font("System Bold", 13));
//		GridPane.setConstraints(label, 0, 4);
//		GridPane.setMargin(label, new Insets(3, 5, 3, 5));
//		gridPane.getChildren().add(label);
//
//		TextField textFiled = null;
//
//		textFiled = new TextField(dirTrees.getTree1().getDirTreeRootPath());
//		textFiled.setEditable(false);
//		GridPane.setConstraints(textFiled, 1, 4);
//		gridPane.getChildren().add(textFiled);
//
//		textFiled = new TextField(dirTrees.getTree2().getDirTreeRootPath());
//		textFiled.setEditable(false);
//		GridPane.setConstraints(textFiled, 2, 4);
//		gridPane.getChildren().add(textFiled);
//
//		label = new Label("Files count");
//		GridPane.setConstraints(label, 0, 5);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label("" + dirTrees.getTree1().getFiles().size());
//		GridPane.setConstraints(label, 1, 5);
//		gridPane.getChildren().add(label);
//
//		label = new Label("" + dirTrees.getTree2().getFiles().size());
//		GridPane.setConstraints(label, 2, 5);
//		gridPane.getChildren().add(label);
//
//		label = new Label("Dirs count");
//		GridPane.setConstraints(label, 0, 6);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label("" + countDirsInSubDir("/", dirTrees.getTree1()));
//		GridPane.setConstraints(label, 1, 6);
//		gridPane.getChildren().add(label);
//
//		label = new Label("" + countDirsInSubDir("/", dirTrees.getTree2()));
//		GridPane.setConstraints(label, 2, 6);
//		gridPane.getChildren().add(label);
//
//		label = new Label("Size");
//		GridPane.setConstraints(label, 0, 7);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label(ConvertUtil.getSizeInHumanFormat(calcSizeOfRepo(dirTrees.getTree1().getFiles())));
//		GridPane.setConstraints(label, 1, 7);
//		gridPane.getChildren().add(label);
//
//		label = new Label(ConvertUtil.getSizeInHumanFormat(calcSizeOfRepo(dirTrees.getTree2().getFiles())));
//		GridPane.setConstraints(label, 2, 7);
//		gridPane.getChildren().add(label);
//
//	}
//
//	private long calcSizeOfRepo(List<FileEntity> files) {
//		long readSize = 0;
//		for (FileEntity ent : files) {
//			readSize += ent.getSize();
//		}
//		return readSize;
//	}
//
//	private void createMetaFileSection(GridPane gridPane, DirTrees dirTrees) {
//
//		Label label = null;
//
//		label = new Label("Meta file");
//		label.setFont(new Font("System Bold", 13));
//		GridPane.setConstraints(label, 0, 0);
//		GridPane.setMargin(label, new Insets(3, 5, 3, 5));
//		gridPane.getChildren().add(label);
//
//		label = new Label("Reposityry (" + dirTrees.getTree1().getId() + ")");
//		GridPane.setConstraints(label, 1, 0);
//		gridPane.getChildren().add(label);
//
//		label = new Label("Reposityry (" + dirTrees.getTree2().getId() + ")");
//		GridPane.setConstraints(label, 2, 0);
//		gridPane.getChildren().add(label);
//
//		label = new Label("Meta file path");
//		GridPane.setConstraints(label, 0, 1);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		TextField textFiled = null;
//
//		textFiled = new TextField(dirTrees.getTree1().getMetaFilePath());
//		textFiled.setEditable(false);
//		GridPane.setConstraints(textFiled, 1, 1);
//		gridPane.getChildren().add(textFiled);
//
//		textFiled = new TextField(dirTrees.getTree2().getMetaFilePath());
//		textFiled.setEditable(false);
//		GridPane.setConstraints(textFiled, 2, 1);
//		gridPane.getChildren().add(textFiled);
//
//		label = new Label("How old");
//		GridPane.setConstraints(label, 0, 2);
//		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
//		gridPane.getChildren().add(label);
//
//		label = new Label(ConvertUtil.getTimeInHumanFormat(dirTrees.getTree1().getMetaFileDuration()));
//		GridPane.setConstraints(label, 1, 2);
//		gridPane.getChildren().add(label);
//
//		label = new Label(ConvertUtil.getTimeInHumanFormat(dirTrees.getTree2().getMetaFileDuration()));
//		GridPane.setConstraints(label, 2, 2);
//		gridPane.getChildren().add(label);
//
//	}
//
//	private RowConstraints createRowConstraint() {
//		RowConstraints result = new RowConstraints();
//		result.setMinHeight(10);
//		result.setPrefHeight(30);
//		result.setVgrow(Priority.SOMETIMES);
//		return result;
//	}

	

}
