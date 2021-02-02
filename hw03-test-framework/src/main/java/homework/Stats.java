package homework;

import java.util.List;

/**
 * Generates output report of Test execution result details
 */

public class Stats {
    public void get(List<TestExecutionResultDetails> testExecutionResultDetails) {
        int testsQty = testExecutionResultDetails.size();
        long passedTestsQty = testExecutionResultDetails.stream()
                .filter(result -> result.getResult().equals("PASSED"))
                .count();
        long failedTestsQty = testExecutionResultDetails.stream()
                .filter(result -> result.getResult().equals("FAILED"))
                .count();

        System.out.printf("Run %d tests. Tests passed: %d. Tests failed: %d.", testsQty, passedTestsQty, failedTestsQty);
    }
}
