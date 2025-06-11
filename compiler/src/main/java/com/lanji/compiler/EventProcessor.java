package com.lanji.compiler;

import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lanji.annotation.EventAnnotation;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 每一个注解处理器类都必须有一个空的构造函数，默认不写就行
 */
@AutoService(Processor.class)
public class EventProcessor extends AbstractProcessor {
    /**
     * init()方法会被注解处理器工具调用，并输入ProcessingEnvironment参数。
     * ProcessingEnvironment 提供很多有用的工具类Elements，Types和Filter
     * @param processingEnvironment 提供给process用来访问工具框架的环境
     */
    private Filer mFiler;
    private Messager messager;
    private ProcessingEnvironment processingEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        processingEnv = processingEnvironment;
        messager.printMessage(Diagnostic.Kind.NOTE, "CustomProcessor initialized");
    }

    private String readJsonFile(String jsonPath) {
        try {
            // 获取项目根目录
            String projectDir = processingEnv.getOptions().get("projectDir");
            if (projectDir == null) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Project directory not found");
                return null;
            }

            // 构建完整的文件路径（指向 assets 目录）
            File jsonFile = new File(projectDir, "src/main/assets/" + jsonPath);
            if (!jsonFile.exists()) {
                messager.printMessage(Diagnostic.Kind.ERROR, "JSON file not found: " + jsonFile.getAbsolutePath());
                return null;
            }

            // 读取文件内容
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Error reading JSON file: " + e.getMessage());
            return null;
        }
    }

    /**
     * 相当于每个处理器的主函数main()，在这里写扫描、评估和处理注解的代码，以及生成java文件。
     * 输入参数RoundEnvironment可以查询出包含特定注解的被注解元素
     * @param set 请求处理注解类型
     * @param roundEnvironment 有关当前和以前的信息环境
     * @return 返回true，则这些注解已声明并且不要求后续Processor处理他们;
     *          返回false，则这些注解未声明并且可能要求后续Processor处理他们;
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing started");
        
        for (Element element : roundEnvironment.getElementsAnnotatedWith(EventAnnotation.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Found annotated element: " + element.getSimpleName());
            
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                EventAnnotation annotation = element.getAnnotation(EventAnnotation.class);
                String jsonPath = annotation.jsonPath();
                
                messager.printMessage(Diagnostic.Kind.NOTE, "Processing class: " + typeElement.getSimpleName() + 
                    " with value: "  + ", jsonPath: " + jsonPath);

                // 读取 JSON 文件
                String jsonContent = readJsonFile(jsonPath);
                if (jsonContent == null) {
                    return false;
                }

                // 解析 JSON
                JsonArray events = JsonParser.parseString(jsonContent).getAsJsonArray();

                String className = "EventConstants";
                
                // 创建类构建器
                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

                // 为每个事件添加常量
                for (JsonElement eventElement : events) {
                    JsonObject event = eventElement.getAsJsonObject();
                    String eventName = event.get("eventName").getAsString();
                    // 将事件名转换为常量名格式（大写，下划线分隔）
                    String constantName = eventName.toUpperCase().replace(" ", "_");
                    
                    FieldSpec eventField = FieldSpec.builder(String.class, constantName)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$S", eventName)
                            .build();
                    
                    classBuilder.addField(eventField);
                }

//                // 添加 main 方法
//                MethodSpec main = MethodSpec.methodBuilder("main")
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .returns(void.class)
//                        .addParameter(String[].class, "args")
//                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
//                        .build();
//
//                classBuilder.addMethod(main);

                try {
                    JavaFile javaFile = JavaFile.builder("com.lanji.annotation", classBuilder.build())
                            .addFileComment("This codes are generated automatically. Do not modify!")
                            .build();
                    javaFile.writeTo(mFiler);
                    messager.printMessage(Diagnostic.Kind.NOTE, "Generated file: " + className + ".java");
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 这里必须指定，这个注解处理器是注册给那个注解的。
     * 注意：它的返回值是一个字符串的集合，包含本处理器想要处理注解的注解类型的合法全程。
     * @return 注解器所支持的注解类型集合，如果没有这样的类型，则返回一个空集合。
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(EventAnnotation.class.getCanonicalName());
        return annotations;
    }

    /**
     * 指定Java版本，通常这里使用SourceVersion.latestSupported(),
     * 默认返回SourceVersion.RELEASE_6
     * @return 使用的Java版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

