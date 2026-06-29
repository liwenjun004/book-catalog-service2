package org.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 图书目录服务启动类
 * <p>Spring Boot 应用入口，统一管理所有配置</p>
 * <p>启用的自动配置：
 * <ul>
 *     <li>数据源自动配置（Druid）</li>
 *     <li>MyBatis-Plus 自动配置</li>
 *     <li>Web MVC 自动配置</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
@MapperScan("org.book.infra.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
