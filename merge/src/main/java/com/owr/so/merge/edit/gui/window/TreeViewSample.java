package com.owr.so.merge.edit.gui.window;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TreeViewSample extends Application {

	private final Node rootIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));

	private final Image icoErr = new Image(getClass().getClassLoader().getResourceAsStream("icons/error.png"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		TreeItem<String> rootItem = new TreeItem<>("Inbox", rootIcon);
		rootItem.setExpanded(true);

		//Node n = new Label("sdfsdf");
		//rootItem.setGraphic(n);

		for (int i = 1; i < 6; i++) {
			TreeItem<String> item = new TreeItem<>("Message" + i, new ImageView(icoErr));
			rootItem.getChildren().add(item);
		}
		TreeView<String> tree = new TreeView<>(rootItem);
		StackPane root = new StackPane();
		root.getChildren().add(tree);

		primaryStage.setTitle("Tree View Sample");
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}