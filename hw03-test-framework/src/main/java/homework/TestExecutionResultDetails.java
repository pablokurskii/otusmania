package homework;


public class TestExecutionResultDetails {
    private final String method;
    private final String result;
    private final String details;

    public TestExecutionResultDetails(String method, String result, String details) {
        this.method = method;
        this.result = result;
        this.details = details;
    }

    public String getMethod() {
        return method;
    }

    public String getResult() {
        return result;
    }

    public String getDetails() {
        return details;
    }
}
