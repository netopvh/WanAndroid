package com.android.apt.processor;

import com.android.apt.AnnotationProcessor;
import com.android.apt.internal.IProcessor;
import com.android.lib.apt.ApiFactory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author Suming
 * @date 2019/3/6
 * @address https://github.com/whamu2
 */
public class ApiFactoryProcessor implements IProcessor {
    private static final String SUFFIX = "Impl";
    private static final String DATA_ARR_CLASS = "BaseResp";

    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor processor) {
        try {
            for (TypeElement typeElement : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                processor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + typeElement.toString());

                String simpleName = String.valueOf(typeElement.getSimpleName());
                String className = simpleName + SUFFIX;
                PackageElement pkg = processor.mElements.getPackageOf(typeElement);
                String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

                /*生成实现类开始*/
                TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                        .addModifiers(PUBLIC, FINAL)
                        .addJavadoc("@ API工厂 此类由apt自动生成");

                /*遍历所有方法*/
                for (Element e : typeElement.getEnclosedElements()) {
                    ExecutableElement executableElement = (ExecutableElement) e;

                    /*生成方法注解*/
                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addJavadoc("@此方法由apt自动生成")
                                    .addModifiers(PUBLIC, STATIC);

                    if (TypeName.get(executableElement.getReturnType()).toString().contains(DATA_ARR_CLASS)) {
                        // methodBuilder.returns(ClassName.get("io.reactivex", "Observable"));
                        methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
                        List<? extends VariableElement> parameters = executableElement.getParameters();
                        StringBuilder paramsString = new StringBuilder();
                        for (int i = 0; i < parameters.size(); i++) {
                            VariableElement ep = parameters.get(i);
                            methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                            if (i == parameters.size() - 1) {
                                paramsString.append(ep.getSimpleName().toString());
                            } else {
                                paramsString.append(ep.getSimpleName().toString()).append(", ");
                            }
                        }
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".create($T.class)" +
                                        ".$L($L)" +
                                        ".compose($T.cutoverSchedulers())"
                                , ClassName.get("com.whamu2.wanandroid.utils", "ApiUtil")
                                , ClassName.get(packageName, simpleName)
                                , e.getSimpleName().toString()
                                , paramsString.toString()
                                , ClassName.get("com.whamu2.wanandroid.utils", "Transformer"));
                        builder.addMethod(methodBuilder.build());

                    } else {
                        methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
                        StringBuilder paramsString = new StringBuilder();
                        for (VariableElement ep : executableElement.getParameters()) {
                            methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                            paramsString.append(ep.getSimpleName().toString()).append(",");
                        }
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".create($T.class)" +
                                        ".$L($L)" +
                                        ".compose($T.cutoverSchedulers())"
                                , ClassName.get("com.whamu2.wanandroid.utils", "ApiUtil")
                                , ClassName.get(packageName, simpleName)
                                , e.getSimpleName().toString()
                                , paramsString.substring(0, paramsString.length() - 1)
                                , ClassName.get("com.whamu2.wanandroid.utils", "Transformer"));

                        /*添加方法到构造器中*/
                        builder.addMethod(methodBuilder.build());
                    }
                }

                if (packageName != null) {
                    JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build();
                    javaFile.writeTo(processor.mFiler);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
