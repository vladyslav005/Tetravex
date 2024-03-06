package tetravex.core;

public enum Color {
    RED("\u001b[31m", "\u001b[41m", 0),
    GREEN("\u001b[32m", "\u001b[42m", 1),
    YELLOW("\u001b[33m", "\u001b[43m", 2),
    BLACK("\u001b[34m", "\u001b[40m", 3),
    CYAN("\u001b[36m", "\u001b[46m", 4),
    MAGENTA("\u001b[35m", "\u001b[45m", 5),
    BRIGHT_GREEN("\033[38;2;128;255;0m", "\033[48;2;102;204;0m", 6),
    ORANGE("\033[38;2;255;178;102m", "\033[48;2;255;178;102m", 7),
    PURPLE("\033[38;2;255;255;0m", "\033[48;2;255;53;255m", 8),
    BRIGHT_MAGENTA("\u001b[95m", "\u001b[105m", 9),
    TILE_BACKGROUND("\033[38;2;228;253;225m", "\033[48;2;228;253;225m", 10),
    GRID_COLOR("\033[38;2;69;105;144m", "\033[48;2;69;105;144m", 11),
    WHITE("\033[38;2;255;255;255m", "\033[48;2;152;152;152m", 12),
    HIGHLOGHT_COLOR("\033[38;2;204;255;153m", "\033[48;2;204;255;153m", 13);

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
        return WHITE.getSymbolColorCode() + getBackgroundColorCode() + getNumber() + reset;
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
