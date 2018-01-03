package application.repository;

import application.entity.User;
import application.model.account.UserOutput;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String>
{
    Optional<User> findFirstByUsername(String username);

    @Query("select u from User u where id = ?#{principal.id}")
    User getCurrentUser();

    List<UserOutput> findAllByIdNotNull(Pageable pageable);
}
