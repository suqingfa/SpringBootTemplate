package application.controller.api;

import application.model.Output;
import org.springframework.web.bind.annotation.*;

import static application.model.Output.outputError;

@ControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Output jsonErrorHandler()
    {
        return outputError();
    }
}
