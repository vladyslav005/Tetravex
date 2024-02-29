package tetravex.core;

public enum Color {

    RED("\u001b[31m", "\u001b[41m", 0),
    GREEN("\u001b[32m", "\u001b[42m", 1),
    YELLOW("\u001b[33m", "\u001b[43m", 2),
    BLUE("\u001b[34m", "\u001b[44m", 3),
    BLACK("\u001b[37m\n", "\u001b[40m", 4),
    CYAN("\u001b[36m", "\u001b[46m", 5),
    MAGENTA("\u001b[35m", "\u001b[45m", 6),
    WHITE("\u001b[37m", "\u001b[47m", 7),
    BRIGHT_GREEN("", "", 0);

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
