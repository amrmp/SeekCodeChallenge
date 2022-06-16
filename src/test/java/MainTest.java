
import SeekCodeChallenge.Main;
import SeekCodeChallenge.Record;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Amirmohammad
 */
@TestMethodOrder(OrderAnnotation.class)
public class MainTest {

    static Main testMain;

    /*
    * Actual data per the trace file.
     */
    public static Collection perDateSeenCars() {
        return Arrays.asList(new Object[][]{
            {LocalDate.parse("2021-12-01"), 179},
            {LocalDate.parse("2021-12-05"), 81},
            {LocalDate.parse("2021-12-08"), 134},
            {LocalDate.parse("2021-12-09"), 4}
        });
    }

    /*
    * Actual data per the trace file.
     */
    public static Collection topThreeHalfHours() {
        return Arrays.asList(new Object[][]{
            {LocalDateTime.parse("2021-12-01T07:30"), 46},
            {LocalDateTime.parse("2021-12-01T08:00"), 42},
            {LocalDateTime.parse("2021-12-08T18:00"), 33}
        });
    }

    /*
    * Actual data per the trace file.
     */
    public static Collection leastOneHourAndHalfContiguous() {
        return Arrays.asList(new Object[][]{
            {LocalDateTime.parse("2021-12-01T05:00"), 31},
            {LocalDateTime.parse("2021-12-01T05:30"), 31},
            {LocalDateTime.parse("2021-12-01T06:00"), 31}
        });
    }

    @BeforeEach
    public void setUp() {
        testMain = new Main();
    }

    @AfterEach
    public void tearDownClass() {
        testMain = null;
    }

    @Test
    @Order(1)
    @DisplayName("Ensure the trace file can be read successfully.")
    public void testReadTraceFile() {
        List<Record> actual = testMain.readTraceFile();
        assertNotNull(actual, "The record list could not be prepared. Check the source file.");
        assertEquals(24, actual.size());
    }

    @Test
    @Order(2)
    @DisplayName("Print the total number of seen car with respect to the given trace file.")
    public void testPrintTotalNumberOfCars() {
        int seenCars = testMain.printTotalNumberOfCars(testMain.readTraceFile());
        assertNotEquals(0, seenCars, "The trace file could not be loaded successfully.");
        assertEquals(398, seenCars, "Trace file was not read successfully.");
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("perDateSeenCars")
    @DisplayName("Print number of seen cars per specified date in the trace file.")
    public void testPrintRecordsPerSpecifiedDate(LocalDate lDate, int seenCars) {
        HashMap<LocalDate, Integer> seenCarsPerDate = testMain.printRecordsPerSpecifiedDate(testMain.readTraceFile());
        assertEquals(seenCars, seenCarsPerDate.get(lDate));
    }

    @ParameterizedTest
    @Order(4)
    @MethodSource("topThreeHalfHours")
    @DisplayName("Print top three half an hours with the most seen cars in the trace file.")
    public void testPrintTopThreeHalfHours(LocalDateTime lDateTime, int seenCars) {
        HashMap<LocalDateTime, Integer> seenCarsPerDate = testMain.printTopThreeHalfHours(testMain.readTraceFile());
        assertEquals(seenCars, seenCarsPerDate.get(lDateTime));
    }

    @ParameterizedTest
    @Order(5)
    @MethodSource("leastOneHourAndHalfContiguous")
    @DisplayName("Print one hour and half contiguous least seen cars.")
    public void testPrintOneHourAndHalfContiguous(LocalDateTime lDateTime, int seenCars) {
        HashMap<LocalDateTime, Integer> seenCarsOneHourAndHalfContiguous = testMain.printOneHourAndHalfContiguous(testMain.readTraceFile());
        assertEquals(seenCars, seenCarsOneHourAndHalfContiguous.get(lDateTime));
    }

}
