package homework;


import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class ClassTest {

    @Before
    public void beforeEachTest() {
    }

    @Test
    public void test1() throws Exception {
        throw new Exception();
    }

    @After
    public void afterEachTest() {
    }

    @Test
    public void test2() {
    }
}
