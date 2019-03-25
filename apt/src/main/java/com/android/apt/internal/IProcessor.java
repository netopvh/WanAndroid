package com.android.apt.internal;

import com.android.apt.AnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;

/**
 * @author Suming
 * @date 2019/3/6
 * @address https://github.com/whamu2
 */
public interface IProcessor {
    /**
     * 注解处理器
     *
     * @param roundEnv  {@link javax.annotation.processing.RoundEnvironment}
     * @param processor {@link com.android.apt.AnnotationProcessor}
     */
    void process(RoundEnvironment roundEnv, AnnotationProcessor processor);
}
