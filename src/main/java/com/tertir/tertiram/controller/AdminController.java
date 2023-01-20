package com.tertir.tertiram.controller;

import com.tertir.tertiram.exception.FxmlLoadingException;
import com.tertir.tertiram.rest.ResponseModelAdmin;
import com.tertir.tertiram.service.JavaFxHandling;
import com.tertir.tertiram.service.SievnService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AdminController {

    @FXML
    private MenuItem addHouseInfo;

    @FXML
    private TableColumn<TableObject, Label> bio;
    @FXML
    private MenuItem changePassword;

    @FXML
    private TableColumn<TableObject, Label> city;

    @FXML
    private TableColumn<TableObject, Label> currency;

    @FXML
    private BorderPane globalPane;

    @FXML
    private TableColumn<TableObject, Label> id;

    @FXML
    private MenuItem logout;

    @FXML
    private TableColumn<TableObject, Label> price;

    @FXML
    private MenuItem refresh;

    @FXML
    private TableColumn<TableObject, Label> street;

    @FXML
    private TableColumn<TableObject, Label> title;

    @FXML
    private TableColumn<TableObject, Label> top;

    @FXML
    private TableView<TableObject> userTableView;

    @FXML
    private TableColumn<TableObject, Label> yerevan;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Autowired
    private SievnService sievnService;

    @Autowired
    private JavaFxHandling javaFxHandling;

    @Value("classpath:/fxml/MainControllerPage.fxml")
    private Resource mainResource;

    @Value("classpath:/fxml/ChangePasswordControllerPage.fxml")
    private Resource changePasswordControllerResource;

    @Value("classpath:/fxml/AddHouseInfoControllerPage.fxml")
    private Resource addHouseInfoControllerResource;

    @Value("classpath:/fxml/EditHouseInfoControllerPage.fxml")
    private Resource editHouseInfoControllerResource;

    private List<ResponseModelAdmin> allAdminData;

    @FXML
    void initialize(ApplicationContext applicationContext, String authorizationToken) {

        this.userTableView.getItems().clear();
        this.allAdminData = new ArrayList<>();
        initializeTable();

        this.refresh.setOnAction(event -> {
            try {
                this.userTableView.getItems().clear();

                this.allAdminData = sievnService.getAllAdminData(authorizationToken);
                for (ResponseModelAdmin product :
                        allAdminData) {
                    TableObject tableObject = new TableObject(
                            String.valueOf(product.getHouseId()),
                            product.getIsTopic(),
                            product.getTitleRU(),
                            product.getBioRU(),
                            product.getCity(),
                            product.getYerevanRegion(),
                            product.getStreetRU(),
                            String.valueOf(product.getPrice()),
                            product.getCurrencyType()
                    );
                    userTableView.getItems().add(tableObject);
                }
            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });

        this.logout.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(mainResource.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                BorderPane borderPane = fxmlLoader.load();

                Stage stage = (Stage) globalPane.getScene().getWindow();
                Scene scene = stage.getScene();
                if (scene == null) {
                    scene = new Scene(borderPane, 1000, 600);
                    stage.setScene(scene);
                } else {
                    stage.getScene().setRoot(borderPane);
                }

                MainController mainController = fxmlLoader.getController();
                mainController.initialize(applicationContext);

            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });

        this.changePassword.setOnAction(event -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(changePasswordControllerResource.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Pane pane = fxmlLoader.load();

                ChangePasswordController changePasswordController = fxmlLoader.getController();
                changePasswordController.initialize(authorizationToken);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UTILITY);
                stage.setTitle("Смена пароля");
                stage.setScene(new Scene(pane));
                stage.getIcons().add(new Image(this.javaFxHandling.getLogoResource().getInputStream()));
                stage.showAndWait();

            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }

        });

        this.addHouseInfo.setOnAction(event -> {

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(addHouseInfoControllerResource.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);

                Parent parent = fxmlLoader.load();

                AddHouseInfoController addHouseInfoController = fxmlLoader.getController();
                addHouseInfoController.initialize(applicationContext, authorizationToken);

                Scene scene = new Scene(parent, 1000, 600);
                Stage stage = new Stage();
                stage.setTitle("SiEVN");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.getIcons().add(new Image(javaFxHandling.getLogoResource().getInputStream()));

                stage.show();

            } catch (IOException e) {
                javaFxHandling.throwException("Системная ошибка. Пожалуйста, повторите позже.");
                throw new FxmlLoadingException(e);
            }

        });


        this.deleteButton.disableProperty().bind(userTableView.getSelectionModel().selectedItemProperty().isNull());
        this.editButton.disableProperty().bind(userTableView.getSelectionModel().selectedItemProperty().isNull());

        this.deleteButton.setOnAction(event -> {
            TableObject tableObject = userTableView.getSelectionModel().getSelectedItem();
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Вы хотите удалить?");
                alert.setTitle("Удаление");
                Optional<ButtonType> choose = alert.showAndWait();

                if(choose.isPresent() && choose.get() == ButtonType.OK){
                    sievnService.deleteHouseInfo(tableObject.getId(), authorizationToken);
                    javaFxHandling.showAlert("Удалено успешно");
                }

            }catch (Exception e){
                javaFxHandling.throwException(e.getMessage());
            }
        });


        this.editButton.setOnAction(event -> {

            try {
                TableObject tableObject = userTableView.getSelectionModel().getSelectedItem();

                ResponseModelAdmin houseDataById = sievnService.getHouseDataById(tableObject.getId(), authorizationToken);

                FXMLLoader fxmlLoader = new FXMLLoader(editHouseInfoControllerResource.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);

                Parent parent = fxmlLoader.load();

                EditHouseInfoController editHouseInfoController = fxmlLoader.getController();
                editHouseInfoController.initialize(applicationContext, authorizationToken, houseDataById);

                Scene scene = new Scene(parent, 1000, 600);
                Stage stage = new Stage();
                stage.setTitle("SiEVN");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.getIcons().add(new Image(javaFxHandling.getLogoResource().getInputStream()));

                stage.show();

            } catch (IOException e) {
                javaFxHandling.throwException("Системная ошибка. Пожалуйста, повторите позже.");
            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });


    }


    private void initColumn(TableColumn<TableObject, Label> tableColumn, String name) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(name));
        tableColumn.setSortable(false);
    }

    private void initializeTable() {
        initColumn(id, "id");
        initColumn(top, "top");
        initColumn(title, "title");
        initColumn(bio, "bio");
        initColumn(city, "city");
        initColumn(yerevan, "yerevan");
        initColumn(street, "street");
        initColumn(price, "price");
        initColumn(currency, "currency");
        this.userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    public static class TableObject {

        private String id;
        private String top;
        private String title;
        private String bio;
        private String city;
        private String yerevan;
        private String street;
        private String price;
        private String currency;

        public TableObject(String id, String top, String title, String bio, String city, String yerevan, String street, String price, String currency) {
            this.id = id;
            this.top = top;
            this.title = title;
            this.bio = bio;
            this.city = city;
            this.yerevan = yerevan;
            this.street = street;
            this.price = price;
            this.currency = currency;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getYerevan() {
            return yerevan;
        }

        public void setYerevan(String yerevan) {
            this.yerevan = yerevan;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }


}
