package application.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ModelFromEntityList<F> extends ModelFromEntity<F>
{
    public List<ModelFromEntity<F>> fromEntityList(Collection<F> collection)
    {
        return collection.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
}
