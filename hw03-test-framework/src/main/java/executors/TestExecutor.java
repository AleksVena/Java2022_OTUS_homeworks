package executors;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestExecutor implements Executor {

    public static final String TESTS_FINISHED = "Tests are finished";

    public static final String TEST_RESULT = "executed: %s, passed: %s, failed: %s";

    private final Class<?> testClass;

    private final TestsResult testsResult;

    private final List<Method> beforeMethods;

    private final List<Method> afterMethods;

    private final List<Method> testMethods;

    public TestExecutor(String classWithTests) throws ClassNotFoundException {
        testClass = Class.forName(classWithTests);

        Method[] methods = testClass.getDeclaredMethods();

        beforeMethods = Arrays.stream(methods)
                .filter(it -> it.getAnnotation(Before.class) != null)
                .collect(Collectors.toList());

        afterMethods = Arrays.stream(methods)
                .filter(it -> it.getAnnotation(After.class) != null)
                .collect(Collectors.toList());

        testMethods = Arrays.stream(methods)
                .filter(it -> it.getAnnotation(Test.class) != null)
                .collect(Collectors.toList());

        testsResult = new TestsResult(testMethods.size());
    }

    public void execute() throws Exception {
        for (Method testMethod : testMethods) {
            invokeTest(testClass, beforeMethods, afterMethods, testMethod, testsResult);
        }

        final String result = String.format(TEST_RESULT, testsResult.getAllTestCount(), testsResult.getSuccessTests(), testsResult.getFailTests());
        System.out.println(TESTS_FINISHED);
        System.out.println(result);
    }

    private void invokeTest(Class<?> testClass, List<Method> beforeMethods,
                            List<Method> afterMethods, Method testMethod, TestsResult testsResult) throws Exception {
        Object instance = testClass.getDeclaredConstructor().newInstance();
        boolean isSuccess = true;

        try {
            for (Method beforeMethod : beforeMethods) {
                invokeMethod(beforeMethod, instance);
            }

            invokeMethod(testMethod, instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwable targetException;
            if (e instanceof InvocationTargetException invocationTargetException) {
                targetException = invocationTargetException.getTargetException();

                if (targetException instanceof AssertionError assertionError) {
                    System.err.println(assertionError.getMessage());
                }
            }
            isSuccess = false;
        } finally {
            try {
                for (Method afterMethod : afterMethods) {
                    invokeMethod(afterMethod, instance);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                isSuccess = false;
            }
        }

        if (isSuccess) {
            testsResult.incrementSuccess();
        } else {
            testsResult.incrementFail();
        }
    }

    private void invokeMethod(Method beforeMethod, Object instance) throws IllegalAccessException, InvocationTargetException {
        beforeMethod.setAccessible(true);
        beforeMethod.invoke(instance);
    }
}
