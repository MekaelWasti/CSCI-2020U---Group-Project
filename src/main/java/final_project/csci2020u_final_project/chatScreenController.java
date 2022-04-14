package final_project.csci2020u_final_project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import static java.lang.System.out;

/**
 * Controls the chat screen fxml file
*/
public class chatScreenController {

    //Connect to server socket
    public static Socket s;
    String ip = null; //My public IP is gotten in constructor
    String outgoingMessage = "";

    static {
        try {
//            s = new Socket("localhost",63030);                  //For server ran locally
//            s = new Socket("99.232.136.159",63030);             //For testing
            s = new Socket("54.89.207.56",63030);       //For connection to AWS server (over internet)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Declare FXML Elements
    @FXML
    private ImageView sendButton;
    @FXML
    private ImageView newChatButton;
    @FXML
    private VBox newChatField;
    @FXML
    private TextField contactUsernameTextField;
    @FXML
    private TextField contactCode;
    @FXML
    private TextField messageArea;
    @FXML
    private TextArea chatLog;
    @FXML
    public Label myIPLabel;
    @FXML
    public ImageView copiedMessage;
    @FXML
    public Button createChatButton;

    //Contacts Section
    @FXML
    public VBox contactBar;
    @FXML
    public AnchorPane singleContact;
    @FXML
    public HBox singleContact1;
    @FXML
    public Circle contactIcon;
    @FXML
    public Label contactUsernameLabel;
    @FXML
    private Label contactIconPicture;

/**
 * Chat Screen Controller Constructor
 * @throws IOException 
 */
    public chatScreenController() throws IOException {
        //Call method to listen For messages
        //Will run on new thread as listening
        //requires blocking so method will run indefinitely
        listenForMessage();


        //Get My Public IP from Amazon Web Services URL fetch
        URL myPublicIP = null;
        try {
            myPublicIP = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Read in the retrieved value
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(myPublicIP.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //Set our IP variable to the fetched value from AWS URL
            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Contacts Section

    //Control drop-down button for adding new contacts
    /**
     * Control drop-down button to add new contacts 
    */
    public void addNewContact() {

        //Call new contact entry field
        newChatButtonDropDown();
        out.println("Sending Messages To: " + contactCode.getText()); //for development purposes

        //Show current contact node
        singleContact.setVisible(true);
        singleContact.setDisable(false);

        //Contact node is dynamic so icon shows initial of user and username is shown in node as well
        contactIconPicture.setText(String.valueOf(contactUsernameTextField.getText().charAt(0)));
        contactUsernameLabel.setText(contactUsernameTextField.getText());
    }

    /** 
     * Change opacity of new chat button when hovered over
    */
    public void newChatButtonHovered() {
        newChatButton.setOpacity(0.5);
    }
    /** 
     * Reset opacity of new chat button when not hovered over
    */
    public void newChatButtonHoveredExited() {
        newChatButton.setOpacity(1);
    }

    /** 
     * Toggle new chat drop-down
    */
    public void newChatButtonDropDown() {

        //Change state of new Chat field to opposite
        //of what it currently is on each click

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


    //Send Button Methods

    /** 
     * Change opacity of send button when hovered over
    */
    public void sendButtonHovered() {
        sendButton.setOpacity(0.5);
    }
    /** 
     * Reset opacity of send button when hovered over
    */
    public void sendButtonHoveredExited() {
        sendButton.setOpacity(1);
    }

    //SetIPLabel Methods

    /** 
     * Copy IP to system clipboard
     *  to share with contacts
    */
    public void copyIPLabel() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        //Need StringBuilder for mutable string
        StringBuilder myIp = new StringBuilder("");
        String IP = myIPLabel.getText();

        //Copy ONLY ip segment of the label
        for (int i = 9; i < IP.length(); i++) {
            myIp.append(IP.charAt(i));
        }

        //Copy to system clipboard
        content.putString(myIp.toString());
        clipboard.setContent(content);

        //Call Copied Message Method
        setCopiedMessage();
    }

    /** 
     * Set copied message (with animation)
    */
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

            ft.setOnFinished(e -> {
                copiedMessage.setDisable(true);
                copiedMessage.setVisible(false);
                copiedMessage.setOpacity(1);
                ft.stop();
            });
        }
    }




    /** 
     * Send message upon entry of enter key
     * @param event Key event that listens for Enter key
     * @throws IOException
    */
    public void sendKeyClicked(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            outgoingMessage = messageArea.getText();
            out.println("SEND: " + outgoingMessage);
            chatLog.appendText("Me: " + outgoingMessage + "\n\n");
            sendMessage();
        }
    }

    /** 
     * Send message upon send button click (UI)
     * @throws IOException
    */
    public void sendButtonClicked() throws IOException {
            outgoingMessage = messageArea.getText();
            out.println("SEND: " + outgoingMessage);
            chatLog.appendText("Me: " + outgoingMessage + "\n\n");
            sendMessage();
    }


    /** 
     * Send message over socket to the server
     *  @throws IOException
    */
    public void sendMessage() throws IOException {
        //Send Message
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

                //Send Contact Code to server
                bw.write(contactCode.getText());
                bw.newLine();

                //Send Username to server
                bw.write(homeScreenController.thisUSERNAME);
                bw.newLine();

                //Send Message to server
                bw.write(messageArea.getText());
                messageArea.clear();
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Error Sending Message");
            }
    }

    /** 
     * Listen for incoming messages 
     * sent via buffers by server. 
    */
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
                                chatLog.appendText(incomingMessage + "\n\n"); //Show received message in chat log
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            }
                    }
        }
        }).start();
    }
}