package SeekCodeChallenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amirmohammad
 */

/*
* This class creates an object of trace file per each line.
 */
public class Record implements Comparable<Record> {

    private LocalTime localTime;
    private LocalDate localDate;
    private int car;
    private List<Record> contiguousRecordList;
    private List<LocalDateTime> contiguousRecordCorrespondingDateTime;
    private int contiguousSeenCars;

    public Record(LocalTime lTime, LocalDate lDate, int ncars) {
        setLocalTime(lTime);
        setLocalDate(lDate);
        setNmbrsCars(ncars);
    }

    public Record(List<Record> contiguousRecords) {
        setContiguousRecords(contiguousRecords);
    }

    public void setNmbrsCars(int ncars) {
        if (ncars < 0) {
            ncars = 0;
        }
        this.car = ncars;
    }

    public void setLocalDate(LocalDate lDate) {
        this.localDate = lDate;
    }

    public void setLocalTime(LocalTime lTime) {
        this.localTime = lTime;
    }

    public int getNmbrsCars() {
        return this.car;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public LocalTime getLocalTime() {
        return this.localTime;
    }

    public void setContiguousRecords(List<Record> conRecords) {
        this.contiguousRecordList = new ArrayList<>(conRecords);
        this.contiguousRecordCorrespondingDateTime = new ArrayList<>();
        this.localDate = conRecords.get(0).getLocalDate();
        int conSeenCars = 0;
        for (Record conRecord : conRecords) {
            conSeenCars += conRecord.getNmbrsCars();
            LocalDateTime ldateTime = LocalDateTime.of(conRecord.getLocalDate(), conRecord.getLocalTime());
            contiguousRecordCorrespondingDateTime.add(ldateTime);
        }
        setContiguousRecordsSeenCars(conSeenCars);

    }

    public List<Record> getContiguousRecords() {
        return this.contiguousRecordList;
    }

    public List<LocalDateTime> getContiguousRecordsCorrespondingDateTime() {
        return this.contiguousRecordCorrespondingDateTime;
    }

    public void setContiguousRecordsSeenCars(int seenCars) {
        this.contiguousSeenCars = seenCars;
    }

    public int getContiguousRecordsSeenCars() {
        return this.contiguousSeenCars;
    }

    /*
    In descending order
     */
    @Override
    public int compareTo(Record obj) {
        int seenCars = obj.getNmbrsCars();
        if (this.car > seenCars) {
            return -1;
        } else if (this.car == obj.getNmbrsCars()) {
            return 0;
        } else {
            return 1;
        }
    }

}
