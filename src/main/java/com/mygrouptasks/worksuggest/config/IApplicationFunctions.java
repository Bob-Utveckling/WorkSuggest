package com.mygrouptasks.worksuggest.config;

public interface IApplicationFunctions {
    public boolean hasOneOfChars(String getString, String charsToCheck);
    public boolean isAlphaNumeric(String getString);
    public boolean hasSpecialChars(String getString);
    public boolean isValidEmail(String emailStr);
}
