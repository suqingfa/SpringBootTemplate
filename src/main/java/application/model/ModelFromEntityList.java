package application.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelFromEntityList<F> extends ModelFromEntity<F>
{
    public List<? extends ModelFromEntity<F>> fromEntityList(List<F> fs)
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
