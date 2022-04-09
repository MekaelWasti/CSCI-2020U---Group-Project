package final_project.csci2020u_final_project;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class chatScreenController {

    String outgoingMessage = "";


    //Connect to socket
    Socket s = new Socket("localhost",4999);
    BufferedWriter br = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

    @FXML
    private ImageView sendButton;
    @FXML
    private TextField messageArea;
    @FXML
    private TextArea chatLog;


    public chatScreenController() throws IOException {
    }

    //Send Button Methods
    public void buttonHovered() {
        sendButton.setOpacity(0.5);
    }

    public void buttonHoveredExited() {
        sendButton.setOpacity(1);
    }

    public void sendKeyClicked(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            outgoingMessage = messageArea.getText();
            System.out.println("SEND: " + outgoingMessage);
            sendMessage();
        }
    }

    public void sendButtonClicked() throws IOException {
            outgoingMessage = messageArea.getText();
            System.out.println("SEND: " + outgoingMessage);
            sendMessage();
    }

    public void sendMessage() throws IOException {
        //Send Message
            try {
                br.write(messageArea.getText());
                chatLog.appendText(messageArea.getText() + "\n");
                messageArea.clear();
                br.newLine();
                br.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error Sending Message");
            }
    }

//    public void homeScreen_to_chatScreen() {
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("homeScreen.fxml"));
//    }
}