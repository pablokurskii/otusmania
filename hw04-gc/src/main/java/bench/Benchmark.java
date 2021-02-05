package bench;


import java.util.ArrayList;
import java.util.Properties;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() {
        long count = 0;
        try {
            ArrayList<myClass> stuff = new ArrayList<>();
            while (count < size) {
                count++;
                myClass p = new myClass();
                p.put("title", "Title #" + count);
                stuff.add(p);
                if ((count % (size / 100)) == 0) {
                    for (int idx = 0; idx < loopCounter; idx++) {
                        Object[] array = new Object[size/100];
                        for (int i = 0; i < loopCounter; i++) {
                            array[i] = new String(new char[0]);
                        }
                    }
                    System.out.println("Total elements saved = " + stuff.size() + " - " + (((count * 100) / size)) + "% done");
                    Thread.sleep(1000);
                }
            }
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("Exception at count" + count);
        }

    }

    public class myClass extends Properties {
        myClass() {
            super();
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
