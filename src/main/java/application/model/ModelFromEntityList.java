package application.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ModelFromEntityList<F> extends ModelFromEntity<F>
{
    default List<ModelFromEntity<F>> fromEntityList(List<F> list)
    {
        Function<F, ModelFromEntity<F>> function = (f) ->
        {
            try
            {
                Class<? extends ModelFromEntity> thisClass = getClass();
                ModelFromEntity inst = thisClass.newInstance();
                return inst.fromEntity(f);
            }
            catch (Exception ignored)
            {
            }
            return null;
        };
        return list.stream().map(function).collect(Collectors.toList());
    }
}
