package com.tertir.tertiram.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum YesNo implements UriBasedEnum {

    Yes("Да","Yes","Այո"),
    No("Нет","No","Ոչ");


    private final String russian;
    private final String english;
    private final String armenian;


    private YesNo(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static YesNo forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (YesNo) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static YesNo forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (YesNo) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static YesNo forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (YesNo) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, YesNo> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, YesNo> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, YesNo> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, YesNo> registerByReadableRU() {
            Map<String, YesNo> map = new HashMap();
            YesNo[] var1 = YesNo.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YesNo qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, YesNo> registerByReadableEN() {
            Map<String, YesNo> map = new HashMap();
            YesNo[] var1 = YesNo.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YesNo qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, YesNo> registerByReadableAM() {
            Map<String, YesNo> map = new HashMap();
            YesNo[] var1 = YesNo.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YesNo qualification = var1[var3];
                map.put(qualification.armenian, qualification);
            }

            return map;
        }
    }

    public String getRussian() {
        return this.russian;
    }

    public String getEnglish() {
        return this.english;
    }

    public String getUri() {
        return this.armenian;
    }



}
