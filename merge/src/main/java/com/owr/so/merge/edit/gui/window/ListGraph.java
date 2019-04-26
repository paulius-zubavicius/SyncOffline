package com.owr.so.merge.edit.gui.window;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListGraph extends Application {

	private final Image icoErr = new Image(getClass().getClassLoader().getResourceAsStream("icons/error.png"));

	ListView<Obj> list = new ListView<>();
	ObservableList<Obj> data = FXCollections.observableArrayList(new Obj("ewrw"), new Obj("sdfds"), new Obj("xcvxcv"));

	@Override
	public void start(Stage stage) {
		VBox box = new VBox();
		Scene scene = new Scene(box, 200, 200);
		stage.setScene(scene);
		stage.setTitle("ListViewSample");
		box.getChildren().addAll(list);
		VBox.setVgrow(list, Priority.ALWAYS);

		list.setItems(data);
		list.setCellFactory(list -> new AttachmentListCell());
		
//		list.setCellFactory(new Callback<ListView<Obj>, ListCell<Obj>>() {
//            @Override
//            public ListCell<Obj> call(ListView<Obj> list) {
//                return new AttachmentListCell();
//            }
//        });

		stage.show();
	}

	private class AttachmentListCell extends ListCell<Obj> {
		@Override
		public void updateItem(Obj item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				setGraphic(new ImageView(icoErr));
				setText(item.getText());
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
