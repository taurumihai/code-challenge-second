package ro.axonsoft.accsecond.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ResponseMessageTest {

    @Test
    public void shouldCreateNewResponseMessageObject() {
        ResponseMessage responseMessage = new ResponseMessage("Test passed successfully!", "1");
        assertEquals("Test passed successfully!", responseMessage.getMessage());
        assertEquals("1", responseMessage.getStatusCode());

        ResponseMessage responseMessage2 = new ResponseMessage();
        responseMessage2.setMessage("Test set method");
        responseMessage2.setStatusCode("404");
        assertEquals("Test set method", responseMessage2.getMessage());
        assertEquals("404", responseMessage2.getStatusCode());
    }
}
