package homework;


import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class ClassTest {

    @Before
    public void before1() {
    }

    @Test
    public void test1() throws Exception {
        throw new Exception();
    }

    @After
    public void after1() {
    }

    @Test
    public void test2() {
    }

    @After
    public void after2() {
    }
}
