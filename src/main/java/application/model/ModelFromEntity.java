package application.model;

public abstract class ModelFromEntity<F>
{
    public abstract ModelFromEntity<F> fromEntity(F f);
}
