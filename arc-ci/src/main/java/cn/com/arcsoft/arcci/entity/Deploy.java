package cn.com.arcsoft.arcci.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Fururur
 * @create 2019-11-08-15:47
 */
@Data
@Entity
@Table(name="deploy")
@EntityListeners(AuditingEntityListener.class)
public class Deploy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deployType;
    private String typeRef;
    private String deployRemark;
    private String state;
    private Long projectId;
    private String envVariable;
    private String deployProject;
    @CreatedDate
    private LocalDateTime createTime;
    private String createBy;
}
