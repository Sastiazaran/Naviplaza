package util;

public final class Inputs {
    private Inputs() {
    }

    public static int parsePositiveInt(String text, String fieldName) {
        int value = parseInt(text, fieldName);
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0");
        }
        return value;
    }

    public static int parseNonNegativeInt(String text, String fieldName) {
        int value = parseInt(text, fieldName);
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be 0 or greater");
        }
        return value;
    }

    private static int parseInt(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a whole number");
        }
    }
}
