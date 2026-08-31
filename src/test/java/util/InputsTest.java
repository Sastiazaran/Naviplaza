package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InputsTest {

    @Test
    void parsePositiveIntAcceptsWholeNumbers() {
        assertEquals(5, Inputs.parsePositiveInt(" 5 ", "Count"));
    }

    @Test
    void parsePositiveIntRejectsZeroNegativeAndJunk() {
        assertThrows(IllegalArgumentException.class, () -> Inputs.parsePositiveInt("0", "Count"));
        assertThrows(IllegalArgumentException.class, () -> Inputs.parsePositiveInt("-2", "Count"));
        assertThrows(IllegalArgumentException.class, () -> Inputs.parsePositiveInt("", "Count"));
        assertThrows(IllegalArgumentException.class, () -> Inputs.parsePositiveInt("abc", "Count"));
    }

    @Test
    void parseNonNegativeIntAllowsZero() {
        assertEquals(0, Inputs.parseNonNegativeInt("0", "Index"));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Inputs.parseNonNegativeInt("-1", "Index"));
        assertTrue(ex.getMessage().contains("Index"));
    }
}
