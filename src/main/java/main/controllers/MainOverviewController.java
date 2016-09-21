package main.controllers;

/**
 * Created by shi on 15.09.16.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private void initialize() { }

    @FXML
    private void handleGo() {
        String id1 = firstId.getText();
        String id2 = secondId.getText();
        if (id1.length()!=0 && id2.length()!=0) {
            mainApp.showUserOverview(id1, id2);
        }
    }

    public void setMain(Main mainApp) { this.mainApp = mainApp; }

}
