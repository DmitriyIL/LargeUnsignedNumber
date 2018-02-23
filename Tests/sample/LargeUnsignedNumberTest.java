package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeUnsignedNumberTest {
    @Test
    void LargeUnsignedNumber() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new LargeUnsignedNumber("0123456789"));
        thrown = assertThrows(IllegalArgumentException.class, () -> new LargeUnsignedNumber(""));
        thrown = assertThrows(IllegalArgumentException.class, () -> new LargeUnsignedNumber("12345678-9"));

        assertEquals("12345678901234567890", new LargeUnsignedNumber("12345678901234567890").toString());
    }

    @Test
    void compareWith() {
        assertEquals("more",
                new LargeUnsignedNumber("123456789123456789")
                        .compareWith(new LargeUnsignedNumber("123456789")));
        assertEquals("more",
                new LargeUnsignedNumber("2222222222222")
                .compareWith(new LargeUnsignedNumber("2222222222221")));
        assertEquals("less",
                new LargeUnsignedNumber("123456789")
                .compareWith(new LargeUnsignedNumber("123456789123456789")));
        assertEquals("less",
                new LargeUnsignedNumber("2222222222221")
                        .compareWith(new LargeUnsignedNumber("2222222222222")));
        assertEquals("equal",
                new LargeUnsignedNumber("123456789123456789")
                .compareWith(new LargeUnsignedNumber("123456789123456789")));
        assertEquals("more",
                new LargeUnsignedNumber("20123456789")
                        .compareWith(new LargeUnsignedNumber("10123456789")));

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                String comparison = (i - j < 0)? "less" : (i - j > 0)? "more" : "equal";

                assertEquals(comparison,
                        new LargeUnsignedNumber(String.valueOf(i))
                                .compareWith(new LargeUnsignedNumber(String.valueOf(j))));
            }
        }
    }

    @Test
    void add() {
        assertEquals("123469134",
                new LargeUnsignedNumber("123456789").add(new LargeUnsignedNumber("12345")).toString());
        assertEquals("0",
                new LargeUnsignedNumber("0").add(new LargeUnsignedNumber("0")).toString());
        assertEquals("1111122222",
                new LargeUnsignedNumber("11111").add(new LargeUnsignedNumber("1111111111")).toString());
        assertEquals("1000999998",
                new LargeUnsignedNumber("999999999").add(new LargeUnsignedNumber("999999")).toString());
        assertEquals("1999999998",
                new LargeUnsignedNumber("999999999").add(new LargeUnsignedNumber("999999999")).toString());

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= limit; j++) {
                assertEquals(String.valueOf(i + j),
                        new LargeUnsignedNumber(String.valueOf(i))
                                .add(new LargeUnsignedNumber(String.valueOf(j))).toString());
            }
        }
    }

    @Test
    void minus() {
        assertEquals("12345678900000000000",
                new LargeUnsignedNumber("12345678901234567890").minus(new LargeUnsignedNumber("1234567890")).toString());

        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            new LargeUnsignedNumber("123").minus(new LargeUnsignedNumber("1234"));
        });

        int limit = 1000;
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j <= i; j++) {
                assertEquals(String.valueOf(i - j),
                        new LargeUnsignedNumber(String.valueOf(i))
                                .minus(new LargeUnsignedNumber(String.valueOf(j))).toString(), String.valueOf(i) + ", " + String.valueOf(j));
            }
        }
    }

}