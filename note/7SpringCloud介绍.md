# 一、Spring Cloud基本组件

​	注册中心： Eureka
​	负载均衡： Ribbon
​	声明式调用远程方法： Feign
​	熔断、降级、监控： Hystrix
​	网关： Zuul

## **原理：**

各个微服务的提供者（provider）将各自注册入Eureka注册中心，可以指定一个模块，导入Feign的相关依赖，专门用于编写对应各个提供者的接口，通过Feign，将抽象方法与微服务的具体方法联系起来。而微服务的使用者（consumer）也注册入Eureka，并依赖前面的Feign接口模块，这样可以通过自动装配各个接口，来调用接口的方法，而这些方法就是在微服务中被实现的，并且可以直接在使用者工程中使用；

Ribbon提供了客户端负载均衡的功能，但是并不需要开发时主动配置，就有默认的配置。

Hystrix的作用则是给provider提供熔断机制（在provider中提供方法的备用方法）、给consumer提供降级功能（在consumer中给调用远程方法处增加备用方法，通常是在调用失败时，在方法中匿名实现接口，使用自己实现的接口的方法），并且提供了监控provider的功能（Hystrix Dashboard）；

Zuul的作用则是给外部提供一个统一的入口，如浏览器等访问时，就可以通过Zuul提供的入口，进行相对简单的访问。



# 二、各服务具体配置内容

此处Spring Boot版本：  2.3.3RELEASE

​		Spring Cloud版本：Hoxton.SR8

在parent工程管理依赖信息

```xml
<dependencyManagement>
    <dependencies>
        <!-- 导入SpringCloud需要的依赖信息 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR8</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <!-- SpringBoot依赖信息 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.3.3.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



## Eureka

### 服务端：

依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

主启动类

```java
@EnableEurekaServer
@SpringBootApplication
public class CrowdMainApp {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainApp.class, args);
    }
}
```

application.yml

```yml
server:					# 服务的端口号
  port: 1000
spring:					# Spring的应用名
  application:
    name: crowd-eureka
eureka:					# Eureka的服务端设置hostname，客户端通过hostname与端口访问
  instance:
    hostname: localhost
  client:
    fetch-registry: false				# 自己就是注册中心，因此不注册自己
    register-with-eureka: false			# 自己就是注册中心，不需要从注册中心取回信息
    service-url:						# 客户端访问Eureka时，使用的地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

### 客户端：

依赖

```xml
<!-- eureka客户端依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

yml配置：

```yml
server:
  port: 2000
spring:
  application:
    name: crowd-mysql		# application.name会显示在Eureka的页面
eureka:
  client:
    service-url:
      defaultZone: http://localhost:1000/eureka/
```

==**注意**==：低版本的Spring Cloud可能需要在主启动类上加@EnableEurekaClient或@EnableDiscoveryClient注解。

## Feign

Feign的依赖spring-cloud-starter-openfeign一般会加入到一个通用的接口模块，其他消费者项目依赖该模块，以得到这些接口，来调用接口对应的远程方法。

依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### 统一接口

远程调用方法的接口示例：

```java
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/get/member/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);
}
```

**@RequestMapping中的url必须与远程方法的@RequestMapping中的url相同；方法的声明也必须完全相同，其中参数前的@RequestParam、@RequestBody等，也必须保持一致**

### 远程方法

```java
@RestController
public class MemberProviderHandler {

    @Autowired
    MemberService memberService;

    @RequestMapping("/get/member/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
        try {
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

}
```

### Consumer

在Consumer中想要调用接口模块中对应的远程方法，则必须在主启动类上加入@EnableFeignClients注解开启Feign客户端功能。

前提也需要引入前面接口项目的依赖

```java
@EnableFeignClients
@SpringBootApplication
public class CrowdMainApp {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainApp.class, args);
    }
}
```



## Hystrix

Hystrix提供了服务提供端的熔断功能、服务使用端的降级功能以及对提供端的监控功能。



### 熔断：

依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

主启动类设置开启断路器功能

```java
// 开启断路器功能
@EnableCircuitBreaker
@SpringBootApplication
public class SpringCloudStudyProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStudyProviderApplication.class, args);
    }

}
```

handler层的方法中添加一个备用方法，作为熔断时的备用：

触发异常、长时间未响应等情况都会触发服务端熔断

```java
@RestController
public class EmployeeHandler {


    // @HystrixCommand表示出现问题时，使用fallbackMethod的属性值的方法名的方法作为备用方法
    @HystrixCommand(fallbackMethod = "getEmpBackup")
    @RequestMapping("/provider/circuit/breaker/get/emp")
    public ResultEntity<Employee> getEmp(@RequestParam("signal") String signal) throws InterruptedException {
        if ("quick-bang".equals(signal)) {
            throw new RuntimeException();
        }
        if ("slow-bang".equals(signal)) {
            Thread.sleep(5000);
        }
        return ResultEntity.successWithData(new Employee(99,"FallBack",2888.88));
    }

    // getEmp熔断时的备用方法
    public ResultEntity<Employee> getEmpBackup(@RequestParam("signal") String signal) {
        return ResultEntity.failed("熔断生效-因为signal：" + signal);
    }

}
```



### 降级：

以下的对接口的设置发生在接口模块，而不是consumer模块

依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

通过一个自己实现的FallbackFactory接口实现类，定义降级操作

```java
// 通过实现FallbackFactory接口，给出consumer端的备用方案（降级）
// 自定义的FallbackFactory必须通过@Component加入IOC容器，
// 否则在对应接口的@FeignClient注解中通过fallbackFactory属性设置时会找不到该类
@Component
// 通过FallbackFactory的泛型指出是对谁进行降级操作
public class MyFallbackFactory implements FallbackFactory<EmployeeRemoteService> {
    @Override
    public EmployeeRemoteService create(Throwable throwable) {
        return new EmployeeRemoteService() {
            // 不想进行降级操作就直接返回null
            @Override
            public Employee getEmployeeRemoteToFeign(String keyword) {
                return null;
            }
			// 实现的该方法，就是在原本service中该方法不可达时，备用调用的方法
            @Override
            public ResultEntity<Employee> getEmp(String signal) throws InterruptedException {
                return ResultEntity.failed("执行降级" + throwable.getMessage());
            }
        };
    }
}
```

EmployeeService使用FallbackFactory：

```java
// value指定eureka中的application name，fallbackFactory指定代表备用方案的FallbackFactory实现类
@FeignClient(value = "fall-provider" , fallbackFactory = MyFallbackFactory.class)
public interface EmployeeRemoteService {

    @RequestMapping("/provider/feign/get/employee/remote")
    public Employee getEmployeeRemoteToFeign(@RequestParam("keyword") String keyword);

    @RequestMapping("/provider/circuit/breaker/get/emp")
    public ResultEntity<Employee> getEmp(@RequestParam("signal") String signal) throws InterruptedException;

}
```

consumer模块的操作：

在application.yml文件中设置开启hystrix：

```yml
feign:
  hystrix:
    enabled: true
```



### 监控

监控仪表盘模块：

依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

主启动类开启仪表盘功能

```java
@EnableHystrixDashboard
@SpringBootApplication
public class MyDashboardMainApp {

    public static void main(String[] args) {
        SpringApplication.run(MyDashboardMainApp.class, args);
    }
}
```



application.yml

```yml
server:
  port: 8000
spring:
  application:
    name: fall-dashboard
hystrix:
  dashboard:
    proxy-stream-allow-list: localhost		# 设置允许监控的列表，不设置正确的话会在监控页面出现无法连接的提示
```

被监控的模块：

依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

application.yml加入：

```yml
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream
```

通过Hystrix Dashboard工程的端口访问监控首页

如 ： http://localhost:8000/hystrix

在页面中通过http://url:port/actutor/hystrix.stream，访问对应url和port的监控页面

## Zuul

Zuul网关与Eureka整合，将自身注册入Eureka，并通过application-name管理其中注册的服务等，让外界可以直接通过Zuul访问各项微服务。

依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

application.yml

```yml
server:
  port: 9000
spring:
  application:
    name: fall-zuul
eureka:
  client:
    service-url:
      defaultZone: http://localhost:5000/eureka/
zuul:
  routes:
    employee:                           # 设置“/zuul-emp/”来代替微服务的名字，“employee”是自定义的，可以使用其他的名字
      serviceId: fall-feign-consumer
      path: /zuul-emp/**
  ignored-services:                     # 设置不能通过“fall-feign-consumer”（微服务的名字）来访问，只能通过包装后的path
    - fall-feign-consumer
  # ignored-services: '*' ：用于忽视所有微服务的名称
```

主启动类

```java
// 启动Zuul代理功能
@EnableZuulProxy
@SpringBootApplication
public class MyZuulMainApp {
    public static void main(String[] args) {
        SpringApplication.run(MyZuulMainApp.class,args);
    }
}
```

此时用户就可以通过：

`http://localhost:9000/application-name/requestMapping的url `来访问



### ZuulFilter

Zuul中提供的一个过滤器，可以通过ZuulFilter进行登录检测等。

需要继承ZuulFilter，并通过@Component加入IOC容器

```java
@Component
// 继承ZuulFilter，并放入IOC容器，通过该类可通过网关进行过滤
public class MyZuulFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(MyZuulFilter.class);



    @Override
    public String filterType() {
        // filterType用pre返回，表示在目标微服务之前执行过滤操作
        String filterType = "pre";
        return filterType;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /***
     *
     * @return true: 表示需要过率； false:表示直接放行
     */
    @Override
    public boolean shouldFilter() {
        // 获取当前的RequestContext对象
        RequestContext context = RequestContext.getCurrentContext();

        // 通过RequestContext对象得到当前请求对象
        HttpServletRequest request = context.getRequest();

        // 判断
        String signal = request.getParameter("signal");

        // signal=="hello"时,进入过滤
        return "hello".equals(signal);
    }

    @Override
    public Object run() throws ZuulException {

        logger.info("signal==hello,进入过滤！");
        System.err.println("signal==hello,进入过滤！");

        // 官方文档指出当前实现会忽略该返回值
        // 因此直接返回null即可
        return null;
    }
}
```