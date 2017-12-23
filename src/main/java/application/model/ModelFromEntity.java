package application.model;

public interface ModelFromEntity<F>
{
    ModelFromEntity<F> fromEntity(F f);
}
