package ro.axonsoft.accsecond.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.axonsoft.accsecond.constants.ResponseMessageConstants;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.RoomEntity;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.helpers.ResponseMessage;
import ro.axonsoft.accsecond.repositories.RoomEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RoomService {

    static Logger log = Logger.getLogger(RoomService.class.getName());

    private final RoomEntityRepository roomEntityRepository;

    private final CustomModelMapper customModelMapper;

    @Autowired
    public RoomService(RoomEntityRepository roomEntityRepository, CustomModelMapper customModelMapper) {
        this.roomEntityRepository = roomEntityRepository;
        this.customModelMapper = customModelMapper;
    }

    public RoomDTO getRoomByRoomNumber(String roomNumber) {
        return customModelMapper.mapRoomEntityToRoomDTO(roomEntityRepository.findRoomByRoomNumber(roomNumber));
    }

    public void deleteRooms() {
        roomEntityRepository.deleteAll();
    }

    public List<RoomDTO> getAllRooms() {
        List<RoomEntity> list = roomEntityRepository.findAll();
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (RoomEntity entity : list) {
            roomDTOList.add(customModelMapper.mapRoomEntityToRoomDTO(entity));
        }
       return roomDTOList;
    }

    public void saveRoom(RoomDTO roomDTO) {
        RoomEntity roomEntity = customModelMapper.mapRoomDtoToRoomEntity(roomDTO);
        roomEntityRepository.save(roomEntity);
    }

    @Transactional
    public void createNewRoom(String roomNumber, List<PersonDTO> personDTOList) {
        RoomEntity checkRoomIfExists = roomEntityRepository.findRoomByRoomNumber(roomNumber);
        if (checkRoomIfExists != null) {
            throw new BadRequestException(new ResponseMessage(ResponseMessageConstants.ROOM_ALREADY_DEFINED, ResponseMessageConstants.ROOM_ALREADY_DEFINED_ERROR_CODE));
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(roomNumber);
        roomDTO.setPeople(personDTOList);
        saveRoom(roomDTO);

    }

    public RoomEntity findLastAddedRoom() {
        return roomEntityRepository.findTopByOrderByIdDesc();
    }
}
