package homework;

import java.util.List;

public class MyTestFramework {
    public static void runAllTests(){
        TestExecutionResultPrinter testExecutionResultPrinter = new TestExecutionResultPrinter();
        List<TestExecutionResultDetails> testExecutionResultDetails = new TestRunner(ClassTest.class).run();
        testExecutionResultPrinter.print(testExecutionResultDetails);
    }
}
