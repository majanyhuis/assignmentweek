package com.accenture.assignmentweek.notused;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PrepareImport {

    public SimpleDateFormat prepareDate (String dateFromFile) {

        String[] split2 = dateFromFile.split("\\.");

        String day = split2[0];
        String month = split2[1];
        String year = split2[2];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String dateNewFormat = "20" + year + "-" + month + "-" + day;
        try {
            simpleDateFormat.parse(dateNewFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return simpleDateFormat;

    }


}
