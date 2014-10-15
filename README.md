MyBatis Generator Custom
========================

## DESCRIPTION
### [Joda-Time](http://www.joda.org/joda-time/, "Joda-Time") support

[Joda-Time](http://www.joda.org/joda-time/, "Joda-Time") can be used for date/time type.

| # | JDBC Type | Java Type (Joda-Time)
|--:|:----------|:-----------------------------
| 1 | DATE      | `org.joda.time.LocalDate`
| 2 | TIME      | `org.joda.time.LocalTime`
| 3 | TIMESTAMP | `org.joda.time.LocalDateTime`

### Java type mapping based on column names

Java type mapping can be configured, based on column names.  It is useful, for example, when "DELETED_FLG" indicates logical deletion and should be mapped to enumerated type `DeletedFlag`.


## HOW TO USE
### pom.ml

Add "cherry:mybatis-generator-custom:0.0" to dependencies of MyBatis Generator.

```XML:pom.xml
<plugin>
	<groupId>org.mybatis.generator</groupId>
	<artifactId>mybatis-generator-maven-plugin</artifactId>
	<version>1.3.2</version>
	<configuration>
		<configurationFile>mbgenerator.xml</configurationFile>
	</configuration>
	<dependencies>
		<dependency>
			<groupId>cherry</groupId>
			<artifactId>mybatis-generator-custom</artifactId>
			<version>0.0</version>
		</dependency>
	</dependencies>
</plugin>
```

### MyBatis Generator configuration

Use `JavaTypeResolverCustomImpl` for javaTypeResolver@type, i.e. `<javaTypeResolver type="cherry.mybatis.generator.custom.JavaTypeResolverCustomImpl">`.  Following properties are supported:

| # | property name                        | property value
|--:|:-------------------------------------|:-----------------------------------
| 1 | "useJodaTime"                        | "true" enables Joda-Time support
| 2 | "javaTypeByColumnName.{COLUMN NAME}" | "{Java Fully Qualified Class Name}"

```XML:mbgenerator.xml
<javaTypeResolver
	type="cherry.mybatis.generator.custom.JavaTypeResolverCustomImpl">
	<property name="useJodaTime" value="true" />
	<property name="javaTypeByColumnName.DELETED_FLG"
		value="cherry.spring.common.type.DeletedFlag" />
</javaTypeResolver>
```

### Generate

```bash:COMMAND
$ mvn mybatis-generator:generate
  => Generated according to configuration file.
```


## HOW TO BUILD

```bash:COMMAND
$ cd mybatis-generator-custom
$ mvn clean compile install
  => Installed to ~/.m2/repository/cherry/mybatis-generator-custom/0.0/mybatis-generator-custom-0.0.jar
```

### NOTICE
It seems that it should be compiled as 1.6 source.  When compiled as 1.7, `mvn mybatis-generator:generate` failed as follows:

```bash:COMMAND
[ERROR] Failed to execute goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate (default-cli) on project dbschema: Execution default-cli of goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate failed: An API incompatibility was encountered while executing org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate: java.lang.UnsupportedClassVersionError: cherry/mybatis/generator/custom/JavaTypeResolverCustomImpl : Unsupported major.minor version 51.0
```
