package application.model;

import java.util.*;

public abstract class ModelFromEntityList<F> extends ModelFromEntity<F>
{
    public List<ModelFromEntity<F>> fromEntityList(Collection<F> collection)
    {
        Class<? extends ModelFromEntity> tClass = getClass();
        List<ModelFromEntity<F>> output = new ArrayList<>();
        try
        {
            for (F f : collection)
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
