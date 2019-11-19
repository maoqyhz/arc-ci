package cn.com.arcsoft.arcci.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Fururur
 * @create 2019-11-08-9:36
 */
@Data
@Entity
@Table(name="project")
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String gitRepo;
    private String lang;
    private String env;
    private String deployPath;
    private String remark;
    private String serverIp;
    private String serverType;
    private String serverPort;
    private String serverUsername;
    private String serverRemark;
    @CreatedDate
    private LocalDateTime createTime;
    private String createBy;
}
