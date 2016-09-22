package main.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi on 21.09.16.
 */
public class StringParser {
    private String string;

    public StringParser(String str) {
        this.string = str;
    }

    public String getString() {
        return string;
    }

    public boolean checkString() {

        char c;
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    public boolean checkUrl() {
        Pattern pat = Pattern.compile("^((https?://)?(www\\.)?(vk\\.com/)?(\\w+))$");
        Matcher mat;
        mat = pat.matcher(string);
        if (mat.find()) {
            this.string = mat.group(5);
        }
        return mat.matches();
    }

}

