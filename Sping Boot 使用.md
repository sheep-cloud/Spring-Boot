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
    
    他的父项目是
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-dependencies</artifactId>
		<version>1.5.9.RELEASE</version>
		<relativePath>../../spring-boot-dependencies</relativePath>
	</parent>
	他来真正管理Spring Boot应用里卖年的所有依赖版本
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

## 4、@Value获取值和@ConfigurationProperties获取值比较

|            | @ConfigurationProperties | @Value            |
| ---------- | ------------------------ | ----------------- |
| 功能         | 批量注入配置文件中的属性             | 一个一个指定            |
| 松散绑定（松散语法） | 支持,person.last-name      | 必须person.lastName |
| SpEL       | 不支持                      | 支持                |
| JSR303数据校验 | 支持                       | 不支持               |

配置文件yml还是properteis他们都能获取到值；