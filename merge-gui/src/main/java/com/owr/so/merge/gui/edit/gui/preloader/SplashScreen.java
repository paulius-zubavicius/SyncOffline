package com.owr.so.merge.gui.edit.gui.preloader;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {

	private Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.initStyle(StageStyle.UNDECORATED);
		this.stage.setScene(createScene());
		this.stage.setAlwaysOnTop(true);
		this.stage.getIcons().add(new Image("icon.png"));
		this.stage.show();
	}

	public Scene createScene() {
		Pane root = new Pane();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("init1.png"));
		ImageView imgView = new ImageView(image);

		root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		Text title = new Text("SyncOffline");
		title.setFont(new Font(26));
		title.setFill(Color.BLACK);

		title.setLayoutX(100);
		title.setLayoutY(50);

		Text version = new Text("comparing trees ...");
		version.setFont(new Font(10));
		version.setFill(Color.BLACK);

		version.setLayoutX(100);
		version.setLayoutY(70);

		root.getChildren().addAll(imgView, title, version);

		Scene scene = new Scene(root, image.getWidth(), image.getHeight());

		return scene;
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
		if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {

			// Run in thread cause need to release main form creation process
			new Thread(() -> {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Platform.runLater(() -> stage.hide());

			}).start();

		}
	}

}