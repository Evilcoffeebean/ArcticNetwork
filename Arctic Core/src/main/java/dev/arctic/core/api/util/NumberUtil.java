package dev.arctic.core.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Zvijer on 20.8.2017..
 */
public class NumberUtil {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
