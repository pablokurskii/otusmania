package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.reflect.Method;

public class TestRunner {

    private final Class<ClassTest> testClass;

    public TestRunner(Class<ClassTest> testClass) {
        this.testClass = testClass;
    }

    public void run(){
        try {
            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)){

                }
                else if (method.isAnnotationPresent(Test.class)) {
                    method.invoke(testClass);
                }
                else if (method.isAnnotationPresent(After.class)) {
                    method.invoke(testClass);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setUp(Object instance){
        scrapMethodsWith(Before.class).forEach(method -> exec(method, instance));
    }
}
