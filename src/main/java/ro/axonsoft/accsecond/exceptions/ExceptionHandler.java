package ro.axonsoft.accsecond.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ro.axonsoft.accsecond.helpers.ResponseMessage;

@Component
@ControllerAdvice
public class ExceptionHandler {

    private static final String METHOD_NOT_ALLOWED = "Method not allowed !";
    private static final String METHOD_NOT_ALLOWED_ERROR_CODE = "405";
    private static final String GENERIC_ERROR = "Unexpected error occurred !";
    private static final String GENERIC_ERROR_CODE = "405";

    ResponseMessage responseMessage;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseMessage> demoBadRequestCustomException(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> demoNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getResponseMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RoomNotFoundException.class)
    public ResponseEntity<ResponseMessage> demoRoomNotFoundException(RoomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getResponseMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = PersonAlreadyDefinedException.class)
    public ResponseEntity<ResponseMessage> demoPersonAlreadyDefinedException(PersonAlreadyDefinedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseMessage> handleWrongMethodExceptions(Exception e){
        responseMessage = new ResponseMessage(METHOD_NOT_ALLOWED,METHOD_NOT_ALLOWED_ERROR_CODE);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseMessage> handleAllExceptions(Exception e){
        responseMessage = new ResponseMessage(GENERIC_ERROR, GENERIC_ERROR_CODE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    }

}
