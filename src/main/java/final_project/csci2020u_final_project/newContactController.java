package final_project.csci2020u_final_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class newContactController implements Initializable {

    //Contacts Section
    @FXML
    public HBox singleContact;
    @FXML
    public Circle contactIcon;
    @FXML
    public Label contactUsernameLabel;
    @FXML
    private Label contactIconPicture;

    public newContactController(String username) {
//        singleContact.setId(singleContact + ":" + username);
        System.out.println(singleContact.getId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        singleContact.setId("20");
    }
}
