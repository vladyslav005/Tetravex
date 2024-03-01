package tetravex.core;

public enum Color {

    RED("\u001b[31m", "\u001b[41m", 0),
    GREEN("\u001b[32m", "\u001b[42m", 1),
    YELLOW("\u001b[33m", "\u001b[43m", 2),
    BLACK("\u001b[34m", "\u001b[40m", 3),
    CYAN("\u001b[36m", "\u001b[46m", 4),
    MAGENTA("\u001b[35m", "\u001b[45m", 5),
    BRIGHT_GREEN("\u001b[92m", "\u001b[102m", 6),
    BRIGHT_RED("\u001b[91m", "\u001b[101m", 7),
    BRIGHT_YELLOW("\u001b[93m", "\u001b[103m", 8),
    BRIGHT_MAGENTA("\u001b[95m", "\u001b[105m", 9),

    TILE_BACKGROUND("\u001b[90m", "\033[48;2;180;180;180m", 10),

    GRAY("\033[38;2;255;255;204m", "\033[48;2;255;255;204m", 11),
    WHITE("\u001b[37m", "\033[48;2;255;255;255m", 12),
    ORANGE("\033[38;2;255;178;102m", "\033[48;2;255;178;102m", 12);

    public static final String reset = "\u001B[0m";
    private final String symbolColorCode;
    private final String backgroundColorCode;
    private final int number;

    Color(String symbolColorCode, String backgroundColorCode, int number) {
        this.symbolColorCode = symbolColorCode;
        this.backgroundColorCode = backgroundColorCode;
        this.number = number;
    }

    public String getColoredBackground() {
        return getBackgroundColorCode() + getNumber() + reset;
    }


    public String getSymbolColorCode() {
        return symbolColorCode;
    }

    public String getBackgroundColorCode() {
        return backgroundColorCode;
    }

    public int getNumber() {
        return number;
    }
}
