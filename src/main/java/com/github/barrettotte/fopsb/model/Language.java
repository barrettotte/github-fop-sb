package com.github.barrettotte.fopsb.model;

public class Language {

    private String name;
    private String colorHex;

    public Language() {}

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColorHex() {
        return this.colorHex;
    }

    public void setColorHex(final String colorHex) {
        this.colorHex = colorHex;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Language{");
        sb.append("name=\"").append(this.name).append('"');
        sb.append("colorHex=\"").append(this.colorHex).append('"');
        sb.append('}');
        return sb.toString();
    }
}
