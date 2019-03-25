package com.whamu2.wanandroid.utils.database;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author whamu2
 * @date 2018/8/28
 */
public class ListConverter implements PropertyConverter<List<String>, String> {

    @SuppressWarnings("unchecked")
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            return Arrays.asList(databaseValue.split(","));
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String link : entityProperty) {
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
