package main.controllers;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.Main;
import main.apivk.APIvk;
import main.apivk.VkUser;
import main.net.ImageLoaderCallable;
import main.net.ImageViewAsyncApplier;
import main.utils.ResultSetWrap;
import main.utils.UserNotFoundException;

import java.util.*;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

public class MainOverviewController {

    @FXML
    private TextField firstId;

    @FXML
    private TextField secondId;

    @FXML
    private Label firstErrMsg;

    @FXML
    private Label secondErrMsg;

    @FXML
    private Button btnGo;

    @FXML
    private FlowPane flowPane;

    @FXML
    private AnchorPane anchorPane;

    private Main mainApp;

    @FXML
    private void initialize() {
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);
    }

    @FXML
    private void handleGo() throws InterruptedException {
        flowPane.getChildren().clear();
        List<Label> labels = new ArrayList<>();
        labels.add(firstErrMsg);
        labels.add(secondErrMsg);
        for (Label l : labels)
            l.setText("");

        CompletableFuture<ResultSetWrap> future = CompletableFuture.supplyAsync(() -> APIvk.parseInputId(firstId.getText(), 0))
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> APIvk.parseInputId(secondId.getText(), 1)), APIvk::getMutualFriends)
                .exceptionally(x -> {
                    if (x.getCause() instanceof UserNotFoundException) {
                        UserNotFoundException e = (UserNotFoundException) x.getCause();
                        return  new ResultSetWrap(e);
                    } else {
                        x.printStackTrace();
                        return null;
                    }
                });
        ResultSetWrap result = future.join();
        if (result.isSuccess())
            showResult(result.getResultSet());
        else
            labels.get(result.getException().getNumber()).setText(result.getException().getMessage());

    }

    private void showResult(Set<VkUser> mutualFriends){

            if (mutualFriends.size()==0) {
                flowPane.getChildren().add(new Label("Mutual friends not found"));
                return;
            }

            Map<VkUser, Future<Image>> friendsPhotos = getUserPhotos(mutualFriends);
            ImageViewAsyncApplier imageViewApplier = new ImageViewAsyncApplier();
            for (VkUser user : mutualFriends) {
                ImageView avatar = new ImageView();
                Future<Image> imageFuture = friendsPhotos.get(user);
                imageViewApplier.add(imageFuture, avatar);
                String userUrl = "http://vk.com/id" + user.getUserId();
                Button userView = new Button(user.getFirstName() + "\n"
                        + user.getLastName(), avatar);
                HostServicesDelegate hostService = HostServicesFactory.getInstance(mainApp);
                userView.setOnAction((ae) -> hostService.showDocument(userUrl));
                userView.setContentDisplay(ContentDisplay.TOP);
                flowPane.getChildren().add(userView);
            }
            imageViewApplier.startApplying();
        }

    public void setMain(Main mainApp) { this.mainApp = mainApp; }

    private static Map<VkUser, Future<Image>> getUserPhotos(Collection<VkUser> users) {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<Image>> imageFutures = users.stream()
                .map(VkUser::getPhotoURL)
                .map(ImageLoaderCallable::new)
                .map(executorService::submit)
                .collect(toList());

        Map<VkUser, Future<Image>> result = new HashMap<>();

        int index = 0;
        Iterator<VkUser> userIterator = users.iterator();

        while (userIterator.hasNext()) {
            result.put(userIterator.next(), imageFutures.get(index++));
        }
        return result;
    }
}
