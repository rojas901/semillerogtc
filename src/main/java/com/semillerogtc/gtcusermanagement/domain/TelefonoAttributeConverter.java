package com.semillerogtc.gtcusermanagement.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class TelefonoAttributeConverter implements AttributeConverter<Telefono, String> {

    private static final String SEPARATOR = ",";
    @Override
    public String convertToDatabaseColumn(Telefono attribute) {
        StringBuilder sb = new StringBuilder();
        sb.append(attribute.getNumber());
        sb.append(SEPARATOR);
        sb.append(attribute.getCitycode());
        sb.append(SEPARATOR);
        sb.append(attribute.getCountrycode());
        return attribute == null ? null : sb.toString();
    }

    @Override
    public Telefono convertToEntityAttribute(String dbData) {
        //return dbData == null ? null : new Telefono(dbData);
        String[] pieces = dbData.split(SEPARATOR);
        Telefono telefono = new Telefono(pieces[0], pieces[1], pieces[2]);
        return telefono;
    }

    /*@Override
    public Telefono convertToEntityAttribute(List<String> dbData) {
        return dbData == null ? null : new Telefono(dbData.get(0), dbData.get(1), dbData.get(2));
    }*/

    /*@Override
    public Telefono convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Telefono(dbData);
    }*/
}
