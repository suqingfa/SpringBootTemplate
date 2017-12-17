package application.scheduled;

import application.entity.Authority;
import application.repository.AuthorityRepository;
import application.util.GenerateRandomSequence;
import application.util.GetLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Component
public class AsyncInsert implements GetLogger
{
    @Autowired
    GenerateRandomSequence randomSequence;
    @Resource
    private AuthorityRepository repository;

    @Async
    public Future<Authority> doInsertAuthority()
    {
        randomSequence.setLength(10);
        Authority authority = new Authority();
        authority.setAuthority(randomSequence.getRandomUppercaseNumber());
        authority = repository.save(authority);
        return new AsyncResult<>(authority);
    }
}
