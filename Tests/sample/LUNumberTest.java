package sample;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
///ghj
class LUNumberTest {
    @Test
    void LargeUnsignedNumber() {
        assertThrows(IllegalArgumentException.class, () -> new LUNumber("0123456789"));
        assertThrows(IllegalArgumentException.class, () -> new LUNumber(""));
        assertThrows(IllegalArgumentException.class, () -> new LUNumber("12345678-9"));

        assertEquals("12345678901234567890", new LUNumber("12345678901234567890").toString());
    }

    @Test
    void compareTo() {
        assertEquals("more",
                new LUNumber("123456789123456789").compareTo(new LUNumber("123456789")));
        assertEquals("more",
                new LUNumber("2222222222222").compareTo(new LUNumber("2222222222221")));
        assertEquals("less",
                new LUNumber("123456789").compareTo(new LUNumber("123456789123456789")));
        assertEquals("less",
                new LUNumber("2222222222221").compareTo(new LUNumber("2222222222222")));
        assertEquals("equal",
                new LUNumber("123456789123456789").compareTo(new LUNumber("123456789123456789")));
        assertEquals("more",
                new LUNumber("20123456789").compareTo(new LUNumber("10123456789")));

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                String comparison = (i - j < 0) ? "less" : (i - j > 0) ? "more" : "equal";
                assertEquals(comparison,
                        new LUNumber(String.valueOf(i))
                                .compareTo(new LUNumber(String.valueOf(j))));
            }
        }
    }

    @Test
    void add() {
        assertEquals("123469134",
                new LUNumber("123456789").add(new LUNumber("12345")).toString());
        assertEquals("0",
                new LUNumber("0").add(new LUNumber("0")).toString());
        assertEquals("1111122222",
                new LUNumber("11111").add(new LUNumber("1111111111")).toString());
        assertEquals("1000999998",
                new LUNumber("999999999").add(new LUNumber("999999")).toString());
        assertEquals("1999999998",
                new LUNumber("999999999").add(new LUNumber("999999999")).toString());

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                assertEquals(String.valueOf(i + j),
                        new LUNumber(String.valueOf(i)).add(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }

    @Test
    void subtract() {
        assertEquals("12345678900000000000",
                new LUNumber("12345678901234567890")
                        .subtract(new LUNumber("1234567890")).toString());

        assertThrows(IllegalArgumentException.class,
                () -> new LUNumber("123").subtract(new LUNumber("1234")));

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= i; j++) {
                assertEquals(String.valueOf(i - j),
                        new LUNumber(String.valueOf(i)).subtract(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }

    @Test
    void multiply() {
        assertEquals("1219326311126352690",
                new LUNumber("1234567890")
                        .multiply(new LUNumber("987654321")).toString());
        assertEquals("1234567890",
                new LUNumber("1234567890")
                        .multiply(new LUNumber("1")).toString());
        assertEquals("0",
                new LUNumber("1234567890")
                        .multiply(new LUNumber("0")).toString());

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                assertEquals(String.valueOf(i * j),
                        new LUNumber(i).multiply(new LUNumber(j)).toString());
            }
        }

    }

    @Test
    void divide() {
        assertEquals("987654321",
                new LUNumber("1219326311126352690")
                        .divide(new LUNumber("1234567890")).toString());
        assertEquals("82304526",
                new LUNumber("1234567890")
                        .divide(new LUNumber("15")).toString());
        assertEquals("0",
                new LUNumber("12")
                        .divide(new LUNumber("1234")).toString());

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 1; j <= i + 1; j++) {
                assertEquals(String.valueOf(i / j),
                        new LUNumber(String.valueOf(i))
                                .divide(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }

    @Test
    void mod() {
        assertEquals("9",
                new LUNumber(String.valueOf("1627384950"))
                        .mod(new LUNumber(String.valueOf("39"))).toString());
        assertEquals("0",
                new LUNumber(String.valueOf("1627384950"))
                        .mod(new LUNumber(String.valueOf("1"))).toString());
        assertEquals("16273",
                new LUNumber(String.valueOf("16273"))
                        .mod(new LUNumber(String.valueOf("162738495"))).toString());

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 1; j <= i + 1; j++) {
                assertEquals(String.valueOf(i % j),
                        new LUNumber(String.valueOf(i))
                                .mod(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }
}