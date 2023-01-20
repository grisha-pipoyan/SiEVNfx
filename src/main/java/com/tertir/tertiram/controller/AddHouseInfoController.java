package com.tertir.tertiram.controller;

import com.tertir.tertiram.persistance.model.addData.*;
import com.tertir.tertiram.rest.RequestModel;
import com.tertir.tertiram.service.JavaFxHandling;
import com.tertir.tertiram.service.SievnService;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AddHouseInfoController {

    @FXML
    private ChoiceBox<String> animalsChoice;

    @FXML
    private TextField areaText;

    @FXML
    private ChoiceBox<String> balconyChoice;

    @FXML
    private TextField bioam;

    @FXML
    private TextField bioen;

    @FXML
    private TextField bioru;

    @FXML
    private ChoiceBox<String> buildingTypeChoice;

    @FXML
    private ChoiceBox<String> cityChoice;

    @FXML
    private ChoiceBox<String> currencyChoice;

    @FXML
    private ChoiceBox<String> elevatorChoice;

    @FXML
    private ChoiceBox<Integer> floorChoice;

    @FXML
    private ChoiceBox<Integer> floorNumbersChoice;

    @FXML
    private ChoiceBox<String> isTopChoice;

    @FXML
    private ChoiceBox<String> newBuildChoice;

    @FXML
    private ChoiceBox<String> paymentChoice;

    @FXML
    private TextField priceText;

    @FXML
    private ChoiceBox<String> propertyChoice;

    @FXML
    private ChoiceBox<String> repairChoice;

    @FXML
    private ChoiceBox<Integer> roomsChoice;

    @FXML
    private ChoiceBox<String> commercialTypeChoice;


    @FXML
    private TextField streetam;

    @FXML
    private TextField streeten;

    @FXML
    private TextField streetru;

    @FXML
    private TextField titleam;

    @FXML
    private TextField titleen;

    @FXML
    private TextField titleru;

    @FXML
    private ChoiceBox<Integer> toiletsChoice;

    @FXML
    private ChoiceBox<String> yerevanChoice;

    @FXML
    private Button mainPicButton;

    @FXML
    private Button otherPicButton;

    @FXML
    private Label mainPictureName;
    @FXML
    private Label otherPicturesCount;

    @FXML
    private Button addHouseInfo;

    private RequestModel requestModel;

    @Autowired
    private SievnService sievnService;

    @Autowired
    private JavaFxHandling javaFxHandling;

    @FXML
    void initialize(ApplicationContext applicationContext, String authorizationToken) {

        initParameters();
        requestModel = new RequestModel();

        this.paymentChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setPaymentMethod(PaymentMethod.forRussian(newValue)));

        this.commercialTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setCommercialType(CommercialType.forRussian(newValue)));

        this.propertyChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setProperty(Property.forRussian(newValue)));

        this.cityChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setCity(Cities.forRussian(newValue)));

        this.yerevanChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setYerevanRegion(YerevanRegions.forRussian(newValue)));

        this.buildingTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setBuildingType(BuildingType.forRussian(newValue)));

        this.roomsChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setRooms(newValue));

        this.animalsChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setAnimals(Animals.forRussian(newValue)));

        this.isTopChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setIsTopic(YesNo.forRussian(newValue)));

        this.newBuildChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setNewBuilt(YesNo.forRussian(newValue)));

        this.repairChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setRepairType(RepairType.forRussian(newValue)));

        this.balconyChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setBalcony(Balcony.forRussian(newValue)));

        this.toiletsChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setToilets(newValue));

        this.elevatorChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setElevator(YesNo.forRussian(newValue)));

        this.currencyChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setCurrencyType(CurrencyType.valueOf(newValue)));

        this.floorChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setFloor(newValue));

        this.floorNumbersChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                requestModel.setFloorNumber(newValue));

        this.areaText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                areaText.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.priceText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priceText.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.mainPicButton.setOnAction(event -> {
            FileChooser fil_chooser = new FileChooser();
            File file = fil_chooser.showOpenDialog(new Stage());
            if (file == null) {
                javaFxHandling.throwException("Выберите основное изображение");
                return;
            }

            requestModel.setMainPicture(Utils.toBase64(DSSUtils.toByteArray(file)));
            mainPictureName.setText(file.getName());

        });

        this.otherPicButton.setOnAction(event -> {
            FileChooser fil_chooser = new FileChooser();
            List<File> files = fil_chooser.showOpenMultipleDialog(new Stage());

            if (files.size() > 0) {

                List<String> collect = files.stream().map(file -> Utils.toBase64(DSSUtils.toByteArray(
                        file))).collect(Collectors.toList());
                requestModel.setPictures(collect);
                otherPicturesCount.setText(String.format("%o выбрано изображение", files.size()));
            }

        });


        this.addHouseInfo.setOnAction(event -> {
            try {
                requestModel.setArea(Double.valueOf(areaText.getText()));
                requestModel.setPrice(Double.valueOf(priceText.getText()));

                requestModel.setTitleRU(titleru.getText());
                requestModel.setTitleEN(titleen.getText());
                requestModel.setTitleAM(titleam.getText());

                requestModel.setStreetRU(streetru.getText());
                requestModel.setStreetEN(streeten.getText());
                requestModel.setStreetAM(streetam.getText());

                requestModel.setBioRU(bioru.getText());
                requestModel.setBioEN(bioen.getText());
                requestModel.setBioAM(bioam.getText());

                sievnService.addHouseData(requestModel, authorizationToken);

                javaFxHandling.showAlert("Добавлено успешно");

            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });

    }

    private void initParameters() {
        this.paymentChoice.getItems().clear();
        this.propertyChoice.getItems().clear();
        this.cityChoice.getItems().clear();
        this.yerevanChoice.getItems().clear();
        this.buildingTypeChoice.getItems().clear();
        this.roomsChoice.getItems().clear();
        this.animalsChoice.getItems().clear();
        this.isTopChoice.getItems().clear();
        this.newBuildChoice.getItems().clear();
        this.repairChoice.getItems().clear();
        this.balconyChoice.getItems().clear();
        this.toiletsChoice.getItems().clear();
        this.elevatorChoice.getItems().clear();
        this.currencyChoice.getItems().clear();
        this.floorNumbersChoice.getItems().clear();
        this.floorChoice.getItems().clear();
        this.commercialTypeChoice.getItems().clear();

        this.paymentChoice.getItems().addAll(Arrays.stream(PaymentMethod.values()).map(PaymentMethod::getRussian).toArray(String[]::new));
        this.commercialTypeChoice.getItems().addAll(Arrays.stream(CommercialType.values()).map(CommercialType::getRussian).toArray(String[]::new));
        this.propertyChoice.getItems().addAll(Arrays.stream(Property.values()).map(Property::getRussian).toArray(String[]::new));
        this.cityChoice.getItems().addAll(Arrays.stream(Cities.values()).map(Cities::getRussian).toArray(String[]::new));
        this.yerevanChoice.getItems().addAll(Arrays.stream(YerevanRegions.values()).map(YerevanRegions::getRussian).toArray(String[]::new));

        this.buildingTypeChoice.getItems().addAll(Arrays.stream(BuildingType.values()).map(BuildingType::getRussian).toArray(String[]::new));
        this.roomsChoice.getItems().addAll(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));
        this.animalsChoice.getItems().addAll(Arrays.stream(Animals.values()).map(Animals::getRussian).toArray(String[]::new));

        this.isTopChoice.getItems().addAll(Arrays.stream(YesNo.values()).map(YesNo::getRussian).toArray(String[]::new));
        this.newBuildChoice.getItems().addAll(Arrays.stream(YesNo.values()).map(YesNo::getRussian).toArray(String[]::new));

        this.repairChoice.getItems().addAll(Arrays.stream(RepairType.values()).map(RepairType::getRussian).toArray(String[]::new));
        this.balconyChoice.getItems().addAll(Arrays.stream(Balcony.values()).map(Balcony::getRussian).toArray(String[]::new));
        this.toiletsChoice.getItems().addAll(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));
        this.elevatorChoice.getItems().addAll(Arrays.stream(YesNo.values()).map(YesNo::getRussian).toArray(String[]::new));

        this.currencyChoice.getItems().addAll(Arrays.stream(CurrencyType.values()).map(CurrencyType::name).toArray(String[]::new));

        this.floorChoice.getItems().addAll(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));
        this.floorNumbersChoice.getItems().addAll(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));

        this.animalsChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Продажа").or(
                this.paymentChoice.valueProperty().isEqualTo("Коммерческая")));
        this.yerevanChoice.disableProperty().bind(this.cityChoice.valueProperty().isNotEqualTo("Ереван"));
        this.propertyChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Коммерческая"));

        this.roomsChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Коммерческая"));
        this.toiletsChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Коммерческая"));
        this.balconyChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Коммерческая"));
        this.buildingTypeChoice.disableProperty().bind(this.paymentChoice.valueProperty().isEqualTo("Коммерческая").or(
                this.propertyChoice.valueProperty().isEqualTo("Частный дом")
        ));

        this.newBuildChoice.disableProperty().bind(this.propertyChoice.valueProperty().isEqualTo("Частный дом"));
        this.elevatorChoice.disableProperty().bind(this.propertyChoice.valueProperty().isEqualTo("Частный дом"));
        this.floorChoice.disableProperty().bind(this.propertyChoice.valueProperty().isEqualTo("Частный дом"));
        this.floorNumbersChoice.disableProperty().bind(this.propertyChoice.valueProperty().isEqualTo("Частный дом"));

        this.commercialTypeChoice.disableProperty().bind(this.paymentChoice.valueProperty().isNotEqualTo("Коммерческая"));
    }


}
