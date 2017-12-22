package application.controller.api;

import application.model.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.UnexpectedTypeException;

import static application.model.Output.outputError;
import static application.model.Output.outputParameterError;

@ControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Output Exception(Exception e)
    {
        if (e instanceof BindException || e instanceof UnexpectedTypeException)
        {
            log.info("参数错误 {}", e);
            return outputParameterError();
        }

        return outputError();
    }
}
