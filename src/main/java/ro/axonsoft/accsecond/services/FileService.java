package ro.axonsoft.accsecond.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.axonsoft.accsecond.constants.ResponseMessageConstants;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.exceptions.NumberFormatException;
import ro.axonsoft.accsecond.helpers.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

@Service
public class FileService {

    private final RoomService roomService;

    private final PersonService personService;

    private final CustomModelMapper customModelMapper;

    @Autowired
    public FileService(RoomService roomService, PersonService personService, CustomModelMapper customModelMapper) {
        this.roomService = roomService;
        this.personService = personService;
        this.customModelMapper = customModelMapper;
    }

    public ResponseEntity<?> parseFile(MultipartFile file) throws Exception {

        if (FileHelper.isNullOrEmptyFile(file)) {
            throw new BadRequestException(new ResponseMessage(ResponseMessageConstants.MISSING_FILE,
                                                ResponseMessageConstants.MISSING_FILE_ERROR_CODE));
        }

        personService.deletePersons();
        roomService.deleteRooms();
        return readFileContent(file);
    }

    private ResponseEntity<?> readFileContent(MultipartFile file) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                HashMap<String, String> inputMap = new HashMap<>();
                String[] lineValues = line.split(",");
                if (!StringHelper.isDigitAndLengthIs4(lineValues[0])) {
                    throw new NumberFormatException(new ResponseMessage(ResponseMessageConstants.NUMBER_FORMAT_ERROR, ResponseMessageConstants.NUMBER_FORMAT_ERROR_CODE));
                }

                RoomDTO roomDTO = null;
                for (String str : lineValues) {
                    if (str.isEmpty()) {
                        continue;
                    }

                    if (StringHelper.isDigitAndLengthIs4(str)) {
                        roomService.createNewRoom(str, null);
                        continue;
                    }

                    roomDTO = customModelMapper.mapRoomEntityToRoomDTO(roomService.findLastAddedRoom());

                    PersonHelper.computePerson(str, inputMap);
                    personService.createAndSavePerson(inputMap.get("firstName"), inputMap.get("lastName"),
                                        inputMap.get("title"), inputMap.get("nameAddition"), inputMap.get("ldapUser"), roomDTO);
                    inputMap.clear();
                }
            }

        }   catch (Exception ex) {
                throw new Exception(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(ResponseMessageConstants.SUCCESS, ResponseMessageConstants.SUCCESS_CODE));
    }
}
