package com.owr.so.merge.gui.edit.gui.diff.modified;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.owr.so.diff.model.DiffAction;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.merge.gui.edit.gui.IconsResource;
import com.owr.so.merge.gui.edit.gui.ReloadableTab;
import com.owr.so.merge.gui.edit.gui.diff.CtxMenu;
import com.sun.javafx.binding.ObjectConstant;
import com.sun.javafx.binding.StringConstant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FileModifiedTab extends Tab implements ReloadableTab {

	private TableView<FileModifiedDiff> table = new TableView<>();

	private IconsResource icons = new IconsResource();

	public FileModifiedTab(List<FileModifiedDiff> modifiedFiles) {
		super("Modified");

		table.setEditable(true);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setContextMenu(new CtxMenu<>(table, this));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		ObservableList<FileModifiedDiff> data = FXCollections.observableArrayList(modifiedFiles);

		DateTimeFormatter dd = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		TableColumn<FileModifiedDiff, String> colFilePath = new TableColumn<>("File path");
		colFilePath.setMinWidth(600);
		colFilePath.setPrefWidth(1200);
		colFilePath.setResizable(true);
		colFilePath.setCellValueFactory((param) -> StringConstant.valueOf(param.getValue().getFile1().getPath()));

		TableColumn<FileModifiedDiff, FileModifiedDiff> colTime1 = new TableColumn<>("Modified Date&Time 1");
		colTime1.setMinWidth(200);
		colTime1.setMaxWidth(200);
		colTime1.setResizable(false);
		colTime1.setCellValueFactory((p) -> ObjectConstant.<FileModifiedDiff>valueOf(p.getValue()));
		colTime1.setCellFactory((column) -> new DateTimeTableCell(true, dd));

		TableColumn<FileModifiedDiff, FileModifiedDiff> colTime2 = new TableColumn<>("Modified Date&Time 2");
		colTime2.setMinWidth(200);
		colTime2.setMaxWidth(200);
		colTime2.setResizable(false);
		colTime2.setCellValueFactory((p) -> ObjectConstant.<FileModifiedDiff>valueOf(p.getValue()));
		colTime2.setCellFactory((column) -> new DateTimeTableCell(false, dd));

		TableColumn<FileModifiedDiff, FileModifiedDiff> colAction = new TableColumn<>("Action");
		colAction.setMinWidth(150);
		colAction.setMaxWidth(150);
		colAction.setResizable(false);
		colAction.setCellValueFactory((p) -> ObjectConstant.<FileModifiedDiff>valueOf(p.getValue()));

		colAction.setCellFactory(column -> {
			return new TableCell<FileModifiedDiff, FileModifiedDiff>() {
				@Override
				protected void updateItem(FileModifiedDiff item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {

						boolean firstNewer = item.getFile1().getModified() > item.getFile2().getModified();

						setGraphic(icons.getImgView(item.getAction().toString()));
						setTextFill(getColor(item.getAction()));
						setText(constructText(firstNewer, item.getAction()));

						// setStyle("-fx-background-color: yellow");

					}
				}

				private String constructText(boolean firstNewer, DiffAction action) {
					String direction = "??";

					switch (action) {
					case UPDATE:
						direction = (firstNewer ? "=>" : "<=");
						break;
					case REVERT:
						direction = (firstNewer ? "<=" : "=>");
						break;
					case IGNORE:
						direction = "==";
						break;
					}

					return direction + " " + action.toString() + " " + direction;
				}

				private Paint getColor(DiffAction action) {
					switch (action) {
					case UPDATE:
						return Color.BLUE;

					case REVERT:
						return Color.RED;

					case IGNORE:
						return Color.DARKGREY;

					}

					return null;
				}
			};
		});

		table.setItems(data);

		table.getColumns().add(colFilePath);
		table.getColumns().add(colTime1);
		table.getColumns().add(colAction);
		table.getColumns().add(colTime2);

		AnchorPane anchorPane = new AnchorPane();
		setContent(anchorPane);

		anchorPane.getChildren().add(table);

		AnchorPane.setBottomAnchor(table, 10d);
		AnchorPane.setLeftAnchor(table, 10d);
		AnchorPane.setRightAnchor(table, 10d);
		AnchorPane.setTopAnchor(table, 10d);
	}

	@Override
	public void reload() {
		table.refresh();
	}

}
