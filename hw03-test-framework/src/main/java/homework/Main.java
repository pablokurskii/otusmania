package homework;


import java.util.List;

public class Main {

    public static void main(String[] args) {
        Stats stats = new Stats();
        List<TestExecutionResultDetails> testExecutionResultDetails = new TestRunner(ClassTest.class).run();
        stats.get(testExecutionResultDetails);
    }

}
