package kz.alseco;

/*
 * To start the application:
 * ./gradlew build
 * java -jar ./L01-gradle/build/libs/gradleHelloOtus-0.1.jar
 *
 * To unzip the jar:
 * unzip -l L01-gradle.jar
 * unzip -l gradleHelloWorld-0.1.jar
 *
 */

import com.google.common.base.CaseFormat;

public class HelloOtus {
    public static void main(String... args) {
        testCaseFormat();
    }

    private static void testCaseFormat() {
        String data = "test_data";
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));
    }
}
