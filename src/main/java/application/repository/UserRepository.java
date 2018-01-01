package application.repository;

import application.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String>
{
    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from User u where id = ?#{principal.id}")
    User getCurrentUser();
}
