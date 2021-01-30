# VM Options чтобы получить OOM
> -Xms512m<br>
-Xmx512m<br>
-XX:+HeapDumpOnOutOfMemoryError<br>
-<u>XX:+UnlockExperimentalVMOptions<br>
-XX:+UseEpsilonGC</u>

# VM Options для тестов GC

> -Xms512m<br>
-Xmx512m<br>
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m<br>
-XX:+HeapDumpOnOutOfMemoryError<br>
-XX:HeapDumpPath=./logs/dump<br>



## GC List

GC | VM Option
------------ | -------------
Serial Collector | -XX:+UseSerialGC
Parallel Collector | -XX:+UseParallelGC
CMS | -XX:+UseConcMarkSweepGC
G1 | -XX:+UseG1GC
ZGC | -XX:+UnlockExperimentalVMOptions-XX:+UseZGC


## Результаты

### Serial Collector

### Parallel Collector

### CMS

### G1

### ZGC


## Резюме

