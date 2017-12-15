package application.model;

public interface OutputResult
{
    default <T> Output<T> output(Output.Code code, T data)
    {
        return new Output<>(code, data);
    }

    default <T> Output<T> output(Output.Code code)
    {
        return output(code, null);
    }

    default <T> Output<T> output(T data)
    {
        return output(Output.Code.OK, data);
    }

    default Output outputOk()
    {
        return output(Output.Code.OK);
    }

    default Output outputNotLogin()
    {
        return output(Output.Code.NotLogin);
    }

    default Output outputUsernameExist()
    {
        return output(Output.Code.UsernameExist);
    }

    default Output outputError()
    {
        return output(Output.Code.Error);
    }

    default Output outputParameterError()
    {
        return output(Output.Code.ParameterError);
    }
}
