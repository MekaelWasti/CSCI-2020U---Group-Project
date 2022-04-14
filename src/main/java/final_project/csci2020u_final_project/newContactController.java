package final_project.csci2020u_final_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;
/** 
 * Controller class for new contact fxml file
 * @implemtns initializable
*/
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

    /** 
     * set ID username
    */
    public newContactController(String username) {
//        singleContact.setId(singleContact + ":" + username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
