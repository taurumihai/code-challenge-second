package ro.axonsoft.accsecond.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.axonsoft.accsecond.entities.PersonEntity;

@Repository
public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findPersonByLdapUser(String ldapUser);
}
