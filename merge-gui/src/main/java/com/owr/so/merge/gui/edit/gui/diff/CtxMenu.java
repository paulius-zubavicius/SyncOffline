package com.owr.so.merge.gui.edit.gui.diff;

import com.owr.so.diff.model.DiffAction;
import com.owr.so.diff.model.UserResolution;
import com.owr.so.merge.gui.edit.gui.IconsResource;
import com.owr.so.merge.gui.edit.gui.ReloadableTab;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class CtxMenu<T extends UserResolution> extends ContextMenu {

	private IconsResource icons = new IconsResource();

	public CtxMenu(TableView<T> table, ReloadableTab callBack) {

		// DiffAction
		MenuItem upd = new MenuItem("Update", icons.getImgView(DiffAction.UPDATE.toString()));
		upd.setOnAction((ActionEvent event) -> {
			changeTo(DiffAction.UPDATE, table.getSelectionModel().getSelectedItems(), callBack);
		});

		MenuItem ign = new MenuItem("Ignore", icons.getImgView(DiffAction.IGNORE.toString()));
		ign.setOnAction((ActionEvent event) -> {
			changeTo(DiffAction.IGNORE, table.getSelectionModel().getSelectedItems(), callBack);
		});

		MenuItem rlb = new MenuItem("Revert", icons.getImgView(DiffAction.REVERT.toString()));
		rlb.setOnAction((ActionEvent event) -> {
			changeTo(DiffAction.REVERT, table.getSelectionModel().getSelectedItems(), callBack);
		});

		getItems().add(upd);
		getItems().add(ign);
		getItems().add(rlb);
	}

	private void changeTo(DiffAction action, ObservableList<T> selectedItems, ReloadableTab callBack) {
		for (T item : selectedItems) {
			item.setAction(action);
		}

		callBack.reload();
	}

}
