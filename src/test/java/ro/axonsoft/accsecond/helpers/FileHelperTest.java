package ro.axonsoft.accsecond.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class FileHelperTest {


    @Test
    public void testIsNullOrEmptyFileShouldPass() {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        boolean checkFile = FileHelper.isNullOrEmptyFile(file);
        assertFalse(checkFile);
    }

    @Test
    public void testIsNullOrEmptyFileShouldFail() {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());
        boolean checkFile = FileHelper.isNullOrEmptyFile(file);
        assertTrue(checkFile);
    }
}
