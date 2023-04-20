package ro.axonsoft.accsecond.helpers;

import java.util.HashMap;

public class PersonHelper {

    public static void computePerson(String str, HashMap<String, String> inputMap) {

        //remove white spaces from string
        str = str.trim();
        if (str.matches("[0-9]+")) {
            return;
        }

        String[] nameSplitBySpace = str.split("\\s+");
        switch (nameSplitBySpace.length) {
            case 3 -> {
                String[] middleName ={};
                if (nameSplitBySpace[1].contains("-")) {
                    middleName = nameSplitBySpace[1].split("-");
                    inputMap.put("firstName", nameSplitBySpace[0] + " " + middleName[0]);
                } else {
                    inputMap.put("firstName", nameSplitBySpace[0]);
                }
                inputMap.put("lastName", middleName.length != 0 ? middleName[1] :nameSplitBySpace[1]);
                inputMap.put("ldapUser", nameSplitBySpace[2].replace("(","").replace(")",""));
                inputMap.put("title", "");
                inputMap.put("nameAddition", "");

            }
            case 4 -> {
                if (str.contains("Dr.")) {
                    inputMap.put("title", "Dr.");
                    inputMap.put("firstName", nameSplitBySpace[1]);
                    inputMap.put("lastName", nameSplitBySpace[2]);
                    inputMap.put("ldapUser", nameSplitBySpace[3].replace("(","").replace(")",""));
                    inputMap.put("nameAddition", "");

                } else if (nameSplitBySpace[1].contains("von") || nameSplitBySpace[1].contains("van") || nameSplitBySpace[1].contains("de") ){
                    inputMap.put("firstName", nameSplitBySpace[0]);
                    inputMap.put("nameAddition", nameSplitBySpace[1]);
                    inputMap.put("lastName", nameSplitBySpace[2]);
                    inputMap.put("ldapUser", nameSplitBySpace[3].replace("(","").replace(")",""));
                    inputMap.put("title", "");

                } else {
                    inputMap.put("firstName", nameSplitBySpace[0] + " " +nameSplitBySpace[1]);
                    inputMap.put("lastName", nameSplitBySpace[2]);
                    inputMap.put("ldapUser", nameSplitBySpace[3].replace("(","").replace(")",""));
                    inputMap.put("title", "");
                    inputMap.put("nameAddition", "");
                }

            }
            case 5 -> {
                inputMap.put("title", "Dr.");
                inputMap.put("firstName", nameSplitBySpace[1]);
                inputMap.put("nameAddition", nameSplitBySpace[2]);
                inputMap.put("lastName", nameSplitBySpace[3]);
                inputMap.put("ldapUser", nameSplitBySpace[4].replace("(","").replace(")",""));
            }
            default -> {
            }
        }
    }

}
