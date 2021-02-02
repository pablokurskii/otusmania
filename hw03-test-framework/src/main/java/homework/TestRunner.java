package homework;

import homework.annotations.After;
import homework.annotations.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    private final Class<ClassTest> classTest;

    public TestRunner(Class<ClassTest> classTest) {
        this.classTest = classTest;
    }

    public List<TestExecutionResultDetails> run() {
        try {
//            for (Method method : classTest.getDeclaredMethods()) {
                Object instance = classTest.getDeclaredConstructor().newInstance();

                runBeforeTest(instance);
                TestExecutionResultDetails testExecutionResultDetails = runTest(method,instance);
                runAfterTest(instance);

//            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    private void runBeforeTest(Object instance) {
        scrapMethodsWith(Before.class).forEach(method -> exec(method, instance));
    }

    private TestExecutionResultDetails runTest(Method method, Object instance) {
        try {
            System.out.printf("exec %s test\n", method.getName());
            method.invoke(instance);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void runAfterTest(Object instance) {
        scrapMethodsWith(After.class).forEach(method -> exec(method, instance));
    }

    private List<Method> scrapMethodsWith(Class<? extends Annotation> annotation) {
        List<Method> declaredMethods = new ArrayList<>();
        for (Method declaredMethod : classTest.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(annotation)){
                declaredMethods.add(declaredMethod);
            }
        }
        return declaredMethods;
    }

    private void exec(Method method, Object instance) {
        try {
            System.out.printf("exec %s method\n", method.getName());
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
