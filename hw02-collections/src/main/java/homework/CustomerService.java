package homework;


import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;

public class CustomerService {

    NavigableMap<Customer, String> map = new TreeMap<Customer, String>(Comparator.comparingLong(Customer::getScores));
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны


    public void walkMap(){
        map.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));
    }
    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return getOriginal(map.firstEntry()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.higherEntry(customer); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        map.put(customer,data);
    }

    /*
    * Mutation test
    * */
    private Map.Entry<Customer, String> getOriginal(Map.Entry<Customer, String> customer) {
        return new OriginalEntry(customer.getKey(), customer.getValue());
    }
    private static class OriginalEntry implements Map.Entry<Customer, String> {

        private final Customer customer;
        private String value;

        public OriginalEntry(Customer key, String value) {
            customer = new Customer(key.getId(), key.getName(), key.getScores());
            this.value=value;
        }

        @Override
        public Customer getKey() {
            return customer;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return value;
        }
    }
}
