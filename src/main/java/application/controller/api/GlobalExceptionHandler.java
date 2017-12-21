package application.controller.api;

import application.model.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.UnexpectedTypeException;

import static application.model.Output.outputParameterError;

@ControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler({BindException.class, UnexpectedTypeException.class})
    @ResponseBody
    public Output parameterError(Exception e)
    {
        log.info("参数错误 {}", e);
        return outputParameterError();
    }
}
