package aop;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param) {
        System.out.println("Hello " + param);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Hello " + param1 + ", " + param2);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Hello " + param1 + ", " + param2 + ", " + param3);
    }

    @Override
    public void calculation(int param1, char param2, Boolean param3) {
        System.out.println("Hello " + param1 + ", " + param2 + ", " + param3);
    }
}
