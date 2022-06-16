package SeekCodeChallenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Amirmohammad
 */
public class Main {

    public static final String TRACE_FILE = "traces.txt";
    public static BufferedReader br;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
    The function reads the trace file, extracts data, time, and number of cars,
    and stores it in a list of Record object.
     */
    public static List<Record> readTraceFile() {
        List<Record> traceRecordList = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(TRACE_FILE));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    String[] lineSplit = line.split(" ");
                    LocalDateTime ldateTime = LocalDateTime.parse(lineSplit[0]);
                    traceRecordList.add(new Record(
                            ldateTime.toLocalTime(),
                            ldateTime.toLocalDate(),
                            Integer.parseInt(lineSplit[1])
                    ));

                }

                System.out.println(printTotalNumberOfCars(traceRecordList));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return traceRecordList;
    }

    /*
    * Print the total number of seen cars in the trace file
     */
    public static int printTotalNumberOfCars(List<Record> traceRecordList) {
        int totalCars = 0;
        System.out.println("Total number of seen cars: ");
        for (int i = 0; i < traceRecordList.size(); i++) {
            totalCars += traceRecordList.get(i).getNmbrsCars();
        }
        return totalCars;
    }

    /*
    * Print the seen cars per specified dates in the trace file
     */
    public static HashMap<LocalDate, Integer> printRecordsPerSpecifiedDate(List<Record> traceRecordList) {

        HashSet<LocalDate> localDateSet = new HashSet<>();
        HashMap<LocalDate, Integer> seenCarsPerDate = new HashMap<>();

        for (Record record : traceRecordList) {
            localDateSet.add(record.getLocalDate());
        }

        for (LocalDate ldate : localDateSet) {
            int seenCarsPerSpecifiedDate = 0;
            for (Record record : traceRecordList) {
                if (record.getLocalDate().equals(ldate)) {
                    seenCarsPerSpecifiedDate += record.getNmbrsCars();
                }
            }
            seenCarsPerDate.put(ldate, seenCarsPerSpecifiedDate);
            System.out.println("A record found with " + ldate + " with the seen cars " + seenCarsPerSpecifiedDate);
        }
        return seenCarsPerDate;

    }

    /*
    * Compute the top three half hours with most seen cars
     */
    public static HashMap<LocalDateTime, Integer> printTopThreeHalfHours(List<Record> traceRecordList) {
        List<Record> traceList = new ArrayList<>(traceRecordList);
        HashMap<LocalDateTime, Integer> topThreeHaldHours = new HashMap<>();
        Collections.sort(traceList);
        for (Record record : traceList) {
            LocalDateTime ldateTime = LocalDateTime.of(record.getLocalDate(), record.getLocalTime());
            System.out.println(ldateTime + " " + record.getNmbrsCars());
        }

        LocalDateTime ldateTime = LocalDateTime.of(traceList.get(0).getLocalDate(), traceList.get(0).getLocalTime());
        topThreeHaldHours.put(ldateTime, traceList.get(0).getNmbrsCars());

        ldateTime = LocalDateTime.of(traceList.get(1).getLocalDate(), traceList.get(1).getLocalTime());
        topThreeHaldHours.put(ldateTime, traceList.get(1).getNmbrsCars());

        ldateTime = LocalDateTime.of(traceList.get(2).getLocalDate(), traceList.get(2).getLocalTime());
        topThreeHaldHours.put(ldateTime, traceList.get(2).getNmbrsCars());

        return topThreeHaldHours;
    }

    /*
    * Determine the list of one hour and half contiguous records in the trace file
    * Prints the least seen cars in that period
     */
    public static HashMap<LocalDateTime, Integer> printOneHourAndHalfContiguous(List<Record> traceRecordList) {
        int step = 3;
        List<Record> contiguousList = new ArrayList<>();
        HashMap<LocalDateTime, Integer> oneHourAndHalfContiguous = new HashMap<>();

        System.out.println(traceRecordList.size() - 1 + " >>> " + (traceRecordList.size() - step));

        for (int i = 0; i <= traceRecordList.size() - step; i++) {

            List<Record> contiguousHalfHour = new ArrayList<>();

            LocalDate lDate = traceRecordList.get(i).getLocalDate();
            LocalTime lTime = traceRecordList.get(i).getLocalTime();
            int seenCars = traceRecordList.get(i).getNmbrsCars();

            contiguousHalfHour.add(traceRecordList.get(i));

            for (int j = i + 1; j < step + i; j++) {
                if (traceRecordList.get(j).getLocalDate().equals(lDate) && Math.abs(MINUTES.between(lTime, traceRecordList.get(j).getLocalTime())) == 30) {
                    contiguousHalfHour.add(traceRecordList.get(j));
                } else if (!traceRecordList.get(j).getLocalDate().equals(lDate) && traceRecordList.get(j).getLocalTime().getHour() == 0) {
                    if (Math.abs(24 * 60 + traceRecordList.get(j).getLocalTime().getMinute() - lTime.getHour() * 60 + lTime.getMinute()) == 30) {
                        contiguousHalfHour.add(traceRecordList.get(j));
                    }
                }
                lDate = traceRecordList.get(j).getLocalDate();
                lTime = traceRecordList.get(j).getLocalTime();
            }

            System.out.println("Three contiguous " + contiguousHalfHour.size());

            if (contiguousHalfHour.size() == 3) {
                contiguousList.add(new Record(contiguousHalfHour));
            }

        }

        Collections.sort(contiguousList, contiguousOrder);

        for (Record record : contiguousList) {
            System.out.println(record.getContiguousRecordsCorrespondingDateTime() + " >> " + record.getContiguousRecordsSeenCars());
        }

        oneHourAndHalfContiguous.put(contiguousList.get(0).getContiguousRecordsCorrespondingDateTime().get(0), contiguousList.get(0).getContiguousRecordsSeenCars());
        oneHourAndHalfContiguous.put(contiguousList.get(0).getContiguousRecordsCorrespondingDateTime().get(1), contiguousList.get(0).getContiguousRecordsSeenCars());
        oneHourAndHalfContiguous.put(contiguousList.get(0).getContiguousRecordsCorrespondingDateTime().get(2), contiguousList.get(0).getContiguousRecordsSeenCars());
        return oneHourAndHalfContiguous;
    }

    /*
    * The comparator that targets the least seen cars in one hour and half contiguous
     */
    public static Comparator<Record> contiguousOrder = new Comparator<Record>() {
        public int compare(Record rcd2, Record rcd1) {
            return (int) (rcd2.getContiguousRecordsSeenCars() - rcd1.getContiguousRecordsSeenCars());
        }
    };

    /**
     * @param args
     */
    public static void main(String[] args) {

        List<Record> traceRecordList = readTraceFile();

        // Outputting the total number of seen cars
        printTotalNumberOfCars(traceRecordList);

        // Sequence of lines with a specified date
        printRecordsPerSpecifiedDate(traceRecordList);

        // Top three seen cars
        printTopThreeHalfHours(traceRecordList);

        // Least one hour and half contiguous seen cars
        printOneHourAndHalfContiguous(traceRecordList);

    }

}
