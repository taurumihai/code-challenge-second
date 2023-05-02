package ro.axonsoft.accsecond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.axonsoft.accsecond.constants.ResponseMessageConstants;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.exceptions.NumberFormatException;
import ro.axonsoft.accsecond.exceptions.PersonAlreadyDefinedException;
import ro.axonsoft.accsecond.exceptions.RoomNotFoundException;
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.helpers.FileHelper;
import ro.axonsoft.accsecond.helpers.ResponseMessage;
import ro.axonsoft.accsecond.helpers.StringHelper;
import ro.axonsoft.accsecond.services.FileService;
import ro.axonsoft.accsecond.services.PersonService;
import ro.axonsoft.accsecond.services.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final FileService fileService;

    private final RoomService roomService;

    private final PersonService personService;

    private final CustomModelMapper customModelMapper;

    @Autowired
    public RoomController(FileService fileService, RoomService roomService, PersonService personService, CustomModelMapper customModelMapper) {
        this.fileService = fileService;
        this.roomService = roomService;
        this.personService = personService;
        this.customModelMapper = customModelMapper;
    }

    @PostMapping("/import")
    public ResponseEntity<?> postApiImport(@RequestParam("uploadedFile") MultipartFile file) throws Exception {

        if (FileHelper.isNullOrEmptyFile(file)) {
            throw  new BadRequestException(new ResponseMessage("File is empty!", "405"));
        }

        return fileService.parseFile(file);
    }

    @GetMapping(value = {"/room/{roomId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoomByRoomId(@PathVariable(value = "roomId", required = false) String roomId) throws Exception {

        RoomDTO roomDTO = roomService.getRoomByRoomNumber(roomId);
        if (roomDTO == null) {
            return new ResponseEntity<>(new ResponseMessage(ResponseMessageConstants.ROOM_NOT_FOUND, ResponseMessageConstants.ROOM_NOT_FOUND_ERROR_CODE), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/room"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRooms() {
        List<RoomDTO> roomDTOList = roomService.getAllRooms();
        return new ResponseEntity<>(roomDTOList, HttpStatus.OK);
    }

    @PutMapping(value = "/room/update/{roomNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoom(@RequestBody RoomEntity roomEntity, @PathVariable String roomNumber) {

        if (!StringHelper.isDigitAndLengthIs4(roomNumber)) {
            throw new NumberFormatException(new ResponseMessage(ResponseMessageConstants.ROOM_NUMBER_NOT_OK, ResponseMessageConstants.ROOM_NUMBER_NOT_OK_ERROR_CODE));
        }

        RoomDTO findRoomByRoomNumber = roomService.getRoomByRoomNumber(roomNumber);
        if (findRoomByRoomNumber == null) {
            throw new RoomNotFoundException(new ResponseMessage(ResponseMessageConstants.ROOM_NOT_FOUND, ResponseMessageConstants.ROOM_NOT_FOUND_ERROR_CODE));
        }

        for (PersonEntity personEntity : roomEntity.getPeople()) {
            PersonDTO personDTO = personService.findPersonByLdapUser(personEntity.getLdapUser());
            if (personDTO == null) {
                PersonDTO saveNewPerson = customModelMapper.mapPersonEntityToPersonDTO(personEntity);
                personService.savePerson(saveNewPerson, findRoomByRoomNumber);
                findRoomByRoomNumber.getPeople().add(saveNewPerson);
            }
        }
        roomService.saveRoom(findRoomByRoomNumber);


        return new ResponseEntity<>(findRoomByRoomNumber, HttpStatus.OK);

    }

}
