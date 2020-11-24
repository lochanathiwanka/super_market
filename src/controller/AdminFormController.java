package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.StageList;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminFormController extends StageList implements Initializable {
    public AnchorPane childPane;
    public ImageView btnAddItem;
    public ImageView btnRemoveItem;
    public ImageView btnModifyItem;
    public ImageView btnSystemReports;

    public void btnAddItems(MouseEvent mouseEvent) throws Exception {
        AnchorPane addItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/AddItemsForm.fxml"));
        Scene scene =btnAddItem.getScene();
        addItemsPane.translateXProperty().set(scene.getWidth());
        childPane.getChildren().add(addItemsPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(addItemsPane.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();
    }

    public void btnDeleteItems(MouseEvent actionEvent) throws Exception {
        AnchorPane deleteItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/DeleteItemsForm.fxml"));
        Scene scene =btnRemoveItem.getScene();
        deleteItemsPane.translateXProperty().set(scene.getWidth());
        childPane.getChildren().add(deleteItemsPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(deleteItemsPane.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();

        /*childPane.getChildren().clear();
        AnchorPane deleteItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/DeleteItemsForm.fxml"));
        childPane.getChildren().setAll(deleteItemsPane.getChildren());*/
    }

    public void btnModifyItems(MouseEvent actionEvent) throws Exception {
        AnchorPane modifyItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/ModifyItemsForm.fxml"));
        Scene scene =btnModifyItem.getScene();
        modifyItemsPane.translateXProperty().set(scene.getWidth());
        childPane.getChildren().add(modifyItemsPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(modifyItemsPane.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();

        /*childPane.getChildren().clear();
        AnchorPane modifyItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/ModifyItemsForm.fxml"));
        childPane.getChildren().setAll(modifyItemsPane.getChildren());*/
    }

    public void btnSystemReports(MouseEvent actionEvent) throws Exception {
        AnchorPane systemReportsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/SystemReportsForm.fxml"));
        Scene scene =btnSystemReports.getScene();
        systemReportsPane.translateXProperty().set(scene.getWidth());
        childPane.getChildren().add(systemReportsPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(systemReportsPane.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();

        /*childPane.getChildren().clear();
        AnchorPane systemReportsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/SystemReportsForm.fxml"));
        childPane.getChildren().setAll(systemReportsPane.getChildren());*/
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        mainStage.show();
        adminStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            childPane.getChildren().clear();
            AnchorPane addItemsPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/AddItemsForm.fxml"));
            childPane.getChildren().setAll(addItemsPane.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
