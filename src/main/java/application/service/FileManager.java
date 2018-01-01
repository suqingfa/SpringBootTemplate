package application.service;

import application.entity.File;
import application.repository.FileRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class FileManager
{
    @Resource
    private FileRepository fileRepository;

    public File save(File file)
    {
        return fileRepository.save(file);
    }

    public Optional<byte[]> find(String id)
    {
        return fileRepository.findFirstById(id)
                .map(File::getData);
    }
}
