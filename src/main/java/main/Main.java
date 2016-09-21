package main;


import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import main.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("VK Viewer");
        initRootLayout();
        showMainOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/controllers/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/controllers/MainOverview.fxml"));
            AnchorPane mainOverview = loader.load();
            rootLayout.setCenter((mainOverview));
            MainOverviewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserOverview(String id1, String id2) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/controllers/UserOverview.fxml"));
            FlowPane userOverview = loader.load();
            userOverview.setAlignment(Pos.TOP_CENTER);
            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(userOverview);
            stage.setScene(scene);
            HostServicesDelegate hostService = HostServicesFactory.getInstance(this);
            UserOverviewController controller = loader.getController();
            controller.setDialogStage(stage, id1, id2, hostService);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        launch(args);
    }
}
