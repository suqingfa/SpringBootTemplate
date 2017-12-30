package application.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
public class File implements Serializable
{
    @Id
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updateTime;

    @Column(nullable = false, columnDefinition = "bytea")
    @Size(min = 10240, max = 10 * 1024 * 1024)
    private byte[] data;
}
