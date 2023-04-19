package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class JavaFile extends File{

    public JavaFile(File parent, String child) {
        super(parent, child);
        
    }
    public void openFile(MouseEvent event, TextArea textArea) {
    	
    	Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
    	selectedFile = fileChooser.showOpenDialog(stage);
    	filePath= selectedFile.getAbsolutePath();

    	textArea.setText("");
  

    	TreeItem item3= new TreeItem(selectedFile.getName());
    	rootFolder.getChildren().add(item3);
    	
    	try {

    		selectedFile= new File(filePath);
    		Scanner myReader = new Scanner(selectedFile);
    		
    		while(myReader.hasNextLine()) {
    			
    			textArea.appendText(myReader.nextLine());
    			textArea.appendText("\n");
    		}
    		
    		myReader.close();
    		
    	} catch(FileNotFoundException e) {
    		
    			System.out.println("An error occurred while reading from a file");
    			e.printStackTrace();
    	}
    }




    
}
