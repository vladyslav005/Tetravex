package tetravex.core;

public enum Complexity {
    EASY(4),
    MEDIUM(7),
    HARD(9);

    private final int numberOfColors;

    Complexity(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }
}
