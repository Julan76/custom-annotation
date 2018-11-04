package com.example.demo.annotation;

import org.atteo.classindex.IndexSubclasses;

import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@IndexSubclasses
@Target({ElementType.FIELD})
public @interface SqlQuery {

    @NotNull
    String value();

}
