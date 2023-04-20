package ro.axonsoft.accsecond.helpers;

public class StringHelper {

    public static boolean isDigitAndLengthIs4(String stringToCheck) {
        return stringToCheck.matches("[0-9]+") && stringToCheck.length() == 4;
    }
}
