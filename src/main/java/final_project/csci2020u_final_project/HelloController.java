package final_project.csci2020u_final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView logInButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    //Log In Button Methods
    public void buttonHovered() {
        logInButton.setOpacity(0.5);
    }

    public void buttonHoveredExited() {
        logInButton.setOpacity(1);
    }

    public void homeScreen_to_chatScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("chatScreen.fxml"));

        Stage window = (Stage) logInButton.getScene().getWindow();
        window.setScene(new Scene(root, 1280,720));

    }
}