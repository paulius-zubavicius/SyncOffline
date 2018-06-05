package com.owr.so.merge.edit.gui;

import com.owr.so.merge.IMergeHandler;
import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.merge.edit.IDiffsEditor;
import com.owr.so.merge.edit.gui.preloader.SplashScreen;
import com.owr.so.merge.log.IUIEventsListener;
import com.owr.so.merge.log.UIDefaultEventsListener;
import com.owr.so.model.DirTreesBundle;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DiffsEditorGui extends Application implements IDiffsEditor {

	private static IMergeHandler mergeHandler;

	@Override
	public void runUI(IMergeHandler mergeHandler) {
		DiffsEditorGui.mergeHandler = mergeHandler;
		LauncherImpl.launchApplication(DiffsEditorGui.class, SplashScreen.class, new String[] {});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		IUIEventsListener eventsListener = new UIDefaultEventsListener();
		DirTreesBundle dirTreesBundle = mergeHandler.loadTrees(eventsListener);
		TreesDiffCollections treeDiffs = mergeHandler.compareTrees(dirTreesBundle, eventsListener);

		MainWindow mainWin = new MainWindow(dirTreesBundle, treeDiffs, eventsListener);

		// Creates main window pane
		BorderPane mainWinPane = new BorderPane();

		// ... pass it to scene
		mainWin.init(mainWinPane);
		Scene scene = new Scene(mainWinPane);

		// HD 720
		primaryStage.setMinWidth(1280);
		primaryStage.setMinHeight(720);
		primaryStage.setTitle("SyncOffline");
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(false);
		primaryStage.getIcons().add(new Image("icon.png"));
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				System.exit(0);
			}
		});
	}

}
