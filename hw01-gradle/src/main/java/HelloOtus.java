import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        genericsProblem();
        guavaSolution();
    }

    /**
     * Problem
     * taken as example from https://www.baeldung.com/guava-reflection
     * */
    private static void genericsProblem(){

        List<String> stringList = Lists.newArrayList();
        List<Integer> intList = Lists.newArrayList();

        boolean result = stringList.getClass()
                .isAssignableFrom(intList.getClass());

        System.out.println("stringList isAssignableFrom intList:" + result);
    }

    /**
     * Guava solution
     * */
    @SuppressWarnings("UnstableApiUsage")
    private static void guavaSolution(){
        TypeToken<List<String>> stringListToken
                = new TypeToken<List<String>>() {};
        TypeToken<List<Integer>> integerListToken
                = new TypeToken<List<Integer>>() {};
        TypeToken<List<? extends Number>> numberTypeToken
                = new TypeToken<List<? extends Number>>() {};

        System.out.println("stringListToken isSubtypeOf integerListToken:" + stringListToken.isSubtypeOf(integerListToken));
        System.out.println("numberTypeToken isSubtypeOf integerListToken:" + numberTypeToken.isSubtypeOf(integerListToken));
        System.out.println("integerListToken isSubtypeOf numberTypeToken:" + integerListToken.isSubtypeOf(numberTypeToken));
    }

}
