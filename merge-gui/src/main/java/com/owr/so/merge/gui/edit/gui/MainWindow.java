package com.owr.so.merge.gui.edit.gui;

import com.owr.so.merge.gui.DirTrees;
import com.owr.so.merge.gui.edit.gui.diff.modified.FileModifiedTab;
import org.springframework.stereotype.Component;

import com.owr.so.diff.model.DirTreesDifferences;
import com.owr.so.merge.gui.log.IUIEventsListener;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

@Component
public class MainWindow {

	private BorderPane mainPane;

	private HBox mainToolBar;
	private TabPane tabPane;

	private DirTrees dirTrees;
	private DirTreesDifferences treeDiffs;
	private IUIEventsListener eventsListener;

	public MainWindow(DirTrees dirTrees, DirTreesDifferences treeDiffs, IUIEventsListener eventsListener) {

		this.dirTrees = dirTrees;
		this.treeDiffs = treeDiffs;
		this.eventsListener = eventsListener;

		RadioMenuItem btnMenuShow1 = new RadioMenuItem("Show changes from (1) only");
		RadioMenuItem btnMenuShow2 = new RadioMenuItem("Show changes from (2) only");
		RadioMenuItem btnMenuShowAll = new RadioMenuItem("Show all");
		// FIXME add events

		ToggleGroup toggleGroup = new ToggleGroup();
		btnMenuShow1.setToggleGroup(toggleGroup);
		btnMenuShow2.setToggleGroup(toggleGroup);
		btnMenuShowAll.setToggleGroup(toggleGroup);
		btnMenuShowAll.setSelected(true);

		MenuButton btnMenu = new MenuButton("Filter", null, btnMenuShow1, btnMenuShow2, btnMenuShowAll);

		Button btnGenerate = new Button("Generate scripts");
		// FIXME add events

		ToolBar mainToolBarLeft = new ToolBar(btnGenerate);
		ToolBar mainToolBarRight = new ToolBar(btnMenu);

		mainToolBarLeft.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		mainToolBarLeft.setPrefHeight(38);

		mainToolBarRight.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		mainToolBarRight.setPrefHeight(38);
		HBox.setHgrow(mainToolBarRight, Priority.ALWAYS);

		mainToolBar = new HBox(mainToolBarRight, mainToolBarLeft);
		mainToolBar.setAlignment(Pos.TOP_RIGHT);
		mainToolBar.setPrefHeight(29);

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		tabPane.getTabs().add(new TabInfo(dirTrees));
		tabPane.getTabs().add(new FileModifiedTab(treeDiffs.getModifiedFiles()));
		tabPane.getTabs().add(new Tab("Moved & renamed"));
		tabPane.getTabs().add(new Tab("New files"));
		tabPane.getTabs().add(new Tab("New dirs"));
		tabPane.getTabs().add(new Tab("Duplicates conflicts"));

	}

	public void init(BorderPane pMainPane) {

		this.mainPane = pMainPane;
		mainPane.setPrefSize(1280, 720);

		mainPane.setTop(mainToolBar);

		mainPane.setCenter(tabPane);

	}

	public void enableUI() {
		mainPane.setDisable(false);
	}

	public Node getFormContainer() {
		return mainPane.getCenter();
	}

}
