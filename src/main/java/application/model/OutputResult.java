package application.model;

public interface OutputResult
{
    default <T> Output<T> output(Output.Info info, T data)
    {
        return new Output<>(info, data);
    }

    default <T> Output<T> output(Output.Info info)
    {
        return output(info, null);
    }

    default <T> Output<T> output(T data)
    {
        return output(Output.Info.OK, data);
    }

    default Output outputOk()
    {
        return output(Output.Info.OK);
    }

    default Output outputNotLogin()
    {
        return output(Output.Info.NotLogin);
    }

    default Output outputUsernameExist()
    {
        return output(Output.Info.UsernameExist);
    }

    default Output outputError()
    {
        return output(Output.Info.Error);
    }

    default Output outputParameterError()
    {
        return output(Output.Info.ParameterError);
    }
}
