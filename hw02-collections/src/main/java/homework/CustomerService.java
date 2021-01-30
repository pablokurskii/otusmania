package homework;


import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;

public class CustomerService {

    NavigableMap<Customer, String> map = new TreeMap<Customer, String>(Comparator.comparingLong(Customer::getScores));


    public void walkMap(){
        map.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));
    }
    public Map.Entry<Customer, String> getSmallest() {
        return getOriginal(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.higherEntry(customer);
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
