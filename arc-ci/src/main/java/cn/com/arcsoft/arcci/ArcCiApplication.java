package cn.com.arcsoft.arcci;

import cn.com.arcsoft.arcci.repo.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "cn.com.arcsoft.arcci.repo",
		repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication
public class ArcCiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcCiApplication.class, args);
	}
}
