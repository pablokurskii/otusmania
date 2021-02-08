package homework;

import java.util.List;

/**
 * Generates output report of Test execution result details
 */

public class Stats {
    public void get(List<TestExecutionResultDetails> testExecutionResultDetails) {
        int testsQty = testExecutionResultDetails.size();
        long passedTestsQty = testExecutionResultDetails.stream()
                .filter(result -> result.getResult().equals("Passed"))
                .count();
        long failedTestsQty = testExecutionResultDetails.stream()
                .filter(result -> result.getResult().equals("Failed"))
                .count();

        System.out.printf("Run %d tests. Tests passed: %d. Tests failed: %d.\n", testsQty,
                passedTestsQty, failedTestsQty);

        testExecutionResultDetails.stream()
                .filter(result -> result.getResult().equals("Failed"))
                .forEach(this::printDetails);

    }

    private void printDetails(TestExecutionResultDetails testExecutionResultDetails) {
        System.out.printf("Test %s failed with reason: %s\n",testExecutionResultDetails.method,
                testExecutionResultDetails.getDetails());
    }

}
