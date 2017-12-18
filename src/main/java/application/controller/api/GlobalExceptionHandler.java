package application.controller.api;

import application.model.Output;
import application.model.OutputResult;
import application.util.GetLogger;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
public class GlobalExceptionHandler implements OutputResult, GetLogger
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Output jsonErrorHandler()
    {
        return outputError();
    }
}