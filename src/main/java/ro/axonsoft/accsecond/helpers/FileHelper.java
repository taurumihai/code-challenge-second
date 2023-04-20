package ro.axonsoft.accsecond.helpers;

import org.springframework.web.multipart.MultipartFile;

public class FileHelper {

    public static boolean isNullOrEmptyFile(MultipartFile file) {
        return file == null || file.isEmpty();
    }
}
