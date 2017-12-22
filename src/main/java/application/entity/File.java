package application.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
public class File implements Serializable
{
    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "bytea")
    @Size(min = 10240, max = 10 * 1024 * 1024)
    private byte[] data;
}
