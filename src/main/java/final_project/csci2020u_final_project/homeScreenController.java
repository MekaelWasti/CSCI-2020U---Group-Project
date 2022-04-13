package final_project.csci2020u_final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static java.lang.System.out;

public class homeScreenController {

    @FXML
    private ImageView logInButton;

    @FXML
    private ImageView logo;
    @FXML
    private TextField inputUsername;

    public static String thisUSERNAME;


    //Log In Button Methods
    public void buttonHovered() {
        logInButton.setOpacity(0.5);
    }

    public void buttonHoveredExited() {
        logInButton.setOpacity(1);
    }

    public void homeScreen_to_chatScreen() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("chatScreen.fxml"));
//        logo.setTranslateY(-175);
//        logo.setTranslateY(-175);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatScreen.fxml"));
        Parent root = loader.load();
        chatScreenController labelController = loader.getController();
        labelController.myIPLabel.setText("My Code: " + inputUsername.getText() + ":" + chatScreenController.s.getLocalPort());
        thisUSERNAME = inputUsername.getText() + ":" + chatScreenController.s.getLocalPort();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(chatScreenController.s.getOutputStream()));

        try {
            //Send Username to server
            bw.write(homeScreenController.thisUSERNAME);
            bw.newLine();
            bw.flush();
        } catch (IOException ignored) {
        }


        logInButton.getScene().setRoot(root);

//        chatScreenController.myIPLabel.setText(inputUsername.getText());
//        Stage window = (Stage) logInButton.getScene().getWindow();
//        window.setScene(new Scene(root, 1280,720));
    }
}