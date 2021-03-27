package com.example.stm.model.enums;

import lombok.Getter;

@Getter
public enum Types {
    TASK("TASK"),
    BUG("BUG"),
    FEATURE("FEATURE");

    private final String typeName;

    Types(String typeName) {
        this.typeName = typeName;
    }
    String getTypes(){
        return typeName;
    }
}
