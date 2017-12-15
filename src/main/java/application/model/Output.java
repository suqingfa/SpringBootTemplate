package application.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Output<T>
{
	private int code;
	private Code codeInfo;
	private T data;

	public Output(Code code, T data)
	{
		this.codeInfo = code;
		this.code = code.getCode();
		this.data = data;
	}

	public enum Code
	{
		OK(0),
		Error(200),
		;

		@Getter
		private final int code;

		Code(int code)
		{
			this.code = code;
		}
	}
}
