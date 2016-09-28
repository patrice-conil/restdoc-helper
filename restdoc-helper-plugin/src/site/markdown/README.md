Maven plugin that generates <a href="http://asciidoctor.org/docs/user-manual/">asciidoctor</a> files and <a href="http://docs.spring.io/autorepo/docs/spring-restdocs/1.1.x/reference/html5/">restdocs</a> FieldDescriptors from <a href="http://swagger.io/">Swagger2</a> annotations 

The purpose of Spring REST Docs is to document your API in an accurate way with auto-generated snippets produced with 
Spring MVC Test.

To achieve that, it's necessary to document all fields of data classes involved in your call-flows. If as me you use swagger2 to describe your API contracts, the job is almost done.

What'is missing is the translation of your swagger annotations to a SpringMVC Test FieldDescriptor[]. This is the purpose of this contribution.

It's also possible to generate asciidoctor files that describe your model from custom annotation. See the bundled springboot-sample to see how it works.


