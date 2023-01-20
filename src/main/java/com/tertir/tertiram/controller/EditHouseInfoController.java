package com.tertir.tertiram.controller;

import com.tertir.tertiram.persistance.model.addData.*;
import com.tertir.tertiram.rest.RequestModelForUpdate;
import com.tertir.tertiram.rest.ResponseModelAdmin;
import com.tertir.tertiram.service.JavaFxHandling;
import com.tertir.tertiram.service.SievnService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EditHouseInfoController {

    @FXML
    private Button addHouseInfo;

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
    private ChoiceBox<String> commercialTypeChoice;

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
    private Button mainPicButton;

    @FXML
    private Label mainPictureName;

    @FXML
    private ChoiceBox<String> newBuildChoice;

    @FXML
    private Button otherPicButton;

    @FXML
    private Label otherPicturesCount;

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
    private Button backButton;

    @FXML
    private Button frontButton;

    @FXML
    private ImageView imageView;

    @Autowired
    private SievnService sievnService;

    @Autowired
    private JavaFxHandling javaFxHandling;

    private RequestModelForUpdate requestModel;

    private int counter = 0;
    private List<Image> images;

    @FXML
    void initialize(ApplicationContext applicationContext, String authorizationToken, ResponseModelAdmin houseDataById) {

        initParameters();
        requestModel = new RequestModelForUpdate();

        try {
            images = sievnService.getAllImages(houseDataById.getHouseId(), houseDataById.getPictures(), authorizationToken);
            this.imageView.setImage(images.get(0));
            counter = 0;
        } catch (Exception e) {
            javaFxHandling.throwException(e.getMessage());
        }

        this.backButton.setOnAction(event -> {
            try {
                counter--;
                if (counter >= 0) {
                    imageView.setImage(images.get(counter));
                } else {
                    counter = 0;
                }
            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });

        this.frontButton.setOnAction(event -> {
            try {
                counter++;
                if (counter < images.size()) {
                    imageView.setImage(images.get(counter));
                }else {
                    counter = images.size()-1;
                }
            } catch (Exception e) {
                javaFxHandling.throwException(e.getMessage());
            }
        });

        this.titleru.setText(houseDataById.getTitleRU());
        this.titleen.setText(houseDataById.getTitleEN());
        this.titleam.setText(houseDataById.getTitleAM());

        this.streetru.setText(houseDataById.getStreetRU());
        this.streeten.setText(houseDataById.getStreetEN());
        this.streetam.setText(houseDataById.getStreetAM());

        this.bioru.setText(houseDataById.getBioRU());
        this.bioen.setText(houseDataById.getBioEN());
        this.bioam.setText(houseDataById.getBioAM());

        this.areaText.setText(houseDataById.getArea().toString());
        this.priceText.setText(houseDataById.getPrice().toString());

        this.paymentChoice.getSelectionModel().select(houseDataById.getPaymentMethod());
        this.propertyChoice.getSelectionModel().select(houseDataById.getProperty());
        this.cityChoice.getSelectionModel().select(houseDataById.getCity());
        this.yerevanChoice.getSelectionModel().select(houseDataById.getYerevanRegion());
        this.buildingTypeChoice.getSelectionModel().select(houseDataById.getBuildingType());
        this.roomsChoice.getSelectionModel().select(houseDataById.getRooms());
        this.animalsChoice.getSelectionModel().select(houseDataById.getAnimals());
        this.isTopChoice.getSelectionModel().select(houseDataById.getIsTopic());
        this.newBuildChoice.getSelectionModel().select(houseDataById.getNewBuilt());
        this.repairChoice.getSelectionModel().select(houseDataById.getRepairType());
        this.balconyChoice.getSelectionModel().select(houseDataById.getBalcony());
        this.toiletsChoice.getSelectionModel().select(houseDataById.getToilets());
        this.elevatorChoice.getSelectionModel().select(houseDataById.getElevator());
        this.currencyChoice.getSelectionModel().select(houseDataById.getCurrencyType());
        this.floorNumbersChoice.getSelectionModel().select(houseDataById.getFloorNumber());
        this.floorChoice.getSelectionModel().select(houseDataById.getFloor());
        this.commercialTypeChoice.getSelectionModel().select(houseDataById.getCommercialType());

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


        this.addHouseInfo.setOnAction(event -> {

            try {

                requestModel.setId(houseDataById.getHouseId());
                requestModel.setIsTopic(YesNo.forRussian(this.isTopChoice.getSelectionModel().getSelectedItem()));
                requestModel.setProperty(Property.forRussian(this.propertyChoice.getSelectionModel().getSelectedItem()));
                requestModel.setPaymentMethod(PaymentMethod.forRussian(this.paymentChoice.getSelectionModel().getSelectedItem()));
                requestModel.setCommercialType(CommercialType.forRussian(this.commercialTypeChoice.getSelectionModel().getSelectedItem()));
                requestModel.setCity(Cities.forRussian(this.cityChoice.getSelectionModel().getSelectedItem()));
                requestModel.setYerevanRegion(YerevanRegions.forRussian(this.yerevanChoice.getSelectionModel().getSelectedItem()));
                requestModel.setBuildingType(BuildingType.forRussian(this.buildingTypeChoice.getSelectionModel().getSelectedItem()));
                requestModel.setNewBuilt(YesNo.forRussian(this.newBuildChoice.getSelectionModel().getSelectedItem()));
                requestModel.setArea(Double.valueOf(this.areaText.getText()));
                requestModel.setElevator(YesNo.forRussian(this.elevatorChoice.getSelectionModel().getSelectedItem()));
                requestModel.setFloor(this.floorChoice.getSelectionModel().getSelectedItem());
                requestModel.setFloorNumber(this.floorNumbersChoice.getSelectionModel().getSelectedItem());
                requestModel.setRepairType(RepairType.forRussian(this.repairChoice.getSelectionModel().getSelectedItem()));
                requestModel.setRooms(this.roomsChoice.getSelectionModel().getSelectedItem());
                requestModel.setToilets(this.toiletsChoice.getSelectionModel().getSelectedItem());
                requestModel.setAnimals(Animals.forRussian(this.animalsChoice.getSelectionModel().getSelectedItem()));
                requestModel.setBalcony(Balcony.forRussian(this.balconyChoice.getSelectionModel().getSelectedItem()));
                requestModel.setPrice(Double.valueOf(this.priceText.getText()));
                requestModel.setCurrencyType(CurrencyType.valueOf(this.currencyChoice.getSelectionModel().getSelectedItem()));

                requestModel.setBioRU(this.bioru.getText());
                requestModel.setBioEN(this.bioen.getText());
                requestModel.setBioAM(this.bioam.getText());

                requestModel.setTitleRU(this.titleru.getText());
                requestModel.setTitleEN(this.titleen.getText());
                requestModel.setTitleAM(this.titleam.getText());

                requestModel.setStreetRU(this.streetru.getText());
                requestModel.setStreetEN(this.streeten.getText());
                requestModel.setStreetAM(this.streetam.getText());

                sievnService.editHouseData(requestModel, authorizationToken);

                javaFxHandling.showAlert("Обновлено успешно");

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
