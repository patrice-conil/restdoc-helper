# restdoc-helper
Maven plugin that generates adoc files and restdocs FieldDescriptors from Swagger2 annotations 

The purpose of Spring REST Docs is to document your API in an accurate way with auto-generated snippets produced with Spring 
MVC Test.
To achieve that, it's necessary to document all fields of your data class involved in callflows. If as me you use swagger2 to
describe your API contracts, the job is allmost done.
What'is missing is the translation of your swagger annotations to a Spring MVC Test FieldDescriptor[].
This is the purpose of this contribution.

See Springboot-sample sample to see how it works.


