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

    private List<Method> beforeMethods;
    private List<Method> afterMethods;

    public TestRunner(Class<ClassTest> classTest) {
        this.classTest = classTest;
    }

    public List<TestExecutionResultDetails> run() {

        List<TestExecutionResultDetails> results = new ArrayList<>();

        beforeMethods = scrapMethodsWith(Before.class);
        afterMethods = scrapMethodsWith(After.class);
        scrapMethodsWith(Test.class).forEach(method -> results.add(runTest(method)));

        return results;

    }

    private void runBeforeTest(Object instance) throws Exception {
        for (Method method : beforeMethods) {
            try {
                exec(method, instance);
            } catch (Exception e) {
                System.out.printf("Failed startup %s method\n", method.getName());
                throw new Exception(e);
            }
        }
    }

    private void runAfterTest(Object instance) throws Exception {
        for (Method method : afterMethods) {
            try {
                exec(method, instance);
            } catch (Exception e) {
                System.out.printf("Failed teardown %s method\n", method.getName());
                throw new Exception(e);
            }
        }
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
            Object classTestInstance = classTest.getDeclaredConstructor().newInstance();

            System.out.println("Startup");
            runBeforeTest(classTestInstance);

            System.out.printf("Executing %s test\n", method.getName());
            method.invoke(classTestInstance);

            System.out.println("Teardown");
            runAfterTest(classTestInstance);

            return new TestExecutionResultDetails(method.getName(), "Passed", "Success");
        } catch (Exception e) {
            System.out.printf("Failed executing %s test\n", method.getName());
            return new TestExecutionResultDetails(method.getName(), "Failed", e.getCause().toString());
        }
    }

    private void exec(Method method, Object instance) throws Exception {
        try {
            System.out.printf("Executing %s method\n", method.getName());
            method.invoke(instance);
        } catch (Exception e) {
            throw new Exception("Failed executing method "+method.getName(), e);
        }

    }
}
