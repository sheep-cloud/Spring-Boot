#  一、Sping Boot 入门

## 1、Spring boot 简介

> 简化Spring应用开发的一个框架；
>
> 整个Spring技术站的一个打整合；
>
> J2EE开发的一站式解决方案。

## 2、微服务

2014， martin fowler

为服务：架构风格

一个应用应该是一组小型服务；可以通过HTTP的方式进行互通；



每一个功能元素最终都是一个可独立替换和独立升级的软件单元；

[详细参照微服务文档][https://martinfowler.com/]

环境约束：

jdk：1.8，spring boot推荐1.7以上

maven：3.x

Intellij IDEA 2017

Spring-Boot 1.5.9.RELEASE

## 3、环境准备

### 1、Maven设置：

给maven的settings.xml配置文件的profiles标签添加

```
	<profile>
		<id>jdk-1.8</id>
		<activation>
			<activeByDefault>true</activeByDefault>
			<jdk>1.8</jdk>
		</activation>
		<properties>  
			<maven.compiler.source>1.8</maven.compiler.source>
			<maven.compiler.target>1.8</maven.compiler.target> 
			<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
		</properties>    
	</profile>
	<!-- eclipse下载jar包源码和doc -->
	<profile>
		<id>downloadSources</id>
		<properties>
			<downloadSources>true</downloadSources>
			<downloadJavadocs>true</downloadJavadocs>
		</properties>
	</profile>
  </profiles>
```

### 2、IDEA设置

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqdrmdrnnpj30st0j0ta9.jpg)

##  4、Spring Boot HeeloWorld

一个功能

浏览器发送hello请求，服务器接收请求并处理，响应hello World字符串；

### 1、创建一个Maven工程；（jar）

### 2、导入依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu</groupId>
    <artifactId>spring-boot-01-helloworld</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

### 3、编写主程序

```
package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个SpringBoot应用
 */
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {
        // Spring 应用启动
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}

```

### 4、编写相关的Controller、Service

```
package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }
}

```

### 5、运行主程序测试

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqdswx0sm9j308p02kq2r.jpg)

### 6、简化部署

```
    <!--这个插件，可以将应用打包成一个可执行的jar包-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

将这个应用打成jar包，直接使用java -jar命令执行

## 5、Hello World探究

### 1、POM文件

#### 1、父项目

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    
    // 他的父项目是
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-dependencies</artifactId>
		<version>1.5.9.RELEASE</version>
		<relativePath>../../spring-boot-dependencies</relativePath>
	</parent>
	// 他来真正管理Spring Boot应用里面的所有依赖版本
```

Spring Boot的版本仲裁中心；

以后我们导入依赖默认是不需要写版本；（没有在dependencies里面管理的依赖自然需要声明版本号）

#### 2、导入的依赖

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

**spring-boot-starter**-`web`:

spring-boot-starter：场景启动器；帮我们导入了web模块正常运行所依赖的组件；



Spring Boot将所有的功能场景都抽取出来，做成一个个的starts（启动器），只需要在项目里面引入这些

starter相关场景的所有依赖都导入进来。要用什么功能就导入什么场景的启动器。

### 2、主程序类，主入口类

```
/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个SpringBoot应用
 */
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {
        // Spring 应用启动
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}

```

**@SpringBootApplication**： Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用。

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```

**@SpringBootApplication**：Spring Boot的配置类；

​	标注在某个类上，表示这个Spring Boot的配置类

​	**@Configuration**：配置类上来标注这个注解；

​		配置类 —— 配置文件；配置类也是容器中的一个组件，`@Component`

​	**@EnableAutoConfiguration**：开启自动配置功能；

​		以前我们需要配置的东西，Spring Boot帮我们自动配置；`@EnableAutoConfiguration`告诉SpringBoot开启自动配置功能；这样自动配置才能生效。

```
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

**@AutoConfigurationPackage**：自动配置包；

```
@Import(AutoConfigurationPackages.Registrar.class)
```

Spring的底层注解@Import，给容器中导入一个组件；导入的组件由AutoConfigurationPackages.Registrar.class

**将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器。**

​	**@Import**(EnableAutoConfigurationImportSelector.class)

​		给容器中导入组件？

​		**EnableAutoConfigurationImportSelector**：导入哪些组件的选择器

​		将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；

​		会给容器中导入非常多的自动配置类（xxxAutoConfiguation）；就是给容器中导入这个场景需要的所有组件，并配置好这些组件。

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqdudiojagj31320pbq7q.jpg)

有了自动配置类，免区了我们手动编写配置注入功能组件等工作。

​	SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, ClassLoader beanClassLoader);

```
public abstract class SpringFactoriesLoader {

   private static final Log logger = LogFactory.getLog(SpringFactoriesLoader.class);

   /**
    * The location to look for factories.
    * <p>Can be present in multiple JAR files.
    */
   public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```

SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值；

将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作；以前需要我们自己配置的东西，自动配置类都帮我们做了；

J2EE的整体整合解决方案和自动配置都在

`E:\maven\repository\org\springframework\boot\spring-boot-autoconfigure\1.5.9.RELEASE\spring-boot-autoconfigure-1.5.9.RELEASE.jar!\org\springframework\boot\autoconfigure` 里

Spring注解版

## 6、使用Spring Initializer快速创建项目

IDE都支持使用Spring的创建想到快速创建一个Spring Boot项目；

选择我们需要的模块；想到会联网创建Spring Boot项目。

默认生成的Spring Boot项目

- 主程序已经生成好了，我们只需要自己的逻辑
- resources文件夹目录结构
  - static：保存所有的静态资源；js, css, images；
  - templates：保存所有的模版页面；（Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSPN页面）；可以使用模版引擎（freemarker、thymeleaf）
  - application.properties：Spring Boot应用的配置文件；可以修改一些配置信息。

# 二、配置文件

## 1、配置文件

SpringBoot使用一个全局的配置文件，配置文件名是固定的；

* application.properties
* application.yml

配置文件的作用：修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好了；

yml - YAML(YAML Ain't Markup Language)

标记语言：

​	以前的配置文件；大多都使用的是**xxx.xml**文件

​	YAML：以数据为中心

​	YAML：配置例子

```
server:
  port: 8081
```

​	XML：

```
<server>
	<port>8081</port>
</server>
```

## 2、YAML语法

#### 1、基本语法

k:(空格)v：表示一对键值对（空格必须有）；

以空格的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的。

```
server:
  port: 8081
  path: /hello
```

属性和值也是大小写敏感；

#### 2、值的写法

- 字面量：普通的值（数字，字符串，布尔）

  k: v：字面直接来写；

  ​	字符串默认不用加上单引号或者双引号；

  ​	""：双引号，不会转义字符串里里面的特殊字符，特殊字符会作为本身想表示的意思

  ​			name: "zhangsan \n list"；输出：zhangsan 换行 list

  ​	''：单引号，会转义特殊字符，特殊字符最终只是一个普通的字符串数据

  ​			name: 'zhangsan \n list'；输出：zhangsan \n list

- 对象、Map（属性和值）（键值对）

  k: v：对象还是k: v的方式，在下一行来写对象的属性和值的关系，注意缩进

  ```
  friends:
    lastName: zhangsan
    age: 20
  ```

  行内写法：

  ```
  firends: {lastname: zhangsan, age: 18}
  ```

- 数组（List，Set）

  用- 值表示数组中的一个元素

  ```
  pets:
    - cat
    - dog
    - pig
  ```

  行内写法

  ```
  pets: [cat, dog, pig]
  ```

## 3、配置文件值注入

配置文件

```
server:
  port: 8081

person:
  lastName: zhangsan
  age: 18
  boss: false
  birth: 2017/12/12
  maps: {k1: v1, k2: 12}
  list:
    - lisi
    - zhaoliu
  dog:
    name: 小狗
    age: 2
```

```
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中所有属性和配置文件相关的配置进行绑定；
 *  prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能使用提供的ConfigurationProperties功能
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String, Object> maps;
    private List<Object> lists;

    private Dog dog;
```

我们可以导入配置文件处理器，以后编写配置就有提示了

```
        <!--导入配置文件处理器，配置文件进行绑定，就会有提示-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```

## 4、@Value获取值

|            | @ConfigurationProperties | @Value            |
| ---------- | ------------------------ | ----------------- |
| 功能         | 批量注入配置文件中的属性             | 一个一个指定            |
| 松散绑定（松散语法） | 支持,person.last-name      | 必须person.lastName |
| SpEL       | 不支持                      | 支持                |
| JSR303数据校验 | 支持                       | 不支持               |

配置文件yml还是properteis他们都能获取到值；

如果说，我们只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value；

如果说，我们专门编写了一个javaBean来和配置文件进行映射，我们就直接使用@ConfigurationProperties；

## 5、@PropertySource

**@PropertySource**：加载指定的配置文件；

```
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 *
 * <pre>
 * ConfigurationProperties：告诉SpringBoot将本类中所有属性和配置文件相关的配置进行绑定；
 *
 *  prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能使用提供的ConfigurationProperties功能
 *
 * @ConfigurationProperties(prefix = "person")  默认从全局配置文件中获取值；
 * </pre>
 *
 * @author: colg
 */
@PropertySource(value = {"classpath:person.properties"})
@Component
//@ConfigurationProperties(prefix = "person")
//@Validated
public class Person {

    /**
     * <bean class="com.atguigu.springboot.bean.Person">
     *      <property name="lastName" value="字面量">从环境变量，配置文件中获取值/#{SpEL}</property>
     * </bean>
     */

    /**
     * lastName必须是邮箱格式
     * */
    @Value("#{person.lastName}")
    /**
     * @Email
     */
    private String lastName;
    @Value("#{11*2}")
    private Integer age;
    @Value("true")
    private Boolean boss;
    @Value("${person.birth }")
    private Date birth;
```

**@ImportResource**：导入Spring的配置文件，让配置文件里面的内容生效

Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；

想让Spring的配置文件生效，加载进来；**@ImportResource**标注在一个配置类上

```
@ImportResource(locations = {"classpath:beans.xml"})
导入Spring的配置文件让其生效
```



SpringBoot推荐给容器中添加组件的方式；推荐使用全注解的方式

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="helloService" class="com.atguigu.springboot.service.HelloService"></bean>
</beans>
```

1、配置类 === Spring 配置文件

2、使用@Bean给容器中添加组件

```
/**
 * @Configuration：指明当前类是一个配置类；就是来替代之前的Spring配置文件
 *
 * 在配置文件中用<bean></bean>便签添加组件
 *
 * @author: colg
 */
@Configuration
public class MyAppConfig {

    /**
     * 将方法的返回值添加到容器中，容器中这个组件默认的id就是方法名
     * @return
     */
    @Bean
    public HelloService helloService() {
        System.out.println("配置类@bean给容器添加组件了");
        return new HelloService();
    }
}

```

## 6、配置文件占位符

### 1、随机数

```age: ${random.int(20,30)}
age: ${random.int(20,30)}
```

### 2、占位符

占位符获取之前配置的值，如果没有可是用：指定默认值

```
person:
  age: ${random.int(20,30)}
  boss: false
  birth: 2017/12/12
  maps: {k1: v1, k2: 12}
  lists: [list, zhaoliu]
  dog:
    name: 小狗
    age: ${person.age>20:3}
  last-name: zhangsan
```

## 7、Profile

### 1、多Profile文件

我们在主配置文件编写的时候，文件名可以是 **application-{profile}.yml**

默认使用application.yml的配置

### 2、 yml支持多文档块方式

```
# Document 1 文档块
server:
  port: 8081
spring:
  profiles:
    active: dev

---
# Document 2 文档块
server:
  port: 8085
spring:
  profiles: dev

---
# Document 3 文档块
server:
  port: 8086
spring:
  profiles: prod
```



### 3、 激活指定profile

1、在配置文件中指定

```
spring.profiles.active=dev
```

## 8、自动配置原理

配置文件到底能写什么？怎么写？自动配置原理：

[Spring Boot 自动配置属性参照 官方](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

### 1、自动配置原理：

1）、Spring Boot启动的时候加载主配置类，开启了自动配置功能 **@EnableAutoConfiguration**

2）、**@EnableAutoConfiguration**作用：

- 利用@Import(**EnableAutoConfigurationImportSelector**.class)给容器中导入组件？

- 可以查看AutoConfigurationImportSelector类中selectImports()方法的内容

- List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes); 获取候选的配置

  - ```
    SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
    扫描所有jar包类路径下 META-INF/spring.factories
    把扫描到的这些文件的内容包装成properties对象
    从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把他们添加在容器中
    ```

**将类路径下 META-INF/spring.factories 里面配置的所有 EnableAutoConfiguration的值加入到了容器中；**

```
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mobile.DeviceResolverAutoConfiguration,\
org.springframework.boot.autoconfigure.mobile.DeviceDelegatingViewResolverAutoConfiguration,\
org.springframework.boot.autoconfigure.mobile.SitePreferenceAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.reactor.ReactorAutoConfiguration,\
org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration,\
org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration,\
org.springframework.boot.autoconfigure.social.LinkedInAutoConfiguration,\
org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration
```

每一个这样的 xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置。

3）、每一个自动配置类进行自动配置功能；

4）、以**HttpEncodingAutoConfiguration**（Http编码自动配置）为例，解释自动配置原理；

```
@Configuration	// 表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
@EnableConfigurationProperties(HttpEncodingProperties.class) // 启用指定类
// Configurationes功能；将配置文件中的值和HttpEncodingProperties绑定起来；
// HttpEncodingProperties加入到ioc容器中
@ConditionalOnWebApplication // Spring底层@Conditional注解（Spring注解版），根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效；	判断当前应用是否是web应用，如果是，当前配置类生效
@ConditionalOnClass(CharacterEncodingFilter.class)	// 判断当前项目有没有这个类
// CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)	// 判断配置文件中是否存在某个配置 spring.http.encodint.enabled；如果不存在，判断也是成立的
// 即使配置文件中不配置spring.http.encodin=true，也是生效的
public class HttpEncodingAutoConfiguration {

	// 它已经和SpringBoot的配置文件映射了
	private final HttpEncodingProperties properties;
	
	// 只有一个有参构造器的情况下，参数的值就会从容器中拿
	public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
		this.properties = properties;
	}

	@Bean	// 给容器中添加一个组件，这个组件的某些值需要从properties中获取
	@ConditionalOnMissingBean(CharacterEncodingFilter.class)
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
		return filter;
	}
```

根据当前不同的条件判断，决定这个配置类是否生效？

一旦这个配置类生效，这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的。

5）、所有在配置文件中能配置的属性都是在xxxProperties类中封装着；配置文件能配置什么就可以参照某个功能对应的这个属性类

```
@ConfigurationProperties(prefix = "spring.http.encoding")	// 从配置文件中获取指定的值和bean的属性进行绑定
public class HttpEncodingProperties {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
```

### 2、细节

1、@Conditional派生注解（Spring注解版原生的@Conditional作用）

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置里面的所有内容才生效。

@Conditional扩展
|@Conditional|扩展注解 作用（判断是否满足当前指定条件）|
|----|----|
|@ConditionalOnJava| 系统的java版本是否符合要求|
|@ConditionalOnBean| 容器中存在指定Bean；|
|@ConditionalOnMissingBean| 容器中不存在指定Bean；|
|@ConditionalOnExpression| 满足SpEL表达式指定|
|@ConditionalOnClass| 系统中有指定的类|
|@ConditionalOnMissingClass| 系统中没有指定的类|
|@ConditionalOnSingleCandidate| 容器中只有一个指定的Bean，或者这个Bean是首选Bean|
|@ConditionalOnProperty| 系统中指定的属性是否有指定的值|
|@ConditionalOnResource| 类路径下是否存在指定资源文件|
|@ConditionalOnWebApplication| 当前是web环境|
|@ConditionalOnNotWebApplication| 当前不是web环境|
|@ConditionalOnJndi| JNDI存在指定项|

**自动配置类只有在一定条件下才会生效**

```
# 开启SpringBoot的debug模式
debug: true

=========================
AUTO-CONFIGURATION REPORT
=========================


Positive matches:
-----------------

   DispatcherServletAutoConfiguration matched:
      - @ConditionalOnClass found required class 'org.springframework.web.servlet.DispatcherServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)

   DispatcherServletAutoConfiguration.DispatcherServletConfiguration matched:
      - @ConditionalOnClass found required class 'javax.servlet.ServletRegistration'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - Default DispatcherServlet did not find dispatcher servlet beans (DispatcherServletAutoConfiguration.DefaultDispatcherServletCondition)

   DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration matched:
      - @ConditionalOnClass found required class 'javax.servlet.ServletRegistration'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - DispatcherServlet Registration did not find servlet registration bean (DispatcherServletAutoConfiguration.DispatcherServletRegistrationCondition)

   DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration matched:
      - @ConditionalOnBean (names: dispatcherServlet; types: org.springframework.web.servlet.DispatcherServlet; SearchStrategy: all) found beans 'dispatcherServlet', 'dispatcherServlet' (OnBeanCondition)

   EmbeddedServletContainerAutoConfiguration matched:
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)

   EmbeddedServletContainerAutoConfiguration.EmbeddedTomcat matched:
      - @ConditionalOnClass found required classes 'javax.servlet.Servlet', 'org.apache.catalina.startup.Tomcat'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnMissingBean (types: org.springframework.boot.context.embedded.EmbeddedServletContainerFactory; SearchStrategy: current) did not find any beans (OnBeanCondition)

   ErrorMvcAutoConfiguration matched:
      - @ConditionalOnClass found required classes 'javax.servlet.Servlet', 'org.springframework.web.servlet.DispatcherServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)

   ErrorMvcAutoConfiguration#basicErrorController matched:
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ErrorController; SearchStrategy: current) did not find any beans (OnBeanCondition)

   ErrorMvcAutoConfiguration#errorAttributes matched:
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ErrorAttributes; SearchStrategy: current) did not find any beans (OnBeanCondition)

   ErrorMvcAutoConfiguration.DefaultErrorViewResolverConfiguration#conventionErrorViewResolver matched:
      - @ConditionalOnBean (types: org.springframework.web.servlet.DispatcherServlet; SearchStrategy: all) found bean 'dispatcherServlet'; @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.DefaultErrorViewResolver; SearchStrategy: all) did not find any beans (OnBeanCondition)

```



## 9、精髓

- **SpringBoot启动会加载大量的自动配置类；**
- **我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；**
- **我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）**
- **给容器中自动配置类添加组件的时候，会从properties类中获取某些属性，我们就可以在配置文件中指定这些属性的值；**

xxxAuthConfiguration：自动配置类；

给容器中添加组件

xxxProperties：封装配置文件中相关属性；

# 三、日志

## 1、日志框架

小张；开发一个大型系统；

​	1、System.out.println("")；将关键数据打印在控制台；去掉？写在一个文件？

​	2、框架来记录系统的一些运行时信息；日志框架；zhanglogging.jar；

​	3、高大上的几个功能？异步模式？自动归档？xxx？zhanglogging-good.jar？

​	4、将以前框架爱卸下来？换上新的框架，重新修改之前相关的API；zhanglogging-prefect.jar？

​	5、JDBC---数据库框架；

​		写了一个统一的接口层；日志门面（日志的一个抽象层）；logging-abstract.jar

​		给项目中导入具体的日志实现就行了；我们之前的日志框架都是实现的抽象层；



市面上的日志框架；

JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j...

| 日志门面（日志的抽象层）                                     | 日志实现                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **~~JCL（Jakarta Commons Logging）~~**<br />SLF4j（Simple Logging Facade for Java）<br />~~**jboss-logging**~~ | **~~Log4j~~**<br />**~~JUL（java.util.logging）~~**<br />Log4j2<br />Logback |

左边选一个门面（抽象层），右边来选一个实现；

日志门面：SLF4j；

日志实现：Logback；



SpringBoot：底层是Spring框架，Spring框架默认是用JCL；

**SpringBoot选用SLF4j和Logback**



## 2、SLF4J使用

### 1、如何在系统中使用SLF4j

以后开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；

给系统里面导入slf4j的jar和logback的实现jar

```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

图示：

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqhdcd4ayqj30w00homz4.jpg)

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是做成日志实现框架自己本身的配置文件**；

### 2、遗留问题

a(slf4j+logback)：Spring(commons-logging)、hibernate(jboss-logging)、Mybatis、xxx

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出？

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqhdk6ddizj31830v70xc.jpg)

**如何让系统中所有的日志都统一到slf4j；**

**1、将系统中其他日志框架先排除出去；**

**2、用中间包来替换原有的日志框架；**

**3、我们导入slf4j其他的实现。**

## 3、SpringBoot日志关系

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
```

SpringBoot使用它来做日志功能：

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</dependency>
```

底层依赖关系：

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqhdzibvp3j30o709f0ta.jpg)

总结：

​	1）、SpringBoot底层也是使用slf4j+logback的方式进行日志记录；

​	2）、SpringBoot也把其他的日志都替换成了slf4j；

​	3）、中间替换包？

```
public abstract class LogFactory {

    static String UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J = "http://www.slf4j.org/codes.html#unsupported_operation_in_jcl_over_slf4j";

    static LogFactory logFactory = new SLF4JLogFactory();
```

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqhe3ubbfoj30bd05ut8q.jpg)

​	4）、如果我们要引入其他框架？一定要把这个框架的默认日志依赖移除掉？

​		Spring框架用的是commons-logging；

```
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<exclusions>
				<exclusion>
					<groupId>commons-logging</groupId>
					<artifactId>commons-logging</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```

**SpringBoot能自动适配所有的日志，而且底层使用slf4j+logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉；**

## 4、日志使用

### 1、默认配置

SpringBoot默认帮我们配置好了日志；

```
    // 记录器
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        // System.out.println();

        // 日志的级别；
        // 由低到高 trace < debug < info < warn < error
        // 可以调整需要输出的日志级别；日志只会在这个级别和以后的高级别生效

        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        // Springboot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别，root级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }
```

```
        <!--
        日志输出格式：
			%d表示日期时间，
			%thread表示线程名，
			%-5level：级别从左显示5个字符宽度
			%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
			%msg：日志消息，
			%n是换行符
        -->
```

SpringBoot修改日志的默认配置

```
logging.level.com.atguigu=trace

# logging.path
# 当前项目下生成springboot.log日志
# 可以指定完整的路径；
#logging.file=E:/springboot.log

# 在当前磁盘的根路径下创建spring文件夹和里卖年的log文件夹；使用 spring.log 作为默认文件
logging.path=/spring/log

# 在控制台输出的日志格式
logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
# 指定文件中日志输出的格式
logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} === - %msg%n
```

### 2、指定配置

给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用它默认的配置了。

![](http://ww1.sinaimg.cn/large/005PjuVtgy1fqhf1dl5z1j30po05g0tk.jpg)

logback.xml：直接就被日志框架识别了；

logback-spring.xml：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置。可以使用SpringBoot的高级Profile功能

```
<springProfile name="staging">
	可以指定某段配置在某个环境下生效
</springProfile>
```

否则

```
no applicable action for [springProfile]
```

```
        <layout class="ch.qos.logback.classic.PatternLayout">
            <springProfile name="dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ----> %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
            <springProfile name="!dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
        </layout>
```

