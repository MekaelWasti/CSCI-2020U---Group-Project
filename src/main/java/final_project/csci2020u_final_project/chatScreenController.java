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

import java.io.*;
import java.net.Socket;

import static java.lang.System.in;
import static java.lang.System.out;

public class chatScreenController {

    String outgoingMessage = "";


    //Connect to socket
    Socket s = new Socket("99.232.136.159",63030);

//    InputStream in = s.getInputStream();
//    ObjectInputStream oin = new ObjectInputStream(in);


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
            out.println("SEND: " + outgoingMessage);
            sendMessage();
        }
    }

    public void sendButtonClicked() throws IOException {
            outgoingMessage = messageArea.getText();
            out.println("SEND: " + outgoingMessage);
            sendMessage();
    }

    public void sendMessage() throws IOException {
        //Send Message

            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                BufferedReader incoming = new BufferedReader(new InputStreamReader(s.getInputStream()));

                bw.write(messageArea.getText());
//                chatLog.appendText(messageArea.getText() + "\n");
                messageArea.clear();
                bw.newLine();
                bw.flush();

                String incomingMessage;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String incomingMessage;

                        while (s.isConnected()) {
                            try {
                                incomingMessage = incoming.readLine();
                                out.println(incomingMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
                out.println("Error Sending Message");
            }
    }

//    public void homeScreen_to_chatScreen() {
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("homeScreen.fxml"));
//    }
}