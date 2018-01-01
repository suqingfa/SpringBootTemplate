package application.repository;

import application.entity.File;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends BaseRepository<File, String>
{
    default Optional<byte[]> find(String id)
    {
        return findFirstById(id).map(File::getData);
    }
}
