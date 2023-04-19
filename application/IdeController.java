package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private TextArea textArea;

	@FXML
	private Hyperlink hyperlink;
	@FXML
	private Label label;

	private String filePath;

	private String folderName;

	private String rootFolderPath;

	@FXML
	private TreeView<String> treeView;
	private RootFolder rootFolder;

	private TreeItem<String> rootFolderTree = new TreeItem<>("rootFolderTree");

	@FXML
	void addFile(MouseEvent event) {
		String folderName = getNameFormPopUp("file");
		String path = rootFolderPath + "/" + folderName;
	
		try {
			File file = new File(path);
			boolean created = file.createNewFile();

			TreeItem<String> floder1 = new TreeItem<>(folderName);
			rootFolderTree.getChildren().add(floder1);
	
		} catch (IOException e) {
			System.out.println(e +"file is not created properly");
		}


	}

	@FXML
	// void addFolder(MouseEvent event) {
	// System.out.println("what do you think about this folder");
	// }

	public void openFile(MouseEvent event) {

		if (rootFolderIsOpen) {

			stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
			selectedFile = fileChooser.showOpenDialog(stage);
			filePath = selectedFile.getAbsolutePath();
			// textArea.setVisible(true);
			textArea.setText("");

			TreeItem<String> item3 = new TreeItem<>(selectedFile.getName());
			rootFolderTree.getChildren().add(item3);

			try {

				selectedFile = new File(filePath);
				Scanner myReader = new Scanner(selectedFile);

				while (myReader.hasNextLine()) {

					textArea.appendText(myReader.nextLine());
					textArea.appendText("\n");
				}

				myReader.close();
				textArea.setVisible(true);

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
				
				textArea.setText("");
				while (myReader.hasNextLine()) {

					textArea.appendText(myReader.nextLine());
					textArea.appendText("\n");
				}

				myReader.close();
				textArea.setVisible(true);

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
			writer.write(textArea.getText());
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

			File rootFolderFile = chooser.showDialog(stage);
			folderName = rootFolderFile.getName();
			rootFolderPath = rootFolderFile.getAbsolutePath();
		} 

	}

	public String getNameFormPopUp(String folderOrFile) {
		TextInputDialog dialog = new TextInputDialog("Enter your name");
		dialog.setTitle(folderOrFile+"'s Name Input Dialog");
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

		File folder = new File(path);
		boolean success = folder.mkdirs();
		TreeItem<String> floder1 = new TreeItem<>(folderName);
		rootFolderTree.getChildren().add(floder1);
		rootFolder.addChildFolder(path);

	}

	public void addFolder(MouseEvent event) {

		if (rootFolderIsOpen) {

			String folderName = getNameFormPopUp("file");
			String path = rootFolderPath + "/" + folderName;
			System.out.println(rootFolderPath);
	
			File folder = new File(path);
			boolean success = folder.mkdirs();
			TreeItem<String> floder1 = new TreeItem<>(folderName);
			rootFolderTree.getChildren().add(floder1);
			
		}

	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItem<String> rootFolderTree = new TreeItem<>("Files");

		TreeItem<String> item1 = new TreeItem<>("Item1");
		TreeItem<String> item2 = new TreeItem<>("Item2");

		rootFolderTree.getChildren().addAll(item1, item2);
		treeView.setRoot(rootFolderTree);
		textArea.setVisible(false);

	}

}