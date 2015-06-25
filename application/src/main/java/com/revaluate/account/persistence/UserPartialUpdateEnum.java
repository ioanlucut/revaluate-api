package com.revaluate.account.persistence;

public enum UserPartialUpdateEnum {

    INITIATED_STATUS("UserDTO__update__initiated-status"),
    ACCOUNT_DETAILS("UserDTO__update__account-details");

    private String mapId;

    UserPartialUpdateEnum(String mapId) {
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }
}