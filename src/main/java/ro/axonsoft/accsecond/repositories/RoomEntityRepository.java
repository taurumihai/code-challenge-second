package ro.axonsoft.accsecond.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.axonsoft.accsecond.entities.RoomEntity;

@Repository
public interface RoomEntityRepository extends JpaRepository<RoomEntity, Long> {

    RoomEntity findRoomByRoomNumber(String roomNumber);

    RoomEntity findTopByOrderByIdDesc();
}
