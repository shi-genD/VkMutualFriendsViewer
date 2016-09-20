package main.controllers;

/**
 * Created by shi on 15.09.16.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import main.Main;

public class MainOverviewController {

    @FXML
    private TextField firstId;

    @FXML
    private TextField secondId;

    @FXML
    private Button btnGo;

    private Main mainApp;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleGo() {
        String s1 = firstId.getText();
        String s2 = secondId.getText();
        if (s1.length()!=0 && s2.length()!=0) {
            mainApp.showUserOverview(s1, s2);
        }

    }


    public void setMain(Main mainApp) { this.mainApp = mainApp; }

}
