package bg.fourweb.android.rss.test;

import junit.framework.TestCase;
import android.util.Log;
import bg.fourweb.android.rss.Feed;
import bg.fourweb.android.rss.Parser;
import bg.fourweb.android.rss.Parser.ProgressObserver;

public class TestParser extends TestCase {

    private static final String TAG = "TestParser";

    public void testParser() {
        final String url = "http://www.rssboard.org/files/sample-rss-2.xml";
        final Parser p = new Parser();
        final Feed f = p.parse(url, new ProgressObserver() {
            public void update(int progress) {
                Log.d(TAG, "progress=" + progress);
            }
        });
        
        assertNotNull(f);
        assertEquals("Liftoff News", f.title);
        assertEquals("http://liftoff.msfc.nasa.gov/", f.link);
        assertEquals("Liftoff to Space Exploration.", f.description);
        assertEquals("en-us", f.language);
        assertEquals("Tue, 10 Jun 2003 04:00:00 GMT", f.pubDate);
        assertEquals("Tue, 10 Jun 2003 09:41:01 GMT", f.lastBuildDate);
        assertEquals("http://blogs.law.harvard.edu/tech/rss", f.docs);
        assertEquals("Weblog Editor 2.0", f.generator);
        assertEquals("editor@example.com", f.managingEditor);
        assertEquals("webmaster@example.com", f.webMaster);
        assertEquals(4, f.items.size());
        // item 1
        assertEquals("Star City", f.items.get(0).title);
        assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp", f.items.get(0).link);
        assertEquals("Tue, 03 Jun 2003 09:39:21 GMT", f.items.get(0).pubDate);
        assertEquals("http://liftoff.msfc.nasa.gov/2003/06/03.html#item573", f.items.get(0).guid.value);
        // TODO y: other items and missed properties
    }

}
