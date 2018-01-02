package application.model;

public abstract class ModelFromEntity<F>
{
    public final ModelFromEntity<F> fromEntity(F f)
    {
        CopySameFields.copySameFields(f, this);
        set();
        return this;
    }

    protected abstract void set();
}
