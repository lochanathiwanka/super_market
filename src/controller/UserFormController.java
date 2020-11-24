package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.StageList;
import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController extends StageList implements Initializable {
    public AnchorPane childPane;
    public ImageView btnAddCustomer;
    public ImageView btnManageOrder;

    public void btnBackOnAction(MouseEvent mouseEvent) {
        mainStage.show();
        userStage.close();
    }

    public void btnAddnewCustomerOnAction(MouseEvent actionEvent) throws Exception {
        AnchorPane addCustomerPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/AddNewCustomerForm.fxml"));
        Scene scene =btnAddCustomer.getScene();
        addCustomerPane.translateXProperty().set(scene.getWidth());
        childPane.getChildren().add(addCustomerPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(addCustomerPane.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();

        //childPane.getChildren().clear();
        //AnchorPane addCustomerPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/AddNewCustomerForm.fxml"));
        //childPane.getChildren().setAll(addCustomerPane.getChildren());
    }

    public void btnManageOrdersOnAction(MouseEvent actionEvent) throws Exception {
        setManageOrderPane();
    }

    public void setManageOrderPane()throws Exception{
        AnchorPane manageOrderPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/ManageOrderForm.fxml"));
        Scene scene = btnManageOrder.getScene();
        manageOrderPane.translateYProperty().set(scene.getHeight());
        childPane.getChildren().add(manageOrderPane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(manageOrderPane.translateYProperty(),0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            childPane.getChildren().remove(childPane);
        });
        timeline.play();

        //childPane.getChildren().clear();
        //AnchorPane manageOrderPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/ManageOrderForm.fxml"));
        //childPane.getChildren().setAll(manageOrderPane.getChildren());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            childPane.getChildren().clear();
            AnchorPane manageOrderPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/ManageOrderForm.fxml"));
            manageOrderPane.translateYProperty().set(manageOrderPane.getHeight());
            childPane.getChildren().setAll(manageOrderPane.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
