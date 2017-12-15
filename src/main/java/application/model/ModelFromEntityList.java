package application.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelFromEntityList<F> implements ModelFromEntity<F>
{
    public List<ModelFromEntity<F>> fromEntityList(List<F> fs)
    {
        Class<? extends ModelFromEntity> tClass = getClass();
        List<ModelFromEntity<F>> output = new ArrayList<>();
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
