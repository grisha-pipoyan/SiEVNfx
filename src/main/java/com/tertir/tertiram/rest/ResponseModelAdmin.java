package com.tertir.tertiram.rest;
import java.util.List;

public class ResponseModelAdmin {

    private Long houseId;

    private String isTopic;

    private String titleRU;

    private String titleEN;

    private String titleAM;

    private String bioRU;

    private String bioEN;

    private String bioAM;

    private String property;

    private String paymentMethod;

    private String commercialType;

    private String city;

    private String yerevanRegion;

    private String streetRU;

    private String streetEN;

    private String streetAM;

    private String buildingType;

    private String newBuilt;

    private Double area;

    private String elevator;

    private Integer floor;

    private Integer floorNumber;

    private String repairType;

    private Integer rooms;

    private Integer toilets;

    private String animals;

    private String balcony;

    private Double price;

    private String currencyType;

    private List<String> pictures;

    public ResponseModelAdmin(Long houseId, String isTopic, String titleRU, String titleEN, String titleAM, String bioRU, String bioEN, String bioAM, String property, String paymentMethod, String commercialType, String city, String yerevanRegion, String streetRU, String streetEN, String streetAM, String buildingType, String newBuilt, Double area, String elevator, Integer floor, Integer floorNumber, String repairType, Integer rooms, Integer toilets, String animals, String balcony, Double price, String currencyType, List<String> pictures) {
        this.houseId = houseId;
        this.isTopic = isTopic;
        this.titleRU = titleRU;
        this.titleEN = titleEN;
        this.titleAM = titleAM;
        this.bioRU = bioRU;
        this.bioEN = bioEN;
        this.bioAM = bioAM;
        this.property = property;
        this.paymentMethod = paymentMethod;
        this.commercialType = commercialType;
        this.city = city;
        this.yerevanRegion = yerevanRegion;
        this.streetRU = streetRU;
        this.streetEN = streetEN;
        this.streetAM = streetAM;
        this.buildingType = buildingType;
        this.newBuilt = newBuilt;
        this.area = area;
        this.elevator = elevator;
        this.floor = floor;
        this.floorNumber = floorNumber;
        this.repairType = repairType;
        this.rooms = rooms;
        this.toilets = toilets;
        this.animals = animals;
        this.balcony = balcony;
        this.price = price;
        this.currencyType = currencyType;
        this.pictures = pictures;
    }

    public ResponseModelAdmin() {
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getIsTopic() {
        return isTopic;
    }

    public void setIsTopic(String isTopic) {
        this.isTopic = isTopic;
    }

    public String getTitleRU() {
        return titleRU;
    }

    public void setTitleRU(String titleRU) {
        this.titleRU = titleRU;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleAM() {
        return titleAM;
    }

    public void setTitleAM(String titleAM) {
        this.titleAM = titleAM;
    }

    public String getBioRU() {
        return bioRU;
    }

    public void setBioRU(String bioRU) {
        this.bioRU = bioRU;
    }

    public String getBioEN() {
        return bioEN;
    }

    public void setBioEN(String bioEN) {
        this.bioEN = bioEN;
    }

    public String getBioAM() {
        return bioAM;
    }

    public void setBioAM(String bioAM) {
        this.bioAM = bioAM;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYerevanRegion() {
        return yerevanRegion;
    }

    public void setYerevanRegion(String yerevanRegion) {
        this.yerevanRegion = yerevanRegion;
    }

    public String getStreetRU() {
        return streetRU;
    }

    public void setStreetRU(String streetRU) {
        this.streetRU = streetRU;
    }

    public String getStreetEN() {
        return streetEN;
    }

    public void setStreetEN(String streetEN) {
        this.streetEN = streetEN;
    }

    public String getStreetAM() {
        return streetAM;
    }

    public void setStreetAM(String streetAM) {
        this.streetAM = streetAM;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getNewBuilt() {
        return newBuilt;
    }

    public void setNewBuilt(String newBuilt) {
        this.newBuilt = newBuilt;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getToilets() {
        return toilets;
    }

    public void setToilets(Integer toilets) {
        this.toilets = toilets;
    }

    public String getAnimals() {
        return animals;
    }

    public void setAnimals(String animals) {
        this.animals = animals;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
