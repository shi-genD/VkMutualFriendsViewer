package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import main.apivk.APIvk;
import org.apache.http.HttpException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shi on 16.09.16.
 */
public class UserOverviewController {

    @FXML
    FlowPane flowPane;

    private Stage stage;

    @FXML
    private Button showRes;

    private String id1;
    private String id2;
    private APIvk apiVk;

    @FXML
    private void initialize() {

    }

    @FXML
    public void handleShow() {
        apiVk = new APIvk();
        Set<String> firstSet;
        Set<String> secondSet;
        try {

            firstSet = new HashSet<>(apiVk.getFriends(id1));
            secondSet = new HashSet<>(apiVk.getFriends(id2));
            firstSet.retainAll(secondSet);
            System.out.println("Number: " + firstSet.size());
            System.out.println(firstSet.toString());

            for (String s : firstSet) {
                ImageView avatar = new ImageView(apiVk.getUserInfo(s).getPhotoURL());
                Label user = new Label(apiVk.getUserInfo(s).getFirstName()+"\n"+apiVk.getUserInfo(s).getLastName(), avatar);
                user.setContentDisplay(ContentDisplay.TOP);
                flowPane.getChildren().add(user);
            }


        } catch (IOException | URISyntaxException | HttpException | JSONException e) {
            e.printStackTrace();
        }


    }

    public void setDialogStage(Stage stage, String id1, String id2) {

        this.stage = stage;
        this.id1 = id1;
        this.id2 = id2;
    }
}
