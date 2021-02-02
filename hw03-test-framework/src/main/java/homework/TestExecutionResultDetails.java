package homework;


public class TestExecutionResultDetails {
    String method;
    String result;
    String details;

    public TestExecutionResultDetails(String method, String result, String details) {
        this.method = method;
        this.result = result;
        this.details = details;
    }

    public String getResult() {
        return result;
    }

    public String getDetails() {
        return details;
    }
}
