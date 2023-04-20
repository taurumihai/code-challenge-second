package ro.axonsoft.accsecond.constants;

public final class ResponseMessageConstants {

    /*
    BadRequestException message and error code for file operations
    */
    public static final String MISSING_FILE  = "File from request is missing or it's content is empty!";
    public static final String MISSING_FILE_ERROR_CODE  = "5";

    /*
    NumberFormatException message and error code for file operations
    */
    public static final String NUMBER_FORMAT_ERROR = "Room number does not match the criteria. Expected a number!";
    public static final String NUMBER_FORMAT_ERROR_CODE = "4";

    /*
    BadRequestException message and error code for person already exists operations
    */
    public static final String PERSON_ALREADY_DEFINED = "Person already exists in current file.";
    public static final String PERSON_ALREADY_DEFINED_ERROR_CODE = "3";

    /*
    BadRequestException message and error code for room already exists operations
    */
    public static final String ROOM_ALREADY_DEFINED = "Room already exists. Please specify another room number.";
    public static final String ROOM_ALREADY_DEFINED_ERROR_CODE = "2";

    /*
    * Success message while importing files
    * */
    public static final String SUCCESS = "Operation completed with no errors!";
    public static final String SUCCESS_CODE = "1";

    /*
     * Room not found messages
     * */
    public static final String ROOM_NOT_FOUND = "Room not found!";
    public static final String ROOM_NOT_FOUND_ERROR_CODE = "5";
}
