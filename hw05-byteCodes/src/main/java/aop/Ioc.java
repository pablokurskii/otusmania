package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }


    public static TestLogging createTestLogging() {

        InvocationHandler invocationHandler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, invocationHandler);

    }

    static class DemoInvocationHandler implements InvocationHandler {

        private final TestLogging testLogging;

        DemoInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("param")
                        .append(i)
                        .append(": ")
                        .append(args[i]);
                if (i != args.length-1) sb.append(" ");
            }
            System.out.printf("executed method: %s, %s\n", methodName, sb.toString());

            return method.invoke(testLogging, args);
        }
    }


}
