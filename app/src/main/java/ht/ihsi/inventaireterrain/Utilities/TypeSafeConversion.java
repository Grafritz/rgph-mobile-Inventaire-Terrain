package ht.ihsi.inventaireterrain.Utilities;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JFDuverseau on 8/23/2016.
 */
public class TypeSafeConversion {
    public static int TranslateStringToInterger(String values) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("^([0-9]+)$");
        matcher = pattern.matcher(values);
        if( values == null){
            return 0;
        }else if( values.equalsIgnoreCase("")){
            return 0;
        }else if(TextUtils.isEmpty(values)){
            return 0;
        }else if (!matcher.matches()) {
            return 0;
        }else {
            return Integer.parseInt(values);
        }
    }

    public static String TranslateIntToBoolString(int pInteger) {
        switch (pInteger) {
            case 1:
            case -1:
                return "Yes";
            default:
                return "No";
        }
    }

    public static Boolean TranslateIntToBool(int pInteger) {
        switch (pInteger) {
            case 1:
            case -1:
                return true;
            default:
                return false;
        }
    }

    public static Boolean TranslateStringToBool(String pString) {
        switch (pString) {
            case "1":
                return true;
            default:
                return false;
        }
    }
}
