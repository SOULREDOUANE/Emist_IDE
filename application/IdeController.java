package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IdeController implements Initializable {

	private Stage stage;

	private boolean rootFolderIsOpen = false;

	private FileChooser fileChooser = new FileChooser();

	private File selectedFile;

	@FXML
	private TextArea textArea1;
	@FXML
	private TextArea textArea2;
	@FXML
	private TextArea textArea3;
	@FXML
	private TextArea textArea4;
	@FXML
	private TextArea textArea5;
	@FXML
	private Hyperlink hyperlink;
	@FXML
	private Label label;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private ScrollPane scrollPane;

	private TextArea textArea;

	private ArrayList<TextArea> textAreas = new ArrayList<>();

	// TextArea[] textAreas = new TextArea[5];

	private int numberOfUsedTextArea = 0;

	private String filePath;

	private String folderName;

	private String rootFolderPath;
	private String rootFolderName;

	private RootFolder rootFolder;
	
	private VBox vBox;

	private TreeItem<String> rootFolderTree = new TreeItem<>("rootFolderTree");

	@FXML
	void addFile(MouseEvent event) {

		if (rootFolderIsOpen) {

			String fileName = getNameFormPopUp("file");
			// if(!fileName.equals("")) {
			// getting the path of the current folder;
			String currentFolderName = (String) getSelectedTreeItem().getValue();

			if (currentFolderName.equals(rootFolderName)) {
				String path = rootFolderPath + "\\" + fileName;
				System.out.println(path);

				try {

					File file = new File(path);
					file.createNewFile();

					// display the file in the text area

					TreeItem<String> newFile = new TreeItem<>(fileName);
					// add the file to its parent folder
					rootFolderTree.getChildren().add(newFile);

					// link the file to its textArea
					textAreas.get(numberOfUsedTextArea).setVisible(true);
					textAreas.get(numberOfUsedTextArea).setId(path);
					// decrement the number of used textAreas;
					numberOfUsedTextArea++;

				} catch (IOException e) {
					System.out.println("\n" + e + "\nthe file is not created properly");
				}
			} else {

				String path = rootFolderPath + "\\" + currentFolderName + "\\" + fileName;
				System.out.println(path);

				try {

					File file = new File(path);
					file.createNewFile();

					// display the file in the text area

					TreeItem<String> newFile = new TreeItem<>(fileName);
					// add the file to its parent folder
					findTreeItem(rootFolderTree, currentFolderName).getChildren().add(newFile);

					// link the file to its textArea
					textAreas.get(numberOfUsedTextArea).setVisible(true);
					textAreas.get(numberOfUsedTextArea).setId(path);
					// decrement the number of used textAreas;
					numberOfUsedTextArea++;

				} catch (IOException e) {
					System.out.println("\n" + e + "\nthe file is not created properly");
				}
			}
			// }
		}

	}

	public void openFile(MouseEvent event) {

		if (rootFolderIsOpen) {

			String fileName = getNameFormPopUp("file");
			String path = rootFolderPath + "//" + folderName;

			File newFile = new File(path);
			newFile.mkdirs();
			TreeItem<String> item1 = new TreeItem<>(fileName);
			rootFolderTree.getChildren().add(item1);

			TreeItem<String> item3 = new TreeItem<>(selectedFile.getName());
			rootFolderTree.getChildren().add(item3);

			try {

				selectedFile = new File(filePath);
				Scanner myReader = new Scanner(selectedFile);

				textArea = textAreas.get(numberOfUsedTextArea);
				textArea.setText("");
				while (myReader.hasNextLine()) {

					textArea.appendText(myReader.nextLine());
					textArea.appendText("\n");
				}

				myReader.close();
				// textArea.setVisible(true);
				setVisibleListOfTextAreas(textArea);
				textArea.setId(filePath);

			} catch (FileNotFoundException e) {

				System.out.println("An error occurred while reading from a file");
				e.printStackTrace();
			}
		} else {

		}

	}

	public void openFile(ActionEvent event) {
		if (rootFolderIsOpen) {

			stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
			selectedFile = fileChooser.showOpenDialog(stage);
			filePath = selectedFile.getAbsolutePath();

			TreeItem<String> item3 = new TreeItem<>(selectedFile.getName());
			rootFolderTree.getChildren().add(item3);

			try {

				selectedFile = new File(filePath);
				Scanner myReader = new Scanner(selectedFile);

				textArea1.setText("");
				while (myReader.hasNextLine()) {

					textArea1.appendText(myReader.nextLine());
					textArea1.appendText("\n");
				}

				myReader.close();
				textArea1.setVisible(true);

			} catch (FileNotFoundException e) {

				System.out.println("An error occurred while reading from a file");
				e.printStackTrace();
			}
		} else {

		}

	}

	public void saveFile() {
		// String currentFolderName = getSelectedTreeItemName();

		TextArea textArea = getVisibilTextArea();
		System.out.println(textArea.getId());
		try {
			File myFile = new File(textArea.getId());
			FileWriter myWriter = new FileWriter(myFile);
			myWriter.write(textArea.getText());
			myWriter.close();

		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public void openRootFolder(ActionEvent event) {

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");

		File rootFolderFile = chooser.showDialog(stage);
		folderName = rootFolderFile.getName();
		rootFolderPath = rootFolderFile.getAbsolutePath();
		rootFolderName = rootFolderFile.getName();

		// set the root folder as root in treeview
		rootFolderTree = new TreeItem<>(folderName);
		treeView.setRoot(rootFolderTree);

		rootFolder = new RootFolder(rootFolderPath, folderName);
		hyperlink.setVisible(false);
		label.setVisible(false);
		rootFolderIsOpen = true;

	}

	public void openFolder(ActionEvent event) {

		if (rootFolderIsOpen) {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("JavaFX Projects");

			File folder = chooser.showDialog(stage);
			Folder folder1 = new Folder(folder.getAbsolutePath(), folder.getName());
			rootFolder.addChildFolder(folder1);
		}

	}

	private String getNameFormPopUp(String folderOrFile) {
		TextInputDialog dialog = new TextInputDialog("Enter your name");
		dialog.setTitle(folderOrFile + "'s Name Input Dialog");
		dialog.setHeaderText("Please enter your " + folderOrFile + " name:");

		// Show the dialog and wait for the user to enter text
		Optional<String> result = dialog.showAndWait();

		// Check if the user clicked OK and entered text
		if (result.isPresent() && !result.get().isEmpty()) {
			return result.get();
		} else {
			System.out.println("No name entered.");
			return "new" + folderOrFile;
		}

	}

	public void addFolder(MouseEvent event) {

		if (rootFolderIsOpen) {

			String folderName = getNameFormPopUp("folder");
			if (!folderName.equals("")) {

				String currentFolderName = (String) getSelectedTreeItem().getValue();
				if (currentFolderName.equals(rootFolderName)) {
					String path = rootFolderPath + "\\" + folderName;
					System.out.println(path);
					try {
						File newFolder = new File(path);
						newFolder.mkdirs();
						// display the file in the text area
						TreeItem<String> folder1 = new TreeItem<>(folderName);
						rootFolderTree.getChildren().add(folder1);

					} catch (Exception e) {
						System.out.println("while adding a folder there " + e);
					}

				} else {

					String path = rootFolderPath + "\\" + currentFolderName + "\\" + folderName;
					System.out.println(path);

					try {
						File newFolder = new File(path);
						newFolder.mkdirs();
						// display the file in the text area

						TreeItem<String> folder1 = new TreeItem<>(folderName);
						// add the file to its parent folder
						findTreeItem(rootFolderTree, currentFolderName).getChildren().add(folder1);

						Folder folder = new Folder(newFolder.getAbsolutePath(), newFolder.getName());
						rootFolder.addChildFolder(folder);

					} catch (Exception e) {
						System.out.println("while adding a folder there " + e);
					}
				}

			}

		}

	}

	public void DisplayTextAreaOfSelectedFile() {
	
		TreeItem selectedTreeItem = getSelectedTreeItem();
		if (selectedTreeItem!=null) {
			// getting the path of the file that is associated with treeview
			String path = (String) selectedTreeItem.getValue();
			selectedTreeItem = selectedTreeItem.getParent();
			while (selectedTreeItem != null && !selectedTreeItem.getValue().equals(rootFolderTree.getValue())) {
				path = selectedTreeItem.getValue() + "\\" + path;
				selectedTreeItem = selectedTreeItem.getParent();
			}
			path = rootFolderPath+ "\\" + path ;
	
			// System.out.println(path);
			for (TextArea textArea : textAreas) {
				if (textArea.getId().equals(path)) {
					setVisibleListOfTextAreas(textArea);
				}
			}
		}
	}

	public void showErrors() {
		Label newlabel=new Label("hi there what do you think about some stuff");
		vBox.getChildren().add(newlabel);
		
		
	}
	private void setVisibleListOfTextAreas(TextArea textArea) {
		for (TextArea textArea1 : textAreas) {

			if (textArea1 != null) {
				if (textArea1.getId() != textArea.getId()) {

					textArea1.setVisible(false);
				}
			} else {
				return;
			}

		}
		textArea.setVisible(true);
	}

	private TreeItem getSelectedTreeItem() {

		TreeItem<String> fileItem = treeView.getSelectionModel().getSelectedItem();

		if (fileItem != null) {
			return fileItem;

		}
		return null;
	}

	// public void addFolder(ActionEvent event) {

	// if (rootFolderIsOpen) {

	// String folderName = getNameFormPopUp("file");
	// String path = rootFolderPath + "/" + folderName;
	// System.out.println(rootFolderPath);

	// File newFolder = new File(path);
	// newFolder.mkdirs();
	// TreeItem<String> item1 = new TreeItem<>(folderName);
	// rootFolderTree.getChildren().add(item1);

	// }

	// }
	private TextArea getVisibilTextArea() {

		for (TextArea textArea : textAreas) {

			// boolean isTextAreaVisible = (textArea.offsetHeight > 0 &&
			// textArea.offsetWidth > 0);
			if (textArea.isVisible()) {

				return textArea;
			}

		}
		return null;
	}

	private TreeItem<String> findTreeItem(TreeItem<String> root, String value) {
		if (root.getValue().equals(value)) {
			return root;
		}
		for (TreeItem<String> child : root.getChildren()) {
			TreeItem<String> result = findTreeItem(child, value);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// stage = (Stage) label.getScene().getWindow();

		// add textarea items to textareas Arraylist
		textAreas.add(textArea1);
		textAreas.add(textArea2);
		textAreas.add(textArea3);
		textAreas.add(textArea4);
		textAreas.add(textArea5);
		// textArea2.setVisible(true);

		// Create a VBox to hold the label
		vBox = new VBox();

		//  set the VBox as a content of ScrollPane
		scrollPane.setContent(vBox);

		// _____________
		// Create a child TreeItem with a node as its value
        // VBox vbox = new VBox();
        // Button but=new Button("red");
        // vbox.getChildren().add(but);
        // TreeItem<String> childItem = new TreeItem<>("Child", vbox);
		// ______________

		TreeItem<String> rootFolderTree = new TreeItem<>("Files");

		TreeItem<String> item1 = new TreeItem<>("Item1");

		TreeItem<String> item2 = new TreeItem<>("Item2");

		rootFolderTree.getChildren().addAll(item1, item2);
		treeView.setRoot(rootFolderTree);
		textArea1.setVisible(false);

	

	}

}