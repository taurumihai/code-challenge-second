package ro.axonsoft.accsecond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.axonsoft.accsecond.constants.ResponseMessageConstants;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.helpers.FileHelper;
import ro.axonsoft.accsecond.helpers.ResponseMessage;
import ro.axonsoft.accsecond.services.FileService;
import ro.axonsoft.accsecond.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final FileService fileService;

    private final RoomService roomService;

    @Autowired
    public RoomController(FileService fileService, RoomService roomService) {
        this.fileService = fileService;
        this.roomService = roomService;
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

}
