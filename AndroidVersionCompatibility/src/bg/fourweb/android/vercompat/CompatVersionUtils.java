package bg.fourweb.android.vercompat;

import android.os.Build;

public class CompatVersionUtils {

    private static final int v = Build.VERSION.SDK_INT;

    public static boolean isFroyo() {
        return v >= Build.VERSION_CODES.FROYO;
    }

    public static boolean isHoneycomb() {
        return v >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isSandwich() {
        return v >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

}
