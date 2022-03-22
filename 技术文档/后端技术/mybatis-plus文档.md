官网:https://mp.baomidou.com

# pom依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
 <!--mybatis-plus-->
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.2</version>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.0</version>
</dependency>
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.4.0</version>
</dependency>
```



# 代码一键生成

> debu运行即可,自动生成代码文件

```java
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author wxy
 * @Description 代码生成器
 */
public class CodeGenerator {

    /**读取控制台内容*/
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("wxy");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mariadb://172.20.54.88:3306/ipm?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&allowMultiQueries=true");
        dsc.setDriverName("org.mariadb.jdbc.Driver");
        dsc.setUsername("ipm");
        dsc.setPassword("sailing");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent("com.sailing");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setChainModel(true); //支持链式操作
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(""); //表名前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```





# 配置

## springboot的mapper配置

> 配置 MapperScan 注解

```java
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```



## 注解



https://mp.baomidou.com/guide/annotation.html#tablename



# 分页插件

```java
@Configuration
@MapperScan("com.sailing.mapper")
public class MybatisPlusConfig {
    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(true);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCallSettersOnNulls(true);
            configuration.setJdbcTypeForNull(JdbcType.NULL);
        };
    }

}
```

```java
// 第一种
@Test
void lambdaPageTest() {
    LambdaQueryChainWrapper<User> wrapper2 = userService.lambdaQuery();
    wrapper2.like(User::getName, "a"); // 模糊查询
    userService.page(new Page<>(1, 10), wrapper2.getWrapper()).getRecords().forEach(System.out::print);
}

@Test
void test() {
    userService.lambdaQuery().like(User::getName, "a").list().forEach(System.out::println);
    Page page = userService.lambdaQuery().like(User::getName, "a").page(new Page<>(1, 10)); // 分页模糊查询
    page.getRecords().forEach(System.out::println);
}

// 第二种
QueryWrapper<AccessControl> queryWrapper = new QueryWrapper<>();
Page<AccessControl> page = new Page<>(accessControl.getCurrent(), accessControl.getLimit());
if (StringUtils.isNotBlank(accessControl.getSrcIp())) {
    queryWrapper.like("src_ip",accessControl.getSrcIp());
}
if (StringUtils.isNotBlank(accessControl.getTargetIp())) {
    queryWrapper.like("target_ip",accessControl.getTargetIp());
}
accessControlService.page(page,queryWrapper);
```

# 逻辑删除

```yml
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```

###  步骤2: 实体类字段上加上`@TableLogic`注解

```java
@TableLogic
private Integer deleted;
```

# 自动填充功能

```java
public class User {

    // 注意！这里需要标记为填充字段
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    
    @TableField(.. fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
```

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称，不是字段名称
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
```



# 支持数据安全,审计,敏感数据脱敏,字段加密解密



# 多数据源

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```

```yml
spring:
  datasource:
    dynamic:
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      strict: false #严格匹配数据源,默认false. true未匹配到指定数据源时抛异常,false使用默认数据源
      datasource:
        master:
          url: jdbc:mysql://xx.xx.xx.xx:3306/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver # 3.2.0开始支持SPI可省略此配置
        slave_1:
          url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver
        slave_2:
          url: ENC(xxxxx) # 内置加密,使用请查看详细文档
          username: ENC(xxxxx)
          password: ENC(xxxxx)
          driver-class-name: com.mysql.jdbc.Driver
       #......省略
       #以上会配置一个默认库master，一个组slave下有两个子库slave_1,slave_2
       
# 多主多从                      纯粹多库（记得设置primary）                   混合配置
spring:                               spring:                               spring:
  datasource:                           datasource:                           datasource:
    dynamic:                              dynamic:                              dynamic:
      datasource:                           datasource:                           datasource:
        master_1:                             mysql:                                master:
        master_2:                             oracle:                               slave_1:
        slave_1:                              sqlserver:                            slave_2:
        slave_2:                              postgresql:                           oracle_1:
        slave_3:                              h2:                                   oracle_2:
```



# 支持数据分库分表

```yml
地址:https://gitee.com/baomidou/mybatis-mate-examples/tree/master/mybatis-mate-sharding/src/main/java/mybatis
# Mybatis Mate 配置
mybatis-mate:
  cert:
    grant: ☺请添加微信wx153666购买授权，不白嫖从我做起！
    license: SM/hy2HGmqRZIw7NE5i1vKIiHOQLGXGymokyRCkR+TYuNZuVTdYiESr41OZiYyDkH095+Y5Ifc7tCyVeGi3qrsnyninzgcmvZsUKDl9kQYXzvbt/R7UMj6yqchPsZYf0UQ0mvRyoja1ljXiqbz49qrqXy0q1umWBEzrbH7QjE55M/bnABovM5ljCoENfY2b0EvSaJCUeqeAAxM3K6VrBo0YI4rz9Uo28AXhRgAyaI0QgMy+APM5bCdp9GEXHXYEyApMDvRCqWLfh5WZVdDWD5PZouQL3TKD3kqEf4k+Z1GkrVtHDS9mugpbSP533dHd9O+NF1KpOhkN0lVojrJ21Bg==
  sharding:
    health: true # 健康检测
    primary: mysql # 默认选择数据源
    datasource:
      mysql:
        - key: t1
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
          username: root
          schema: test
        - key: t2
          cluster: slave # 从库读写分离时候负责 sql 查询操作，主库 master 默认可以不写
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
          username: root
          schema: test2
      postgres:
        - key: t1
          driver-class-name: org.postgresql.Driver
          url: jdbc:postgresql://localhost:5432/test
          username: postgres
          password: 123456
          schema: test
```



# 自动生成主键

| 方法     | 主键生成策略 | 主键类型            | 说明                                                         |
| -------- | ------------ | ------------------- | ------------------------------------------------------------ |
| nextId   | ASSIGN_ID    | Long,Integer,String | 支持自动转换为String类型，但数值类型不支持自动转换，需精准匹配，例如返回Long，实体主键就不支持定义为Integer |
| nextUUID | ASSIGN_UUID  | String              | 默认不含中划线的UUID生成                                     |

###  方式一：声明为Bean供Spring扫描注入

```java
@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private String name;
}
```

```java
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

	private final AtomicLong al = new AtomicLong(1);

	@Override
	public Long nextId(Object entity) {
		//可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
		String bizKey = entity.getClass().getName();
		log.info("bizKey:{}", bizKey);
		MetaObject metaObject = SystemMetaObject.forObject(entity);
		String name = (String) metaObject.getValue("name");
		final long id = al.getAndAdd(1);
		log.info("为{}生成主键值->:{}", name, id);
		return id;
	}


	@Override
	public String nextUUID(Object entity) {
		return IdWorker.get32UUID();
	}
}
```



# 乐观锁

```java
@Version
private Integer version;
```

> 说明:
>
> - **支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime**
> - 整数类型下 `newVersion = oldVersion + 1`
> - `newVersion` 会回写到 `entity` 中
> - 仅支持 `updateById(id)` 与 `update(entity, wrapper)` 方法
> - **在 `update(entity, wrapper)` 方法下, `wrapper` 不能复用!!!**



# CRUD接口

```java
    @Test
    public void aInsert() {
        User user = new User();
        user.setName("小羊");
        user.setAge(3);
        user.setEmail("abc@mp.com");
        assertThat(mapper.insert(user)).isGreaterThan(0);
        // 成功直接拿回写的 ID
        assertThat(user.getId()).isNotNull();
    }


    @Test
    public void bDelete() {
        assertThat(mapper.deleteById(3L)).isGreaterThan(0);
        assertThat(mapper.delete(new QueryWrapper<User>()
                .lambda().eq(User::getName, "Sandy"))).isGreaterThan(0);
    }


    @Test
    public void cUpdate() {
        assertThat(mapper.updateById(new User().setId(1L).setEmail("ab@c.c"))).isGreaterThan(0);
        assertThat(
                mapper.update(
                        new User().setName("mp"),
                        Wrappers.<User>lambdaUpdate()
                                .set(User::getAge, 3)
                                .eq(User::getId, 2)
                )
        ).isGreaterThan(0);
        User user = mapper.selectById(2);
        assertThat(user.getAge()).isEqualTo(3);
        assertThat(user.getName()).isEqualTo("mp");

        mapper.update(
                null,
                Wrappers.<User>lambdaUpdate().set(User::getEmail, null).eq(User::getId, 2)
        );
        assertThat(mapper.selectById(1).getEmail()).isEqualTo("ab@c.c");
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isNull();
        assertThat(user.getName()).isEqualTo("mp");

        mapper.update(
                new User().setEmail("miemie@baomidou.com"),
                new QueryWrapper<User>()
                        .lambda().eq(User::getId, 2)
        );
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isEqualTo("miemie@baomidou.com");

        mapper.update(
                new User().setEmail("miemie2@baomidou.com"),
                Wrappers.<User>lambdaUpdate()
                        .set(User::getAge, null)
                        .eq(User::getId, 2)
        );
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isEqualTo("miemie2@baomidou.com");
        assertThat(user.getAge()).isNull();
    }


    @Test
    public void dSelect() {
        mapper.insert(
                new User().setId(10086L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(3));
        assertThat(mapper.selectById(10086L).getEmail()).isEqualTo("miemie@baomidou.com");
        User user = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 10086));
        assertThat(user.getName()).isEqualTo("miemie");
        assertThat(user.getAge()).isEqualTo(3);

        mapper.selectList(Wrappers.<User>lambdaQuery().select(User::getId))
                .forEach(x -> {
                    assertThat(x.getId()).isNotNull();
                    assertThat(x.getEmail()).isNull();
                    assertThat(x.getName()).isNull();
                    assertThat(x.getAge()).isNull();
                });
        mapper.selectList(new QueryWrapper<User>().select("id", "name"))
                .forEach(x -> {
                    assertThat(x.getId()).isNotNull();
                    assertThat(x.getEmail()).isNull();
                    assertThat(x.getName()).isNotNull();
                    assertThat(x.getAge()).isNull();
                });
    }

    @Test
    public void orderBy() {
        List<User> users = mapper.selectList(Wrappers.<User>query().orderByAsc("age"));
        assertThat(users).isNotEmpty();
        //多字段排序
        List<User> users2 = mapper.selectList(Wrappers.<User>query().orderByAsc("age", "name"));
        assertThat(users2).isNotEmpty();
        //先按age升序排列，age相同再按name降序排列
        List<User> users3 = mapper.selectList(Wrappers.<User>query().orderByAsc("age").orderByDesc("name"));
        assertThat(users3).isNotEmpty();
    }

    @Test
    public void selectMaps() {
        List<Map<String, Object>> mapList = mapper.selectMaps(Wrappers.<User>query().orderByAsc("age"));
        assertThat(mapList).isNotEmpty();
        assertThat(mapList.get(0)).isNotEmpty();
        System.out.println(mapList.get(0));
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = mapper.selectMapsPage(new Page<>(1, 5), Wrappers.<User>query().orderByAsc("age"));
        assertThat(page).isNotNull();
        assertThat(page.getRecords()).isNotEmpty();
        assertThat(page.getRecords().get(0)).isNotEmpty();
        System.out.println(page.getRecords().get(0));
    }

    @Test
    public void orderByLambda() {
        List<User> users = mapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge));
        assertThat(users).isNotEmpty();
        //多字段排序
        List<User> users2 = mapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge, User::getName));
        assertThat(users2).isNotEmpty();
        //先按age升序排列，age相同再按name降序排列
        List<User> users3 = mapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge).orderByDesc(User::getName));
        assertThat(users3).isNotEmpty();
    }

    @Test
    public void testSelectMaxId() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("max(id) as id");
        User user = mapper.selectOne(wrapper);
        System.out.println("maxId=" + user.getId());
        List<User> users = mapper.selectList(Wrappers.<User>lambdaQuery().orderByDesc(User::getId));
        Assertions.assertEquals(user.getId().longValue(), users.get(0).getId().longValue());
    }

    @Test
    public void testGroup() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(*)")
                .groupBy("age");
        List<Map<String, Object>> maplist = mapper.selectMaps(wrapper);
        for (Map<String, Object> mp : maplist) {
            System.out.println(mp);
        }
        /**
         * lambdaQueryWrapper groupBy orderBy
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda()
                .select(User::getAge)
                .groupBy(User::getAge)
                .orderByAsc(User::getAge);
        for (User user : mapper.selectList(lambdaQueryWrapper)) {
            System.out.println(user);
        }
    }

    @Test
    public void testTableFieldExistFalse() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(age) as count")
                .groupBy("age");
        List<User> list = mapper.selectList(wrapper);
        list.forEach(System.out::println);
        list.forEach(x -> {
            Assertions.assertNull(x.getId());
            Assertions.assertNotNull(x.getAge());
            Assertions.assertNotNull(x.getCount());
        });
        mapper.insert(
                new User().setId(10088L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(3));
        User miemie = mapper.selectById(10088L);
        Assertions.assertNotNull(miemie);

    }

    @Test
    public void testSqlCondition() {
        Assertions.assertEquals(user2Mapper.selectList(Wrappers.<User2>query()
                .setEntity(new User2().setName("n"))).size(), 2);
        Assertions.assertEquals(user2Mapper.selectList(Wrappers.<User2>query().like("name", "J")).size(), 2);
        Assertions.assertEquals(user2Mapper.selectList(Wrappers.<User2>query().gt("age", 18)
                .setEntity(new User2().setName("J"))).size(), 1);
    }
```







# 条件构造器



## 查询

```java
    @Test
    public void tests() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new QueryWrapper<User>().eq("role_id", 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userMapper.selectList(new QueryWrapper<User>()
                .inSql("role_id", "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        Assertions.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userMapper.selectList(new QueryWrapper<User>()
                .nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L))
                .and(i -> i.ge("age", 20)));
        List<User> lambdaUsers3 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assertions.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        // 方式一
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = 2"));
/*        List<User> lambdaUsers4 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .apply("role_id = 2"));*/
        // 方式二
        List<User> plainUsers5 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = {0}",2));
/*        List<User> lambdaUsers5 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .apply("role_id = {0}",2));*/
        print(plainUsers4);
        Assertions.assertEquals(plainUsers4.size(), plainUsers5.size());

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assertions.assertNull(u4.getEmail());


    }

    @Test
    public void lambdaQueryWrapper() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getRoleId, 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userMapper.selectList(new LambdaQueryWrapper<User>()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        Assertions.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userMapper.selectList(new LambdaQueryWrapper<User>()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        List<User> lambdaUsers3 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assertions.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assertions.assertNull(u4.getEmail());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

    /**
     * SELECT id,name,age,email,role_id FROM user
     * WHERE ( 1 = 1 ) AND ( ( name = ? AND age = ? ) OR ( name = ? AND age = ? ) )
     */
    @Test
    public void testSql() {
        QueryWrapper<User> w = new QueryWrapper<>();
        w.and(i -> i.eq("1", 1))
                .nested(i ->
                        i.and(j -> j.eq("name", "a").eq("age", 2))
                                .or(j -> j.eq("name", "b").eq("age", 2)));
        userMapper.selectList(w);
    }

    /**
     * SELECT id,name FROM user
     * WHERE (age BETWEEN ? AND ?) ORDER BY role_id ASC,id ASC
     */
    @Test
    public void testSelect() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("id","name").between("age",20,25)
                .orderByAsc("role_id","id");
        List<User> plainUsers = userMapper.selectList(qw);

        LambdaQueryWrapper<User> lwq = new LambdaQueryWrapper<>();
        lwq.select(User::getId,User::getName).between(User::getAge,20,25)
                .orderByAsc(User::getRoleId,User::getId);
        List<User> lambdaUsers = userMapper.selectList(lwq);

        print(plainUsers);
        Assertions.assertEquals(plainUsers.size(), lambdaUsers.size());
    }
```





## 更新

```java
    @Autowired
    private UserMapper userMapper;

    /**
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void tests() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user,new UpdateWrapper<User>().eq("name","Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null,new UpdateWrapper<User>()
                .set("age",29).set("email","test3update@baomidou.com").eq("name","Tom"));

    }

    /**
     * 使用lambda条件构造器
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void testLambda() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user,new LambdaUpdateWrapper<User>().eq(User::getName,"Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null,new LambdaUpdateWrapper<User>()
                .set(User::getAge,29).set(User::getEmail,"test3update@baomidou.com").eq(User::getName,"Tom"));

    }

```















