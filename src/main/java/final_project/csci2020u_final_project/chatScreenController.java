package final_project.csci2020u_final_project;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.in;
import static java.lang.System.out;

public class chatScreenController implements Initializable {

    //Connect to server socket
    public Socket s = new Socket("99.232.136.159",63030);
    String outgoingMessage = "";
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

    @FXML
    private ImageView sendButton;
    @FXML
    private ImageView newChatButton;
    @FXML
    private VBox newChatField;
    @FXML
    private TextField messageArea;
    @FXML
    private TextArea chatLog;
    @FXML
    public Label myIPLabel;
    @FXML
    public ImageView copiedMessage;



    public chatScreenController() throws IOException {
        listenForMessage();
    }


    //Send Button Methods

    //Change Opacity of Send button on hover event
    public void sendButtonHovered() {
        sendButton.setOpacity(0.5);
    }
    public void sendButtonHoveredExited() {
        sendButton.setOpacity(1);
    }

    //SetIPLabel Methods

    //Copy IP to clipboard to share with contacts
    public void copyIPLabel() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        //Need StringBuilder for mutable string
        StringBuilder myIp = new StringBuilder("");
        String IP = myIPLabel.getText();

        //Copy ONLY ip segment of the label
        for (int i = 7; i < IP.length(); i++) {
            myIp.append(IP.charAt(i));
        }

        //Copy to system clipboard
        content.putString(myIp.toString());
        clipboard.setContent(content);

        //Call Copied Message Method
        setCopiedMessage();
    }

    //Copied Message Methods
    public void setCopiedMessage() {
        if (copiedMessage.isDisabled()) {
            copiedMessage.setVisible(true);
            copiedMessage.setDisable(false);

            //Add Copied Message Animation
            FadeTransition ft = new FadeTransition(Duration.millis(3000), copiedMessage);
            ft.setDelay(Duration.seconds(2));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            return;
        }
        if (!copiedMessage.isDisabled()) {
            copiedMessage.setVisible(false);
            copiedMessage.setDisable(true);
        }




    }

    //New Chat Button Methods

    //Change Opacity of New Chat button on hover event
    public void newChatButtonHovered() {
        newChatButton.setOpacity(0.5);
    }
    public void newChatButtonHoveredExited() {
        newChatButton.setOpacity(1);
    }

    //Toggle new chat drop down
    public void newChatButtonDropDown() {
        if (newChatField.isDisabled()) {
            newChatField.setVisible(true);
            newChatField.setDisable(false);
            return;
        }
        if (!newChatField.isDisabled()) {
            newChatField.setVisible(false);
            newChatField.setDisable(true);
        }
    }

    //Manage sending of message when enter key is pressed on keyboard
    public void sendKeyClicked(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            outgoingMessage = messageArea.getText();
            out.println("SEND: " + outgoingMessage);
            chatLog.appendText("Me: " + outgoingMessage + "\n\n");
            sendMessage();
        }
    }

    //Manage sending of message when send button is clicked on GUI
    public void sendButtonClicked() throws IOException {
            outgoingMessage = messageArea.getText();
            out.println("SEND: " + outgoingMessage);
            chatLog.appendText("Me: " + outgoingMessage + "\n\n");
            sendMessage();
    }


    //Send Message Over Socket to Server
    public void sendMessage() throws IOException {
        //Send Message
            try {
                bw.write(messageArea.getText());
                messageArea.clear();
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Error Sending Message");
            }
    }

    //Listen to incoming messaged sent in buffers by server
    //Due to blocking by readline method, need a new thread to listen for messages
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String incomingMessage;

                    while (s.isConnected()) {
                        try {
                            BufferedReader incoming = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            while((incomingMessage = incoming.readLine()) != null) {
                                chatLog.appendText("Contact: " + incomingMessage + "\n\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            }
                    }
        }
        }).start();
    }

    //Need to override initialize from Initializable interface to initialize myIPLabel so that it
    //is not null upon screen change and socket IP can be appended to the label
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myIPLabel.setText("MY IP: " + s.getRemoteSocketAddress().toString());
    }
}