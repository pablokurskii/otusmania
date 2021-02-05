package homework;


import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestExecutionResultPrinter testExecutionResultPrinter = new TestExecutionResultPrinter();
        List<TestExecutionResultDetails> testExecutionResultDetails = new TestRunner(ClassTest.class).run();
        testExecutionResultPrinter.print(testExecutionResultDetails);
    }

}
