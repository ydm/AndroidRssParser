package bg.fourweb.android.vercompat;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.net.http.AndroidHttpClient;

@SuppressLint("NewApi")
public class CompatHttp {

    public static HttpClient getHttpClient() {
        if (CompatVersionUtils.isFroyo()) {
            return AndroidHttpClient.newInstance("Android");
        } else {
            return new DefaultHttpClient();
        }
    }

    public static void closeHttpClient(HttpClient client) {
        if (client == null) {
            return;
        }
        if (CompatVersionUtils.isFroyo() && client instanceof AndroidHttpClient) {
            ((AndroidHttpClient) client).close();
        }
    }

}
