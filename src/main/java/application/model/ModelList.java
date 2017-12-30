package application.model;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ModelList<F> implements ModelFromEntity<F>
{
    public final List<ModelFromEntity<F>> fromEntityList(List<F> list)
    {
        return list.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    private ModelFromEntity<F> apply(F f)
    {
        try
        {
            Class<? extends ModelFromEntity> thisClass = getClass();
            @SuppressWarnings("unchecked")
            ModelFromEntity<F> inst = thisClass.newInstance();
            return inst.fromEntity(f);
        }
        catch (Exception ignored)
        {
        }
        return null;
    }
}
