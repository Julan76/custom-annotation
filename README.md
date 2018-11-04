Custom annotation to read xml files and to set class field at runtime. 
The goal is to avoid to have sql queries in java classes.
@SqlFile on class to specify file 
@SqlQuery on field to spécify query

```java
@SqlFile("queries/test.xml")
public class Test{
    @SqlQuery("_sql_query")
    private static myQuery;
}
````
Xml file should look like this :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">

<properties>

    <entry key="_SQL_QUERY">
        <![CDATA[
    Successfull operation
    ]]></entry>

</properties>
```