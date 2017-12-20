package application.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Output<T>
{
    private int code;
    private Info info;
    private T data;

    public enum Info
    {
        OK(0),
        NotLogin(100),
        UsernameExist(101),
        Error(200),
        ParameterError(201),
        ;

        @Getter
        private final int code;

        Info(int code)
        {
            this.code = code;
        }
    }

    private Output(Info info, T data)
    {
        this.info = info;
        this.code = info.getCode();
        this.data = data;
    }

    private static <T> Output<T> output(Output.Info info)
    {
        return new Output<>(info, null);
    }

    public static <T> Output<T> outputOk(T data)
    {
        return new Output<>(Output.Info.OK, data);
    }

    public static Output outputOk()
    {
        return output(Output.Info.OK);
    }

    public static Output outputNotLogin()
    {
        return output(Output.Info.NotLogin);
    }

    public static Output outputUsernameExist()
    {
        return output(Output.Info.UsernameExist);
    }

    public static Output outputError()
    {
        return output(Output.Info.Error);
    }

    public static Output outputParameterError()
    {
        return output(Output.Info.ParameterError);
    }
}
