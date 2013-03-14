package bg.fourweb.android.vercompat;

import java.io.File;

import android.content.Context;
import android.util.Log;

public class CompatFileUtils {

    private static final String TAG = "CompatFileUtils";
    private static Object sSync = new Object();
    private static File sCacheDir;

    public static File getCacheDir(Context c) {
        if (CompatVersionUtils.isFroyo()) {
            return c.getCacheDir();
        } else {
            return getCacheDirImpl(c);
        }
    }

    private static File getCacheDirImpl(Context c) {
        synchronized (sSync) {
            sCacheDir = new File(c.getFilesDir(), "cache");
            if (sCacheDir.exists() == false) {
                if (sCacheDir.mkdirs() == false) {
                    Log.w(TAG, "Unable to create cache directory");
                    return null;
                }
            }
        }
        return sCacheDir;
    }

}
