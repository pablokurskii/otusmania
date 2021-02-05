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

    private Object classTestInstance;

    public TestRunner(Class<ClassTest> classTest) {
        this.classTest = classTest;
    }

    public List<TestExecutionResultDetails> run() {

        List<TestExecutionResultDetails> results = new ArrayList<>();

        scrapMethodsWith(Test.class).forEach(method -> results.add(runTest(method)));

        return results;

    }

    private void runBeforeTest(Object instance) {
        scrapMethodsWith(Before.class).forEach(method -> exec(method, instance));
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

    private TestExecutionResultDetails runTest(Method method) {

        try {
            classTestInstance = classTest.getDeclaredConstructor().newInstance();

            System.out.printf("Startup\n", method.getName());
            runBeforeTest(classTestInstance);

            System.out.printf("Executing %s test\n", method.getName());
            method.invoke(classTestInstance);

            System.out.printf("Teardown\n", method.getName());
            runAfterTest(classTestInstance);

            return new TestExecutionResultDetails(method.getName(), "Passed", "Success");
        } catch (Exception e) {
            System.out.printf("Teardown %s test with exception\n", method.getName());
            runAfterTest(classTestInstance);

            return new TestExecutionResultDetails(method.getName(), "Failed", e.getCause().toString());
        }
    }

    private void exec(Method method, Object instance) {
        try {
            System.out.printf("Executing %s method\n", method.getName());
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
