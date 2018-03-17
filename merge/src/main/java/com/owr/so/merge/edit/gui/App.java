package com.owr.so.merge.edit.gui;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.owr.so.merge.edit.gui.window.MainWindow;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

	private AnnotationConfigApplicationContext ctx = null;

	@Override
	public void init() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.getEnvironment().setActiveProfiles(Profiles.LOCAL);
		ctx.register(AppCtxConfig.class);
		ctx.refresh();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		/**
		 * Check the version
		 */
		

		MainWindow mainWin = ctx.getBean(MainWindow.class);

		// Creates main window pane
		BorderPane mainWinPane = new BorderPane();

		// ... pass it to scene
		mainWin.init(mainWinPane);
		Scene scene = new Scene(mainWinPane);
		// scene.

		// HD 720
		primaryStage.setMinWidth(1280);
		primaryStage.setMinHeight(720);
		primaryStage.setTitle("SyncOffline - Merge repositories");
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(false);
		// primaryStage.getIcons().add(new
		// Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
		primaryStage.getIcons().add(new Image("icon.png"));
		primaryStage.show();

		// Default first form
		//Nav nav = ctx.getBean(Nav.class);
		//nav.navClear(LoginForm.class);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				System.exit(0);
			}
		});

	}

	@Override
	public void stop() {
		ctx.close();
	}

}
