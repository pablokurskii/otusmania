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
Execution time: 32 sec<br>
Garbage Collector Copy processed 999 times.<br>
Processing speed 16 times/min<br>
Garbage Collector MarkSweepCompact processed 3 times.<br>
Processing speed 0 times/min

### Parallel Collector
Execution time: 529 sec
Garbage Collector PS MarkSweep processed 499 times.
Processing speed 8 times/min
Garbage Collector PS Scavenge processed 2327 times.
Processing speed 38 times/min

### CMS
Execution time: 34 sec
Garbage Collector ParNew processed 999 times.
Processing speed 16 times/min
Garbage Collector ConcurrentMarkSweep processed 5 times.
Processing speed 0 times/min

### G1
Execution time: 87 sec<br>
Garbage Collector G1 Young Generation processed 2016 times.<br>
Processing speed 33 times/min<br>
Garbage Collector G1 Old Generation processed 2 times.<br>
Processing speed 0 times/min

### ZGC
Execution time: 47 sec
Garbage Collector ZGC processed 529 times.
Processing speed 8 times/min

## Резюме

