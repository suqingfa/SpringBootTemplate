package application.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelFromEntityList<F, T extends ModelFromEntityList<F, T>> implements ModelFromEntity<F, T>
{
    public List<T> fromEntityList(List<F> fs)
    {
        Class<T> tClass = (Class<T>) getClass();
        List<T> output = new ArrayList<>();
        try
        {
            for (F f : fs)
            {
                output.add(tClass.newInstance().fromEntity(f));
            }
        }
        catch (ReflectiveOperationException e)
        {
        }
        return output;
    }
}
