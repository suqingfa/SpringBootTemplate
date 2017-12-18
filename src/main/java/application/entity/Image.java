package application.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
public class Image
{
    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "mediumblob")
    @Size(min = 10240, max = 10240 * 1024)
    private byte[] data;
}
