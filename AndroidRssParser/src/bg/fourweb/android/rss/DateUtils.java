package bg.fourweb.android.rss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public final static String RFC822 = "EEE, dd MMM yyyy HH:mm:ss Z";
    
    public static SimpleDateFormat createFormatRFC822(Locale loc) {
        return new SimpleDateFormat(RFC822, loc);
    }
    
    public static SimpleDateFormat createFormatRFC822() {
        return new SimpleDateFormat(RFC822);
    }

    public static String formatRFC822(Date date) {
        return createFormatRFC822().format(date);
    }

    public static Date parseRFC822(String date) throws ParseException {
        return createFormatRFC822().parse(date);
    }

}
