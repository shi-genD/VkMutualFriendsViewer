package main.controllers;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import main.Main;
import main.apivk.APIvk;
import main.apivk.VkUser;
import org.apache.http.HttpException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class MainOverviewController {

    @FXML
    private TextField firstId;

    @FXML
    private TextField secondId;

    @FXML
    private Button btnGo;

    @FXML
    private FlowPane flowPane;

    private Main mainApp;

    @FXML
    private void initialize() {
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.CENTER);
    }

    @FXML
    private void handleGo() {
        String id1 = firstId.getText();
        String id2 = secondId.getText();
        if (id1.length()!=0 && id2.length()!=0) {
            showResult(id1, id2);
        }
    }

    private void showResult(String id1, String id2){

        APIvk apiVk = new APIvk();
        Set<VkUser> mutualFriends;
        try {
            mutualFriends = apiVk.getMutualFriends(id1, id2);
            for (VkUser user : mutualFriends) {
                ImageView avatar = new ImageView(user.getPhotoURL());
                String userUrl = "http://vk.com/id" + user.getUserId();
                Button userView = new Button(user.getFirstName()+"\n"
                        + user.getLastName(), avatar);
                HostServicesDelegate hostService = HostServicesFactory.getInstance(mainApp);
                userView.setOnAction( (ae) -> hostService.showDocument(userUrl) );
                userView.setContentDisplay(ContentDisplay.TOP);
                flowPane.getChildren().add(userView);
            }

        } catch (IOException | URISyntaxException | HttpException | JSONException e) {
            e.printStackTrace();
        }

    }

    public void setMain(Main mainApp) { this.mainApp = mainApp; }
}
