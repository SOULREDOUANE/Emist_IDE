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

import org.w3c.dom.Text;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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

	private TextArea textArea;

	private ArrayList<TextArea> textAreas = new ArrayList<>();

	// TextArea[] textAreas = new TextArea[5];
	@FXML
	private Hyperlink hyperlink;
	@FXML
	private Label label;

	@FXML
	private TreeView<String> treeView;

	private int numberOfUsedTextArea = 0;

	private String filePath;

	private String folderName;

	private String rootFolderPath;

	private RootFolder rootFolder;

	private TreeItem<String> rootFolderTree = new TreeItem<>("rootFolderTree");

	@FXML
	void addFile(MouseEvent event) {
		String fileName = getNameFormPopUp("file");

		try {

			// getting the path of the current folder;
			String currentFolderName = getSelectedTreeItemName();
			String path = rootFolderPath + "\\" + currentFolderName + "\\" + fileName;
			System.out.println(path);
			File file = new File(path);
			file.createNewFile();

			// display the file in the text area

			TreeItem<String> file1 = new TreeItem<>(folderName);
			// add the file to its parent folder
			findTreeItem(rootFolderTree, currentFolderName).getChildren().add(file1);
			

			// link the file to its textArea
			textAreas.get(numberOfUsedTextArea).setId(path);
			// decrement the number of used textAreas;
			numberOfUsedTextArea++;

		} catch (IOException e) {
			System.out.println(e + "file is not created properly");
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

		try {
			FileWriter writer = new FileWriter(selectedFile);
			writer.write(textArea1.getText());
			writer.close();
		} catch (IOException ex) {
			System.out.println("Error saving text to file: " + ex.getMessage());
		}
	}

	public void openRootFolder(ActionEvent event) {

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");

		File rootFolderFile = chooser.showDialog(stage);
		folderName = rootFolderFile.getName();
		rootFolderPath = rootFolderFile.getAbsolutePath();

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

	public void addFolder(ActionEvent event) {

		String folderName = getNameFormPopUp("file");
		String path = rootFolderPath + "/" + folderName;
		System.out.println(rootFolderPath);

		File newFolder = new File(path);
		newFolder.mkdirs();
		TreeItem<String> item1 = new TreeItem<>(folderName);
		rootFolderTree.getChildren().add(item1);

		Folder folder = new Folder(newFolder.getAbsolutePath(), newFolder.getName());
		rootFolder.addChildFolder(folder);
		// rootFolder.addChildFolder(path);

	}

	private void setVisibleListOfTextAreas(TextArea textArea) {
		for (TextArea textArea1 : textAreas) {

			if (textArea1.getId() != textArea.getId()) {
				textArea1.setVisible(false);
			}

		}
		textArea.setVisible(true);
	}

	private String getSelectedTreeItemName() {

		TreeItem<String> fileItem = treeView.getSelectionModel().getSelectedItem();

		if (fileItem != null) {
			return fileItem.getValue();
			// for (TreeItem folder : rootFolderTree.getChildren()) {

			// if (folder.getValue().equals(fileItem.getParent().getValue())) {
			// System.out.println(folder.getValue());
			// return (String) folder.getValue();
			// }
			// }

		}
		return "";
	}

	public void addFolder(MouseEvent event) {

		if (rootFolderIsOpen) {

			String folderName = getNameFormPopUp("file");
			String path = rootFolderPath + "/" + folderName;
			// System.out.println(rootFolderPath);

			File newFolder = new File(path);
			newFolder.mkdirs();
			TreeItem<String> item1 = new TreeItem<>(folderName);
			rootFolderTree.getChildren().add(item1);

		}

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

		for (TextArea textArea : textAreas) {

			System.out.println(textArea.getId());
		}
		for (int index = 0; index < 5; index++) {
			System.out.println();
		}

		TreeItem<String> rootFolderTree = new TreeItem<>("Files");

		TreeItem<String> item1 = new TreeItem<>("Item1");
		TreeItem<String> item2 = new TreeItem<>("Item2");

		rootFolderTree.getChildren().addAll(item1, item2);
		treeView.setRoot(rootFolderTree);
		textArea1.setVisible(false);

	}

}