package main.apivk;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MyWebView extends Application {
    private String client_id = "5629635";
    private String scope = "friends";
    private String redirect_uri = "http://oauth.vk.com/blank.html";
    private String display = "page";
    private String response_type = "token";
    private String access_token;
    private String email = "**********";//тут должен быть прописан email
    private String pass = "**********";//тут должен быть прописан пароль

    private Stage primaryStage;
    private StackPane root;

    @Override
    public void start(Stage stage) throws Exception {

        this.primaryStage = stage;
        this.primaryStage.setTitle("VKViewer");

        //showMainOverview();

        StackPane root = new StackPane();

        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        String currentURL = new String("http://oauth.vk.com/authorize?" +
                "client_id=" + client_id +
                "&scope=" + scope +
                "&redirect_uri=" + redirect_uri +
                "&display=" + display +
                "&response_type=" + response_type);
        engine.load(currentURL);
        root.getChildren().add(view);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
        while(currentURL.equals(engine.getLocation())) {}
        currentURL = engine.getLocation();
        System.out.println(currentURL);
    }

   /* public void showMainOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainOverview.fxml"));
            AnchorPane mainOverview = (AnchorPane) loader.load();

            rootLayout.setCenter((mainOverview));

            MainOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }
}