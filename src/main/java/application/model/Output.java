package application.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Output<T>
{
	private int code;
	private Info info;
	private T data;

	public Output(Info info, T data)
	{
		this.info = info;
		this.code = info.getCode();
		this.data = data;
	}

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
}
