package final_project.csci2020u_final_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static java.lang.System.out;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("PRISM");
        stage.getIcons().add(new Image("file:src/main/resources/final_project/csci2020u_final_project/Application Icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //Terminate program when closed via windows methods (eg. windows program bar close button)
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}