package main.controllers;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import main.apivk.APIvk;
import org.apache.http.HttpException;
import org.json.JSONException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * Created by shi on 16.09.16.
 */
public class UserOverviewController{

    @FXML
    FlowPane flowPane;

    @FXML
    private Button showResults;

    private Stage stage;
    private String id1;
    private String id2;
    private HostServicesDelegate hostService;


    @FXML
    private void initialize() { }

    @FXML
    public void handleShow() {
        APIvk apiVk = new APIvk();
        Set<String> mutualFriends;
        try {
                mutualFriends = apiVk.getMutualFriends(id1, id2);
                for (String user : mutualFriends) {
                    ImageView slow = new ImageView("http://cs301804.vk.me/v301804880/5a80/vYtHFEoDKxM.jpg");
                    ImageView avatar = new ImageView(apiVk.getUser(user).getPhotoURL());
                    String userUrl = "http://vk.com/id" + apiVk.getUser(user).getUserId();
                    Button userView = new Button(apiVk.getUser(user).getFirstName()+"\n"
                            + apiVk.getUser(user).getLastName(), avatar);
                    userView.setOnAction( (ae) -> hostService.showDocument(userUrl) );
                    userView.setContentDisplay(ContentDisplay.TOP);
                    flowPane.getChildren().add(userView);
                }

            } catch (IOException | URISyntaxException | HttpException | JSONException e) {
                e.printStackTrace();
            }
    }

    public void setDialogStage(Stage stage, String id1, String id2, HostServicesDelegate hostService) {
        this.stage = stage;
        this.id1 = id1;
        this.id2 = id2;
        this.hostService = hostService;
    }
}
