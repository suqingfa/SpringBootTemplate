package application.service;

import application.entity.File;
import application.repository.FileRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileManager
{
    @Resource
    private FileRepository fileRepository;

    public File save(File file)
    {
        return fileRepository.save(file);
    }

    public File save(String id, byte[] bytes)
    {
        File file = new File();
        file.setId(id);
        file.setData(bytes);
        return save(file);
    }

    public byte[] findOne(String id)
    {
        File file = fileRepository.findOne(id);
        if (file == null)
        {
            return null;
        }

        return file.getData();
    }
}
