package aop;

public class Demo {
    public static void action() {
        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(6);
        testLogging.calculation(6, 4);
        testLogging.calculation(6, 2, "Algo más");
        testLogging.calculation(6, '%', false);
    }
}
