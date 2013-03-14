/*
 * This file is part of AndroidRssParser.
 * 
 * AndroidRssParser is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * AndroidRssParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Foobar.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package bg.fourweb.android.rss.test;

import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import bg.fourweb.android.rss.Category;
import bg.fourweb.android.rss.Enclosure;
import bg.fourweb.android.rss.Feed;
import bg.fourweb.android.rss.FeedBuilder;
import bg.fourweb.android.rss.Image;
import bg.fourweb.android.rss.Item;
import bg.fourweb.android.rss.ItemBuilder;
import bg.fourweb.android.rss.Mixin;

public class TestBuilders extends TestCase {

    private static Random random = new Random();

    private static String randomStr() {
        return RandomStringUtils.random(random.nextInt(128));
    }

    private static String randomDate() {
        // TODO :)
        return randomStr();
    }

    private static int randomInt() {
        return random.nextInt();
    }

    private static boolean randomBool() {
        return random.nextBoolean();
    }

    private static Category[] addRandomCategories(Mixin.CategoryBuilder<?> categoryBuilder, int howMany) {
        final Category[] CATEGORIES = new Category[10];
        for (int i = 0; i < CATEGORIES.length; i++) {
            final String catValue = randomStr();
            final String catDomain = randomStr();
            CATEGORIES[i] = new Category(catValue, catDomain);

            assertSame(categoryBuilder, categoryBuilder.addCategory(catValue, catDomain));
        }
        return CATEGORIES;
    }

    private static void assertCategories(Category[] expected, List<Category> actual) {
        if (expected == null) {
            throw new IllegalArgumentException();
        }
        assertNotNull(actual);
        assertEquals(expected.length, actual.size());
        for (int i = 0; i < expected.length; i++) {
            assertNotNull(actual.get(i));
            assertEquals(expected[i], actual.get(i));

            assertNotNull(actual.get(i).value);
            assertEquals(expected[i].value, actual.get(i).value);

            assertNotNull(actual.get(i).domain);
            assertEquals(expected[i].domain, actual.get(i).domain);
        }
    }

    public void testItemBuilder() {
        final ItemBuilder b = new ItemBuilder();

        // prepare categories
        final Category[] CATEGORIES = addRandomCategories(b, 10);

        // prepare enclosures
        final Enclosure[] ENCLOSURES = new Enclosure[10];
        for (int i = 0; i < ENCLOSURES.length; i++) {
            final String encUrl = randomStr();
            final int encLength = randomInt();
            final String encMimeType = randomStr();
            ENCLOSURES[i] = new Enclosure(encUrl, encLength, encMimeType);

            assertSame(b, b.addEnclosureUrl(encUrl, encLength, encMimeType));
        }

        final String AUTHOR = randomStr();
        final String COMMENTS = randomStr();
        final String DESCRIPTION = randomStr();
        final String GUID_VALUE = randomStr();
        final boolean GUID_IS_PERMA_LINK = randomBool();
        final String LINK = randomStr();
        final String PUB_DATE = randomDate();
        final String SOURCE_VALUE = randomStr();
        final String SOURCE_URL = randomStr();
        final String TITLE = randomStr();

        assertSame(b, b.author(AUTHOR));
        assertSame(b, b.comments(COMMENTS));
        assertSame(b, b.description(DESCRIPTION));
        assertSame(b, b.guidValue(GUID_VALUE));
        assertSame(b, b.guidIsPermaLink(GUID_IS_PERMA_LINK));
        assertSame(b, b.link(LINK));
        assertSame(b, b.pubDate(PUB_DATE));
        assertSame(b, b.sourceValue(SOURCE_VALUE));
        assertSame(b, b.sourceUrl(SOURCE_URL));
        assertSame(b, b.title(TITLE));

        final Item item = b.build();
        assertNotNull(item);

        assertCategories(CATEGORIES, item.categories);

        assertNotNull(item.enclosures);
        for (int i = 0; i < ENCLOSURES.length; i++) {
            assertEquals(ENCLOSURES[i].length, item.enclosures.get(i).length);

            assertNotNull(item.enclosures.get(i).type);
            assertEquals(ENCLOSURES[i].type, item.enclosures.get(i).type);

            assertNotNull(item.enclosures.get(i).url);
            assertEquals(ENCLOSURES[i].url, item.enclosures.get(i).url);
        }

        assertEquals(AUTHOR, item.author);
        assertEquals(COMMENTS, item.comments);
        assertEquals(DESCRIPTION, item.description);

        assertNotNull(item.guid);
        assertEquals(GUID_VALUE, item.guid.value);
        assertEquals(GUID_IS_PERMA_LINK, item.guid.isPermaLink);

        assertEquals(LINK, item.link);
        assertEquals(PUB_DATE, item.pubDate);

        assertNotNull(item.source);
        assertEquals(SOURCE_VALUE, item.source.value);
        assertEquals(SOURCE_URL, item.source.url);

        assertEquals(TITLE, item.title);
    }

    public void testFeedBuilder() {
        final FeedBuilder b = new FeedBuilder();

        // generate and add categories
        final Category[] CATEGORIES = addRandomCategories(b, 10);

        // generate skip days
        String[] skipDays = ArrayUtils.EMPTY_STRING_ARRAY;
        for (final String skipDay : new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                "Sunday" }) {
            if (randomBool()) {
                skipDays = ArrayUtils.add(skipDays, skipDay);
            }
        }
        // add skip days
        for (final String skipDay : skipDays) {
            b.addSkipDay(skipDay);
        }

        // generate skip hours
        int[] skipHours = ArrayUtils.EMPTY_INT_ARRAY;
        for (int i = 0; i <= 23; i++) {
            if (randomBool()) {
                skipHours = ArrayUtils.add(skipHours, i);
            }
        }
        // add skip hours
        for (final int skipHour : skipHours) {
            b.addSkipHour(skipHour);
        }

        final String COPYRIGHT = randomStr();
        final String DESCRIPTION = randomStr();
        final String DOCS = randomStr();
        final String GENERATOR = randomStr();
        final String IMAGE_DESCRIPTION = randomStr();
        final int IMAGE_HEIGHT = randomInt();
        final String IMAGE_LINK = randomStr();
        final String IMAGE_TITLE = randomStr();
        final String IMAGE_URL = randomStr();
        final int IMAGE_WIDTH = randomInt();
        final String LANGUAGE = randomStr();
        final String LAST_BUILD_DATE = randomStr();
        final String LINK = randomStr();
        final String MANAGING_EDITOR = randomStr();
        final String PUB_DATE = randomStr();
        final String RATING = randomStr();
        final String TITLE = randomStr();
        final int TTL = randomInt();
        final String WEB_MASTER = randomStr();

        assertSame(b, b.copyright(COPYRIGHT));
        assertSame(b, b.description(DESCRIPTION));
        assertSame(b, b.docs(DOCS));
        assertSame(b, b.generator(GENERATOR));
        assertSame(b, b.imageDescription(IMAGE_DESCRIPTION));
        assertSame(b, b.imageHeight(IMAGE_HEIGHT));
        assertSame(b, b.imageLink(IMAGE_LINK));
        assertSame(b, b.imageTitle(IMAGE_TITLE));
        assertSame(b, b.imageUrl(IMAGE_URL));
        assertSame(b, b.imageWidth(IMAGE_WIDTH));
        assertSame(b, b.language(LANGUAGE));
        assertSame(b, b.lastBuildDate(LAST_BUILD_DATE));
        assertSame(b, b.link(LINK));
        assertSame(b, b.managingEditor(MANAGING_EDITOR));
        assertSame(b, b.pubDate(PUB_DATE));
        assertSame(b, b.rating(RATING));
        assertSame(b, b.title(TITLE));
        assertSame(b, b.ttl(TTL));
        assertSame(b, b.webMaster(WEB_MASTER));
        
        final Feed feed = b.build();
        
        assertNotNull(feed);
        assertNotNull(feed.categories);
        assertNotNull(feed.image);
        assertNotNull(feed.items);
        assertNotNull(feed.skipDays);
        assertNotNull(feed.skipHours);
        
        // assert categories
        assertCategories(CATEGORIES, feed.categories);
        
        // assert skip days
        assertEquals(skipDays.length, feed.skipDays.size());
        for (final String skipDay : feed.skipDays) {
            assertTrue(feed.skipDays.contains(skipDay));
        }
        
        // assert skip hours
        assertEquals(skipHours.length, feed.skipHours.size());
        for (final int skipHour: feed.skipHours) {
            assertTrue(feed.skipHours.contains(skipHour));
        }
        
        // assert image width and height
        if (IMAGE_WIDTH > Image.MAX_WIDTH) {
            assertEquals(Image.MAX_WIDTH, feed.image.width);
        } else if (IMAGE_WIDTH < Image.MIN_WIDTH) {
            assertEquals(Image.MIN_WIDTH, feed.image.width);
        } else {
            assertEquals(IMAGE_WIDTH, feed.image.width);
        }

        if (IMAGE_HEIGHT > Image.MAX_HEIGHT) {
            assertEquals(Image.MAX_HEIGHT, feed.image.height);
        } else if (IMAGE_HEIGHT < Image.MIN_HEIGHT) {
            assertEquals(Image.MIN_HEIGHT, feed.image.height);
        } else {
            assertEquals(IMAGE_HEIGHT, feed.image.height);
        }

        // assert fields
        assertEquals(COPYRIGHT, feed.copyright);
        assertEquals(DESCRIPTION, feed.description);
        assertEquals(DOCS, feed.docs);
        assertEquals(GENERATOR, feed.generator);
        assertEquals(IMAGE_DESCRIPTION, feed.image.description);
        assertEquals(IMAGE_LINK, feed.image.link);
        assertEquals(IMAGE_TITLE, feed.image.title);
        assertEquals(IMAGE_URL, feed.image.url);
        assertEquals(LANGUAGE, feed.language);
        assertEquals(LAST_BUILD_DATE, feed.lastBuildDate);
        assertEquals(LINK, feed.link);
        assertEquals(MANAGING_EDITOR, feed.managingEditor);
        assertEquals(PUB_DATE, feed.pubDate);
        assertEquals(RATING, feed.rating);
        assertEquals(TITLE, feed.title);
        assertEquals(TTL, feed.ttl);
        assertEquals(WEB_MASTER, feed.webMaster);
        
        // TODO y: assert items
    }
}
