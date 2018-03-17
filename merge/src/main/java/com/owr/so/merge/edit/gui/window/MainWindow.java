package com.owr.so.merge.edit.gui.window;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


@Component
public class MainWindow {

	private BorderPane mainPane;
	private Pane infoBarPane;
	private AnchorPane infoBarTextWraper;
	private Text infoBarText;
	private VBox errMsgContainer;

	public MainWindow() {

		/*
		 * Text
		 */
		infoBarText = new Text("");
		infoBarText.setLayoutX(15);
		infoBarText.setLayoutY(15);
		infoBarText.setFill(Color.GREY);

		/*
		 * Border pane
		 */
		infoBarPane = new Pane(infoBarText);
		infoBarPane.setMaxHeight(20);
		infoBarPane.setMinHeight(20);
		infoBarPane.setStyle("-fx-border-radius:3; -fx-border-color:lightgrey;");

		/*
		 * Wrapper
		 */
		infoBarTextWraper = new AnchorPane(infoBarPane);
		AnchorPane.setTopAnchor(infoBarPane, 2d);
		AnchorPane.setLeftAnchor(infoBarPane, 2d);
		AnchorPane.setRightAnchor(infoBarPane, 2d);
		AnchorPane.setBottomAnchor(infoBarPane, 2d);

		errMsgContainer = new VBox();
		// errMsgContainer.setMaxWidth(200);

	}

	public void init(BorderPane pMainPane) {

		this.mainPane = pMainPane;
		mainPane.setPrefSize(1280, 720);
		
		//mainPane.setTop(value);
		
		//mainPane.setCenter(value);
		
		mainPane.setBottom(errMsgContainer);



	}

	public void changeAppStatus(String infoBarText) {
		this.infoBarText.setText(infoBarText);
	}

	public void disableUI() {
		changeAppStatus("Waiting for server. Please wait ... ");
		mainPane.setDisable(true);
	}

	public void enableUI() {
		mainPane.setDisable(false);
	}


	public Node getFormContainer() {
		return mainPane.getCenter();
	}

//	public void showMessages(NotOkResult dvr) {
//
//		clearMessages();
//
//		for (MsgItem item : dvr.getItems()) {
//			String message = (item.getElement() == null ? "Error" : item.getElement()) + ": " + item.getMessage();
//			addMessage(item.getSeverity(), message);
//		}
//	}
//
//	public void addMessage(EnumMsgSeverity type, String message) {
//
//		if (errMsgContainer.getChildren().size() == 1) {
//			errMsgContainer.getChildren().add(errMsgContainer.getChildren().size() - 1,
//					new Separator(Orientation.HORIZONTAL));
//		}
//
//		errMsgContainer.getChildren().add(errMsgContainer.getChildren().size() - 1, createMsgNode(type, message));
//		errMsgContainer.getChildren().add(errMsgContainer.getChildren().size() - 1,
//				new Separator(Orientation.HORIZONTAL));
//
//	}
//
//	public void clearMessages() {
//		errMsgContainer.getChildren().clear();
//		errMsgContainer.getChildren().add(infoBarTextWraper);
//	}
//
//	private Node createMsgNode(EnumMsgSeverity type, String message) {
//
//		Label text = new Label(message);
//		text.setWrapText(true);
//		text.setTextFill(Color.RED);
//		// text.setPadding(new Insets(0, 45, 0, 0));
//
//		HBox msgPane = new HBox();
//		// msgPane.setPadding(new Insets(3, 0, 3, 0));
//
//		// msgPane.setMinHeight(50);
//
//		Node icon = createIcon(type);
//		msgPane.getChildren().add(icon);
//		msgPane.getChildren().add(text);
//
//		HBox.setMargin(icon, new Insets(3, 15, 3, 15));
//		HBox.setMargin(text, new Insets(3, 25, 3, 0));
//
//		return msgPane;
//	}
//
//	private Node createIcon(EnumMsgSeverity type) {
//
//		type = (type == null ? EnumMsgSeverity.ERROR : type);
//		String imgPath = "icons/" + type.toString().toLowerCase() + ".png";
//
//		// Image img = new
//		// Image(getClass().getClassLoader().getResourceAsStream(imgPath));
//		ImageView icon = new ImageView(imgPath);
//		icon.setFitHeight(16);
//		icon.setFitWidth(16);
//		icon.setLayoutX(0);
//		icon.setLayoutY(0);
//		icon.setPickOnBounds(true);
//		icon.setPreserveRatio(true);
//
//		return icon;
//	}
}
