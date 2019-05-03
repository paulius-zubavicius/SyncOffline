package com.owr.so.merge.gui.edit.gui;

import com.owr.so.merge.gui.DirTrees;
import com.owr.so.merge.gui.IMergeHandler;
import com.owr.so.merge.gui.edit.gui.preloader.SplashScreen;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.merge.gui.edit.IDiffsEditor;
import com.owr.so.merge.gui.log.IUIEventsListener;
import com.owr.so.merge.gui.log.UIDefaultEventsListener;
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
		DirTrees dirTrees = mergeHandler.loadTrees(eventsListener);
		DirTreesDiffResult treeDiffs = mergeHandler.compareTrees(dirTrees, eventsListener);

		MainWindow mainWin = new MainWindow(dirTrees, treeDiffs, eventsListener);

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
