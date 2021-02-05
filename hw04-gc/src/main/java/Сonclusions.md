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
Execution time: 139 sec<br>
Garbage Collector Copy processed 44 times.<br>
Processing speed 18 times/min<br>
Garbage Collector MarkSweepCompact processed 140 times.<br>
Processing speed 60 times/min

### Parallel Collector
Execution time: 76 sec<br>
Garbage Collector PS MarkSweep processed 77 times.<br>
Processing speed 60 times/min<br>
Garbage Collector PS Scavenge processed 50 times.<br>
Processing speed 39 times/min

### CMS
Execution time: 98 sec<br>
Garbage Collector ParNew processed 52 times<br>
Processing speed 31 times/min<br>
Garbage Collector ConcurrentMarkSweep processed 84 times.<br>
Processing speed 51 times/min

### G1
Execution time: 65 sec<br>
Garbage Collector G1 Young Generation processed 201 times.<br>
Processing speed 185 times/min<br>
Garbage Collector G1 Old Generation processed 26 times.<br>
Processing speed 24 times/min

## Показатели Full gc pauses
Данные указаны в секундах.

| GC  | min  | avg  | max | total 
|---|---|---|---|---|
Serial Collector | 0.05862 | 0.38327 | 0.4835 | 75.12
Parallel Collector | 0.02074 | 0.21167 | 0.44827 | 17.78
CMS | 0.16412 | 0.48366 | 0.65951 | 35.31
G1 | 0.10947 | 0.1243 | 0.17269| 3.23


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
