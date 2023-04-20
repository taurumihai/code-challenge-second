package ro.axonsoft.accsecond.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.axonsoft.accsecond.helpers.ResponseMessage;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NumberFormatException extends RuntimeException{

    private final ResponseMessage responseMessage;

    public NumberFormatException(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }
}
