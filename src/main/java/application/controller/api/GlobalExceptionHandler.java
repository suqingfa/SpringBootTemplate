package application.controller.api;

import application.model.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static application.model.Output.outputError;

@ControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Output jsonErrorHandler(Exception e)
    {
        log.warn("错误", e);
        return outputError();
    }
}
