package com.example.demo;

import com.example.demo.annotation.SqlFile;
import com.example.demo.annotation.SqlQuery;
import org.atteo.classindex.ClassIndex;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;


public class SqlQueryHandler {


    public void init(){
        java.util.Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SqlFile.class));

        Set<BeanDefinition> beansWithSqlFileAnnotation = scanner.findCandidateComponents(this.getClass().getPackage().getName());

    //    ClassIndex.getSubclasses();
        Class<?> theClass=null;
        String file=null;
        for (BeanDefinition aclass : beansWithSqlFileAnnotation) {
            try {
                theClass = Class.forName(aclass.getBeanClassName());
                file=theClass.getDeclaredAnnotation(SqlFile.class).value();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class "+aclass.getBeanClassName()+" not found \t"+e.getMessage());
            }
            try {
                if(classLoader.getResourceAsStream(file)==null){
                    throw new RuntimeException("Unable to read " + file);
                }
                prop.loadFromXML(classLoader.getResourceAsStream(file));
            } catch (IOException e) {
                throw new RuntimeException("Unable to read" + file ,e);
            }
            for (Field aField : theClass.getDeclaredFields()) {
                if (aField.isAnnotationPresent(SqlQuery.class)) {
                    SqlQuery sqlQuery = aField.getAnnotation(SqlQuery.class);
                    aField.setAccessible(true);
                    try {
                        if(prop.getProperty(sqlQuery.value().toUpperCase())==null){
                            throw new RuntimeException("Key "+sqlQuery.value()+ " not found in "+ file);
                        }
                        aField.set(aclass, prop.getProperty(sqlQuery.value().toUpperCase()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            }
        }
    }



}
