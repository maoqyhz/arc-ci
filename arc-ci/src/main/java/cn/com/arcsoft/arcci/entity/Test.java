package cn.com.arcsoft.arcci.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Fururur
 * @create 2019-11-07-13:37
 */
@Data
@Entity
@Table(name = "test")
@EntityListeners(AuditingEntityListener.class)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @CreatedDate
    private LocalDateTime createTime;
}
