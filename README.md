# Seek Code Challenge

The program comes with a class object called `Record` representing each line of the given trace file. In this class, there are the following class fields:

- Field of type `LocalDate` for representing the specified date in the trace file,
- Field of type `LocalTime` for representing the specified time in the trace file,
- Field of type `int` for representing the number of cars per each line,
- Field of type `int` for representing the number of seen cars in contiguous half hours,
- Field of type `List<Record>` for representing corresponding contiguous records, and
- Field of type `List<LocalDateTime>` for representing the corresponding contiguous records. 

The aim of this code challenge program is to output:

- <h3>The number of cars seen in total</h3>



There is a function called `printTotalNumberOfCars(List<Record> T)` that takes the list of `Record` objects and produces the total number of seen cars with respect to the records as follows: 
```bash
398
```
---

- <h3> A sequence of lines where each line contains a date (in yyyy-mm-dd format) and the number of cars seen on that day (eg. 2016-11-23 289) for all days listed in the input file. </h3>

The function ``printRecordsPerSpecifiedDate(List<Record> T)`` is responsible for computing the sequence of lines, outputting the seen cars per each date in the trace file as follows:

```bash
2021-12-01 179
2021-12-05 81
2021-12-08 134
2021-12-09 4
```
---

- <h3> The top 3 half hours with most cars, in the same format as the input file </h3>

The function ``printTopThreeHalfHours(List<Record> T)`` computes the top half hours with the most seen cars in the trace file as follows:

```bash
2021-12-01T07:30 46
2021-12-01T08:00 42
2021-12-08T18:00 33
```
---

- <h3> The 1.5 hour period with least cars (i.e. 3 contiguous half hour records) </h3>

The function ``printOneHourAndHalfContiguous(List<Record> T)`` calcualtes the least seen cars in the 1.5 hour period as follows:

```bash
[2021-12-01T05:00, 2021-12-01T05:30, 2021-12-01T06:00] 31
```

## Installation
This program is written in Java-1.8.0.* accompanied by JUnit-5.6.0 for unit testing in Netbeans. 
