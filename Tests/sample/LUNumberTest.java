package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LUNumberTest {
    @Test
    void LargeUnsignedNumber() {
        assertThrows(IllegalArgumentException.class, () -> new LUNumber("0123456789"));
        assertThrows(IllegalArgumentException.class, () -> new LUNumber(""));
        assertThrows(IllegalArgumentException.class, () -> new LUNumber("12345678-9"));

        assertEquals("12345678901234567890", new LUNumber("12345678901234567890").toString());
    }

    @Test
    void compareWith() {
        assertEquals("more",
                new LUNumber("123456789123456789")
                        .compareWith(new LUNumber("123456789")));
        assertEquals("more",
                new LUNumber("2222222222222")
                .compareWith(new LUNumber("2222222222221")));
        assertEquals("less",
                new LUNumber("123456789")
                .compareWith(new LUNumber("123456789123456789")));
        assertEquals("less",
                new LUNumber("2222222222221")
                        .compareWith(new LUNumber("2222222222222")));
        assertEquals("equal",
                new LUNumber("123456789123456789")
                .compareWith(new LUNumber("123456789123456789")));
        assertEquals("more",
                new LUNumber("20123456789")
                        .compareWith(new LUNumber("10123456789")));

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                String comparison = (i - j < 0)? "less" : (i - j > 0)? "more" : "equal";

                assertEquals(comparison,
                        new LUNumber(String.valueOf(i))
                                .compareWith(new LUNumber(String.valueOf(j))));
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
                        new LUNumber(String.valueOf(i))
                                .add(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }

    @Test
    void minus() {
        assertEquals("12345678900000000000",
                new LUNumber("12345678901234567890")
                        .minus(new LUNumber("1234567890")).toString());

        assertThrows(IllegalArgumentException.class, () -> new LUNumber("123").minus(new LUNumber("1234")));

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= i; j++) {
                assertEquals(String.valueOf(i - j),
                        new LUNumber(String.valueOf(i))
                                .minus(new LUNumber(String.valueOf(j))).toString());
            }
        }
    }

}