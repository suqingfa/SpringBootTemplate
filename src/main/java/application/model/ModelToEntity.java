package application.model;

import javax.validation.UnexpectedTypeException;
import java.util.function.Supplier;

public abstract class ModelToEntity<T> extends ModelBase <T>
{
    // TODO  是否有更优雅的实现？？？
    public final T toEntity(Class<T> clazz)
    {
        try
        {
            T t = copyFields(this, clazz);
            set(t);
            return t;
        }
        catch (Exception e)
        {
            throw new UnexpectedTypeException();
        }
    }
}
