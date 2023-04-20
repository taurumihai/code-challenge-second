package ro.axonsoft.accsecond.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
public class StringHelperTest {

    @Test
    public void testStringIsEmptyOrNull() {
        String emptyString = "not a digit string";
        assertFalse(StringHelper.isDigitAndLengthIs4(emptyString));

    }
}
