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


## Показания

### Serial Collector
Execution time: 33 sec<br>
Garbage Collector Copy processed 999 times.<br>
Processing speed 16 times/min<br>
Garbage Collector MarkSweepCompact processed 5 times.<br>
Processing speed 0 times/min

### Parallel Collector
Execution time: 529 sec<br>
Garbage Collector PS MarkSweep processed 499 times.<br>
Processing speed 8 times/min<br>
Garbage Collector PS Scavenge processed 2327 times.<br>
Processing speed 38 times/min

### CMS
Execution time: 34 sec<br>
Garbage Collector ParNew processed 999 times.<br>
Processing speed 16 times/min<br>
Garbage Collector ConcurrentMarkSweep processed 5 times.<br>
Processing speed 0 times/min

### G1
Execution time: 82 sec<br>
Garbage Collector G1 Young Generation processed 1623 times.<br>
Processing speed 27 times/min<br>
Garbage Collector G1 Old Generation processed 4 times.<br>
Processing speed 0 times/min

## Комментарии

Сравнивая результаты отмечаю, что время выполнения программы заметно меньше с использованием
Serial Collector и CMS. В результате можно предположить, что для данного приложения (нет многопоточности) в текущей конфигурации (домашний ПК Windows 10)
и отсутствии требований к времени простоя (время паузы для сборки мусора) очевидным выбором будет Serial Collector. 
 

С другой стороны если существуют требования к времени простоя приложения (должно быть коротким), то зачастую выбирается 
Parallel Collector. Нагрузка на процессор больше по сравнению с Serial Collector, так как сборка производится в несколько потоков, вместо одного. 
В нашем случае, очевидно, не стоит использовать 
данный сборщик. Скорость выполнения приложения возросла <u>16 раз!</u> по сравнению с Serial Collector.


CMS показал результат очень схожий с Serial Collector. Хорошо подойдет для серверных приложений с длительным
uptime и производительным CPU (GC может потребовать дополнительные ресурсы CPU во время работы) и размером heap менее 4GB.
Является устаревшим начиная с версии Java 9. Имеет наибольшую пропускную способность.

G1 является сборщиком мусора по умолчанию для Java 11, которая использовалась в данном проекте. Лучше всего использовать на многопроцессорных серверах с большим объемом памяти.
Показал средние результаты для нашей конфигурации и приложения.
Наименьшая пауза STW. Высшее потребление ресурсов процессора.

##  Вывод

Исходя из полученных показаний и комментариев можно сделать вывод, что
выбор GC зависит во многом от приложения для которого он должен работать. На выбор также влияют ресурсы, 
доступные для приложения, а именно параметры CPU и размер Heap. Для современных приложений, запущенных на клиентских машинах или серверах
стоит использовать G1, который выбран по умолчанию для версий старше Java 11.
