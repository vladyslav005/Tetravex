package tetravex.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static int getRandInt(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    public static <T> List<List<T>> initTwoDimensionalArray(int width, int height) {
        List<List<T>> array = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            array.add(new ArrayList<>(width));

            for (int j = 0; j < width; j++) {
                array.get(i).add(null);
            }
        }

        return array;
    }


}
