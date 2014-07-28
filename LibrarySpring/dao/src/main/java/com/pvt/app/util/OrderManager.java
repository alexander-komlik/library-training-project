package com.pvt.app.util;

public class OrderManager {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private String type = ASC;
    private String field = "id";

    public void asc(String field) {
        type = ASC;
        this.field = field;
    }

    public void desc(String field) {
        type = DESC;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }
}
