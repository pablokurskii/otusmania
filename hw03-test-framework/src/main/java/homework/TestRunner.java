package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private final Class<ClassTest> classTest;

    public TestRunner(Class<ClassTest> classTest) {
        this.classTest = classTest;
    }

    public List<TestExecutionResultDetails> run() {
        try {
            Object instanceBefore = classTest.getDeclaredConstructor().newInstance();
            runBeforeTest(instanceBefore);

            Object instanceTest = classTest.getDeclaredConstructor().newInstance();
            List<TestExecutionResultDetails> results = runTests(instanceTest);

            Object instanceAfter = classTest.getDeclaredConstructor().newInstance();
            runAfterTest(instanceAfter);

            return results;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void runBeforeTest(Object instance) {
        scrapMethodsWith(Before.class).forEach(method -> exec(method, instance));
    }

    private List<TestExecutionResultDetails> runTests(Object instance) {
        List<TestExecutionResultDetails> results = new ArrayList<>();
        scrapMethodsWith(Test.class).forEach(method -> results.add(runTest(method, instance)));
        return results;
    }

    private void runAfterTest(Object instance) {
        scrapMethodsWith(After.class).forEach(method -> exec(method, instance));
    }

    private List<Method> scrapMethodsWith(Class<? extends Annotation> annotation) {
        List<Method> declaredMethods = new ArrayList<>();
        for (Method declaredMethod : classTest.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(annotation)) {
                declaredMethods.add(declaredMethod);
            }
        }
        return declaredMethods;
    }

    private TestExecutionResultDetails runTest(Method method, Object instance) {
        try {
            System.out.printf("Running %s test\n", method.getName());
            method.invoke(instance);
            return new TestExecutionResultDetails(method.getName(), "Passed", "Success");
        } catch (Exception e) {
            return new TestExecutionResultDetails(method.getName(), "Failed", e.getCause().toString());
        }
    }

    private void exec(Method method, Object instance) {
        try {
            System.out.printf("Running %s method\n", method.getName());
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
