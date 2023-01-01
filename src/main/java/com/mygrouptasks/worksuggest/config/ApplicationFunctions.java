package com.mygrouptasks.worksuggest.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationFunctions implements IApplicationFunctions {

    @Override
    public boolean hasOneOfChars(String str, String chars) {
    //https://stackoverflow.com/questions/17223185/how-can-detect-one-string-contain-one-of-several-characters-using-java-regex
        for (int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            for (int j = 0; j < str.length(); j++) {
                if (c == str.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAlphaNumeric(String str) {
    //https://www.thecrazyprogrammer.com/2015/07/how-to-check-string-is-alphanumeric-in-java.html
        return str.matches("[a-zA-Z0-9]+");
    }

    @Override
    public boolean hasSpecialChars(String str) {
        return !str.matches("[a-zA-Z0-9]+");
    }


    //https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValidEmail(String str) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(str);
        return matcher.find();

    }
}
