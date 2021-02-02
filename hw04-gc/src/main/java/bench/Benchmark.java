package bench;



class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int i = 0; i < loopCounter; i++) {
            for (int j = 0; j < 50; j++) {
              GrandPa grandPa = new GrandPa(size*11);
              Pa pa = new Pa(size*16);
              YoungOne youngOne = new YoungOne();

              pa.father = grandPa;
              youngOne.father = pa;

              youngOne.father.fatherPassedAway();

              youngOne.money = youngOne.father.money;
              youngOne.father.money = new String[100];
              youngOne.growUp();
//              Thread.sleep(10);
                for (int idx = 0; idx < loopCounter; idx++) {
                    int local = size;
                    Object[] array = new Object[local];
                    Object[] array2 = new Object[local];
                    for (int k = 0; k < local; k++) {
                        array[k] = new String(new char[48]);
                        array2[k] = array;
                    }
//                Thread.sleep(10); //Label_1
                }
            }



            System.out.println(i);

        }

    }

    static class GrandPa{
        private final String[] money;

        public GrandPa(int money) {
            this.money = new String[money];
        }
    }

    static class Pa{
        private GrandPa father;
        private String[] money;

        public Pa(int money) {
            this.money = new String[money];
        }

        void fatherPassedAway(){
            money = father.money;
            father = null;
        }
    }

    static class YoungOne{
        public String[] money;
        private Pa father;
        void growUp(){
            money = father.money;
            father = null;
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
