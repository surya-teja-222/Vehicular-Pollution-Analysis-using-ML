package com.pollutionmonitor.helperclass;

public class vehiclePrimary {
    String vehicleClass, isVehicleMaintained, dateOfPurchase, vehicleNumber  ;

    public vehiclePrimary() {
    }


    public vehiclePrimary(String vehicleClass, String isVehicleMaintained, String dateOfPurchase, String vehicleNumber) {
        this.vehicleClass = vehicleClass;
        this.isVehicleMaintained = isVehicleMaintained;
        this.dateOfPurchase = dateOfPurchase;
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getIsVehicleMaintained() {
        return isVehicleMaintained;
    }

    public void setIsVehicleMaintained(String isVehicleMaintained) {
        this.isVehicleMaintained = isVehicleMaintained;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
