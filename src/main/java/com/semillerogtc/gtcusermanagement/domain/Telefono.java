package com.semillerogtc.gtcusermanagement.domain;

public class Telefono {
    private String number;
    private String citycode;
    private String countrycode;

    public Telefono(String number, String citycode, String countrycode) {

        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }

    public String getNumber() {

        return this.number;
    }

    public String getCitycode() {

        return this.citycode;
    }

    public String getCountrycode() {

        return this.countrycode;
    }

    public static Telefono instance(String number, String citycode, String countrycode) {
        return new Telefono(number, citycode, countrycode);
    }
}
