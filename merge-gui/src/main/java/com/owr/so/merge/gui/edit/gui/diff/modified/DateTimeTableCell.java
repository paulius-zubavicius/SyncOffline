package com.owr.so.merge.gui.edit.gui.diff.modified;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.diff.model.FileEntityWrapper;

import javafx.scene.control.TableCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DateTimeTableCell extends TableCell<FileModifiedDiff, FileModifiedDiff> {

	private boolean firstFile;
	private DateTimeFormatter dtf;

	public DateTimeTableCell(boolean firstFile, DateTimeFormatter dtf) {
		this.firstFile = firstFile;
		this.dtf = dtf;
	}

	@Override
	protected void updateItem(FileModifiedDiff item, boolean empty) {
		super.updateItem(item, empty);
		setStyle("");
		if (item == null || empty) {
			setText(null);

		} else {
			FileEntityWrapper entity = (firstFile ? item.getFile1() : item.getFile2());
			FileEntityWrapper entityOther = (!firstFile ? item.getFile1() : item.getFile2());

			setText(LocalDateTime
					.ofInstant(Instant.ofEpochMilli(entity.getFile().getModified()), ZoneId.systemDefault())
					.format(dtf));

			FontWeight fw = ((entity.getFile().getModified() > entityOther.getFile().getModified()) ? BOLD : NORMAL);
			setFont(Font.font("Verdana", fw, 12));

		}
	}

}
