package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeUnsignedNumberTest {
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
    }
}