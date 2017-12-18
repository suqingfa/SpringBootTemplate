package application.repository;

import application.entity.Authority;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "Authority")
public interface AuthorityRepository extends JpaRepository<Authority, String>
{
    boolean existsByRole(Authority.Role role);

    @Cacheable
    Authority findByRole(Authority.Role role);

    @CachePut
    @Override
    Authority save(Authority authority);
}
