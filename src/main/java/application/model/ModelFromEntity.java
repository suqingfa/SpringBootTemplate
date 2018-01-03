package application.model;

import javax.validation.UnexpectedTypeException;

public abstract class ModelFromEntity<F> extends ModelBase<ModelFromEntity>
{
    public final ModelFromEntity fromEntity(F f)
    {
        try
        {
            ModelFromEntity result = copyFields(f, getClass());
            set(result);
            return result;
        }
        catch (Exception e)
        {
            throw new UnexpectedTypeException();
        }
    }
}
