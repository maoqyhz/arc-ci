package cn.com.arcsoft.arcci.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Fururur
 * @create 2019-11-15-9:47
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String avatar;
}
