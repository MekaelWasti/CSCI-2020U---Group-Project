package final_project.csci2020u_final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class chatScreenController {

    String outgoingMessage = "";

    @FXML
    private ImageView sendButton;
    @FXML
    private TextField messageArea;

    //Send Button Methods
    public void buttonHovered() {
        sendButton.setOpacity(0.5);
    }

    public void buttonHoveredExited() {
        sendButton.setOpacity(1);
    }

    public void sendButtonClicked(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            outgoingMessage = messageArea.getText();
            System.out.println("SEND: " + outgoingMessage);
            messageArea.clear();
        }
    }

    public void sendKeyClicked() {
            outgoingMessage = messageArea.getText();
            System.out.println("SEND: " + outgoingMessage);
            messageArea.clear();
    }

//    public void homeScreen_to_chatScreen() {
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("homeScreen.fxml"));
//    }
}