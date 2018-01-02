package application.model;

import javax.validation.UnexpectedTypeException;
import java.util.function.Supplier;

public abstract class ModelToEntity<T>
{
    // TODO Supplier<T> supplier 是否有更优雅的实现？？？
    public final T toEntity(Supplier<T> supplier)
    {
        T t = supplier.get();
        CopySameFields.copySameFields(this, t);
        try
        {
            set(t);
            return t;
        }
        catch (Exception e)
        {
            throw new UnexpectedTypeException();
        }
    }

    protected abstract void set(T t) throws Exception;
}
