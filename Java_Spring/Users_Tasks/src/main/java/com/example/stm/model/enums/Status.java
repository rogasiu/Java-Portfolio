package com.example.stm.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }
    String getStatus(){
        return statusName;
    }
}
