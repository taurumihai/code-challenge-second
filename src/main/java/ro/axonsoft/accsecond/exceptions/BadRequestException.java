package ro.axonsoft.accsecond.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.axonsoft.accsecond.helpers.ResponseMessage;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private final ResponseMessage responseMessage;

    public BadRequestException(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }
}
