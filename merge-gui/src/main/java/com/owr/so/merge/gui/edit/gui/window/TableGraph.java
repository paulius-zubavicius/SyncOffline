package com.owr.so.merge.gui.edit.gui.window;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableGraph extends Application {

	private final Image icoErr = new Image(getClass().getClassLoader().getResourceAsStream("icons/error.png"));

	private final TableView<Person> table = new TableView<>();
	private final ObservableList<Person> data = FXCollections.observableArrayList(
			new Person("Jacob", "Smith", "jacob.smith@example.com"),
			new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
			new Person("Ethan", "Williams", "ethan.williams@example.com"),
			new Person("Emma", "Jones", "emma.jones@example.com"),
			new Person("Michael", "Brown", "michael.brown@example.com"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(450);
		stage.setHeight(500);

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		MenuItem mi1 = new MenuItem("Menu item 1");
		mi1.setOnAction((ActionEvent event) -> {
		    //System.out.println("Menu item 1");
			ObservableList<Person> selected = table.getSelectionModel().getSelectedItems();
		    System.out.println("Selected item: " + selected.size());
		});

		ContextMenu menu = new ContextMenu();
		menu.getItems().add(mi1);
		table.setContextMenu(menu);
		
	

		TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<Person, String> emailCol = new TableColumn<>("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		// emailCol.setCellFactory(p -> new AttachmentListCell());
		emailCol.setCellFactory(column -> {
			return new TableCell<Person, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.

						setTextFill(Color.CHOCOLATE);
						setGraphic(new ImageView(icoErr));
						//setStyle("-fx-background-color: yellow");

						// setTextFill(Color.BLACK);
						// setStyle("");

					}
				}
			};
		});

		table.setItems(data);
		// table.setRowFactory(p -> new AttachmentListCell());

		table.getColumns().add(firstNameCol);
		table.getColumns().add(lastNameCol);
		table.getColumns().add(emailCol);
		
		//table.<Person>getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

	public static class Person {

		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;

		private Person(String fName, String lName, String email) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}
	}
}
