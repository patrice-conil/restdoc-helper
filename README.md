
# Restdoc Helper

## Overview

The main goal of this project is to simplify the documentation process of your RESTFul services by combining hand-written using [Asciidoctor][3] docs, [Sprinfox static docs][1] generated docs or [Spring REST Docs][10] generated docs. 
Restdoc helper is a Maven plugin that generates [Asciidoctor][3] files and  [FieldDescriptors][7] from [Swagger 2][6] annotations

The purpose of [Spring REST Docs][10] is to document your API with auto-generated snippets produced with [Spring MVC Test][4].
To achieve that, it's necessary to document all fields of data classes involved in your call-flows. If as me you use [Swagger 2][6] to describe your API contracts, the job is almost done.
What'is missing is the translation of your swagger annotations to a [FieldDescriptors][7]. This is the purpose of this contribution.
It's also possible to generate asciidoctor files only that describe your model from custom annotation. 

## Doc generation

### Add dependencies to your pom

```
        <dependency>
            <groupId>com.pconil.restdoc</groupId>
            <artifactId>restdoc-annotation</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>`
            <build>
            ...
                <plugins>
                    <plugin>
                        <groupId>com.pconil.restdoc</groupId>
                        <artifactId>restdoc-helper-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <configuration>
                            <sourceDir>${basedir}/target/classes/</sourceDir>
                            <basePackageName>com.pconil.restdoc.model</basePackageName>
                            <javaDir>${basedir}/target/generated-test-sources</javaDir>
                            <adocDir>${basedir}/target/generated-snippets</adocDir>
                        </configuration>
                        <executions>
                            <execution>
                                <id>generate</id>
                                <phase>generate-test-sources</phase>
                                <goals>
                                    <goal>restdoc-helper</goal>
                                </goals>
                            </execution>
                        </executions>
                        <dependencies>
                            <dependency>
                                <groupId>org.springframework</groupId>
                                <artifactId>spring-web</artifactId>
                                <version>4.3.3.RELEASE</version>
                            </dependency>
                        </dependencies>
                    </plugin>
                    ...
        
```

### Howto Generate adoc files from swagger annotations

As simple as adding @InspectToDocument to your model class

```
@InspectToDocument(description = "This is the Class1 description")
public class ClassSwaggerDTO {
    @ApiModelProperty(required = true, value="This is the field field1")
    private String field1;

    @ApiModelProperty(value="This is the optional field field2")
    private String field2 = null;

    @ApiModelProperty(required = true, value="This is the field field3")
    private String field3 = null;
    
    public ClassSwaggerDTO(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
```


### And If you don't use swagger or want to document classes that doesn't contain @ApiModelProperty

Add a @AsciidocAnnotation to your fields

```
@InspectToDocument(description = "This is the Class1 description")
public class Class1DTO {
    @AsciidocAnnotation(description="This field describe name of Class1DTO", constraints = "Length must be between 4 and 6")
    String field1;

    @AsciidocAnnotation(description="field 2")
    String field2 = null;

    public Class1DTO(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
```

## Go further

See the springboot-sample to see how it works.

## Prerequisite

Java 8, maven 3

## Building from source

You will need Java 8 or later to build restdoc-helper. It is built using [maven][2]:

```
mvn clean install
```

## Contributing

There are several ways to contribute to restdoc-helper:

 - Open a [pull request][12].
 - Ask and answer questions on Stack Overflow using the [`restdoc-helper`][15] tag.

## Licence

Restdoc helper is open source software released under the [Apache 2.0 license][14].


[1]: https://springfox.github.io/springfox/docs/snapshot/#configuring-springfox-staticdocs
[2]: https://maven.apache.org/download.cgi
[3]: http://asciidoctor.org
[4]: http://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/htmlsingle/#spring-mvc-test-framework
[5]: https://developer.github.com/v3/
[6]: http://swagger.io/specification/
[7]: http://docs.spring.io/spring-restdocs/docs/current/reference/html5/#documenting-your-api-request-response-payloads-reusing-field-descriptors
[10]: http://docs.spring.io/spring-restdocs/docs/current/reference/html5/
[12]: https://help.github.com/articles/using-pull-requests/
[14]: http://www.apache.org/licenses/LICENSE-2.0.html
[15]: http://stackoverflow.com/tags/restdoc-helper
