package aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        private final List<?> logMethods;

        DemoInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.logMethods = scrapMethodsWith(Log.class);
            logMethods.forEach(System.out::println);
        }

        private boolean requiresLogging(Method method) {
            String methodModifiers = Modifier.toString(method.getModifiers());
            String returnType = method.getReturnType().getName();
            String methodName = method.getName();
            String fullSignature = methodModifiers + " " + returnType + " " + methodName;
            System.out.println(fullSignature);
//            System.out.println("requiresLogging "+method.toString());
//            String signature = method.getName()+"(";
            /*Arrays.stream(method.getParameterTypes())
                    .forEach(type -> {
                         signature+=type.toString();
                    });*/
//            System.out.println(signature.toString());
//            System.out.println(logMethods.contains(signature.toString()));
            return false;
        }

        private List<Method> scrapMethodsWith(Class<? extends Annotation> annotation) {

            List<Method> declaredMethods = new ArrayList<>();
            for (Method declaredMethod : testLogging.getClass().getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(annotation)) {
                    declaredMethods.add(declaredMethod);
                    System.out.println("scrapMethodsWith " + declaredMethod.toString());
                }
            }
            return declaredMethods;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (this.requiresLogging(method)) {
                String methodName = method.getName();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append("param")
                            .append(i)
                            .append(": ")
                            .append(args[i]);
                    if (i != args.length - 1) sb.append(" ");
                }
                System.out.printf("executed method: %s, %s\n", methodName, sb.toString());
            }

            return method.invoke(testLogging, args);
        }
    }


}
