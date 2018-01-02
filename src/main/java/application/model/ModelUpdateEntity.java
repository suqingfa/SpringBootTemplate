package application.model;

public abstract class ModelUpdateEntity<E>
{
    public final void update(E e)
    {
        CopySameFields.copySameFields(this, e);
        set(e);
    }

    protected abstract void set(E e);
}
