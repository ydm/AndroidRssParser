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
package bg.fourweb.android.rss;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.webkit.MimeTypeMap;

public class RssHandler extends AbsRssHandler {

    private static class Thumbnail implements Comparable<Thumbnail> {

        private int width, height;
        private String url;
        private String mimeType;

        public Thumbnail(String url, String mimeType, int width, int height) {
            super();
            this.url = url != null ? url : StringUtils.EMPTY;
            this.mimeType = mimeType != null ? mimeType : StringUtils.EMPTY;
            this.width = Math.max(0, width);
            this.height = Math.max(0, height);
        }

        @Override
        public int compareTo(Thumbnail another) {
            return (width + height) - (another.width + another.height);
        }

    }

    // channel elements
    // @formatter:off
    private static final int ELT_INVALID             = 0;
    private static final int ELT_RSS                 = 1;
    private static final int ELT_CHANNEL             = 2;
    private static final int ELT_TITLE               = 4;
    private static final int ELT_LINK                = 5;
    private static final int ELT_DESCRIPTION         = 6;
    private static final int ELT_LANGUAGE            = 7;
    private static final int ELT_COPYRIGHT           = 8;
    private static final int ELT_MANAGINGEDITOR      = 9;
    private static final int ELT_WEBMASTER           = 10;
    private static final int ELT_PUBDATE             = 11;
    private static final int ELT_LASTBUILDDATE       = 12;
    private static final int ELT_CATEGORY            = 13;
    private static final int ELT_GENERATOR           = 14;
    private static final int ELT_DOCS                = 15;
    private static final int ELT_CLOUD               = 16;
    private static final int ELT_TTL                 = 17;
    private static final int ELT_IMAGE               = 18;
    private static final int ELT_IMAGE_URL           = 34;
    private static final int ELT_IMAGE_WIDTH         = 37;
    private static final int ELT_IMAGE_HEIGHT        = 38;
    private static final int ELT_RATING              = 19;
    private static final int ELT_TEXTINPUT           = 20;
    private static final int ELT_SKIPHOURS           = 21;
    private static final int ELT_SKIPDAYS            = 22;
    private static final int ELT_ITEM                = 23;
    private static final int ELT_AUTHOR              = 27;
    private static final int ELT_COMMENTS            = 29;
    private static final int ELT_ENCLOSURE           = 30;
    private static final int ELT_GUID                = 31;
    private static final int ELT_SOURCE              = 33;
    
    // y: this isn't part of the RSS standard, but too many **** include it in feeds
    private static final int ELT_THUMBNAIL           = 40;
    // @formatter:on

    private static final Map<String, Integer> elements = new HashMap<String, Integer>();

    static {
        // @formatter:off
        elements.put("rss"            , ELT_RSS);
        elements.put("channel"        , ELT_CHANNEL);
        elements.put("title"          , ELT_TITLE);
        elements.put("link"           , ELT_LINK);
        elements.put("description"    , ELT_DESCRIPTION);
        elements.put("language"       , ELT_LANGUAGE);
        elements.put("copyright"      , ELT_COPYRIGHT);
        elements.put("managingEditor" , ELT_MANAGINGEDITOR);
        elements.put("webMaster"      , ELT_WEBMASTER);
        elements.put("pubDate"        , ELT_PUBDATE);
        elements.put("lastBuildDate"  , ELT_LASTBUILDDATE);
        elements.put("category"       , ELT_CATEGORY);
        elements.put("generator"      , ELT_GENERATOR);
        elements.put("docs"           , ELT_DOCS);
        elements.put("cloud"          , ELT_CLOUD);
        elements.put("ttl"            , ELT_TTL);
        elements.put("image"          , ELT_IMAGE);
        elements.put("url"            , ELT_IMAGE_URL);
        elements.put("width"          , ELT_IMAGE_WIDTH);
        elements.put("height"         , ELT_IMAGE_HEIGHT);
        elements.put("rating"         , ELT_RATING);
        elements.put("textInput"      , ELT_TEXTINPUT);
        elements.put("skipHours"      , ELT_SKIPHOURS);
        elements.put("skipDays"       , ELT_SKIPDAYS);
        elements.put("item"           , ELT_ITEM);
        elements.put("author"         , ELT_AUTHOR);
        elements.put("comments"       , ELT_COMMENTS);
        elements.put("enclosure"      , ELT_ENCLOSURE);
        elements.put("guid"           , ELT_GUID);
        elements.put("source"         , ELT_SOURCE);
        
        elements.put("thumbnail"      , ELT_THUMBNAIL);
        // @formatter:on
    }

    protected ItemBuilder currentItemB;
    private FeedBuilder feedB = new FeedBuilder();
    private StringBuilder valueB;
    // private boolean inChannel = false;
    private boolean inItems = false;
    private boolean inImage = false;
    private String categoryDomain;
    private int currentElt;
    private Thumbnail largestThumbnail;

    public RssHandler(ParserOptions opts) {
        super(opts);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        checkStopCondition();

        boolean createValueB = false;

        currentElt = elements.containsKey(localName) ? elements.get(localName) : ELT_INVALID;
        // @formatter:off
        switch (currentElt) {
        // case ELT_CHANNEL: inChannel = true; break;
        case ELT_ITEM: inItems = true; currentItemB = new ItemBuilder(); break;
        case ELT_IMAGE: inImage = true; break; // channel
        // ---
        // tags that contain value only
        case ELT_TITLE: // channel & image & item
        case ELT_LINK: // channel & image & item
        case ELT_DESCRIPTION: // channel & item
        case ELT_LANGUAGE: // channel
        case ELT_COPYRIGHT: // channel
        case ELT_MANAGINGEDITOR: // channel
        case ELT_WEBMASTER: // channel
        case ELT_PUBDATE: // channel & item
        case ELT_LASTBUILDDATE: // channel
        case ELT_GENERATOR: // channel
        case ELT_DOCS: // channel
        case ELT_CLOUD: // channel
        case ELT_TTL: // channel
        case ELT_IMAGE_URL: // image
        case ELT_IMAGE_WIDTH: // image
        case ELT_IMAGE_HEIGHT: // image
        case ELT_RATING: // channel
        case ELT_SKIPHOURS: // channel
        case ELT_SKIPDAYS: // channel
        case ELT_AUTHOR: // item
        case ELT_COMMENTS: // item
            createValueB = true;
            break;
        // ---
        // tags that contain both values and attributes
        case ELT_CATEGORY: categoryDomain = attributes.getValue("domain"); createValueB = true; break; // channel & item
        case ELT_GUID: currentItemB.guidIsPermaLink(attributes.getValue("isPermaLink")); createValueB = true; break; // item
        case ELT_SOURCE: currentItemB.sourceUrl(attributes.getValue("url")); createValueB = true; break; // item    
        // ---
        // tags that contain attributes only
        case ELT_ENCLOSURE: {// item
            final String url = attributes.getValue("url");
            int length; try {length = Integer.parseInt(attributes.getValue("length"));} catch (NumberFormatException e) {length = 0;}
            final String mimeType = attributes.getValue("type");
            currentItemB.addEnclosureUrl(url, length, mimeType);
            break;
        }
        case ELT_THUMBNAIL: {
            final String url = attributes.getValue("url");
            final String mimeType = StringUtils.isEmpty(url) ? "" : MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
            int width; try {width = Integer.parseInt(attributes.getValue("width"));} catch (NumberFormatException e) {width = 0;}
            int height; try {height = Integer.parseInt(attributes.getValue("height"));} catch (NumberFormatException e) {height= 0;}
            final Thumbnail t = new Thumbnail(url, mimeType, width, height);
            if (largestThumbnail == null || largestThumbnail.compareTo(t) < 0) {
                largestThumbnail = t;
            }
        }
        }
        
        // @formatter:on
        if (createValueB) {
            valueB = new StringBuilder(63);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        checkStopCondition();
        switch (currentElt) {
        case ELT_CHANNEL:
            /* inChannel = */
            inItems = false;
            break;
        case ELT_ITEM:
            inItems = true;
            break;
        case ELT_TITLE: // channel & image & item
        case ELT_LINK: // channel & image & item
        case ELT_DESCRIPTION: // channel & item
        case ELT_LANGUAGE: // channel
        case ELT_COPYRIGHT: // channel
        case ELT_MANAGINGEDITOR: // channel
        case ELT_WEBMASTER: // channel
        case ELT_PUBDATE: // channel & item
        case ELT_LASTBUILDDATE: // channel
        case ELT_CATEGORY: // channel & item
        case ELT_GENERATOR: // channel
        case ELT_DOCS: // channel
        case ELT_CLOUD: // channel
        case ELT_TTL: // channel
        case ELT_IMAGE: // channel
        case ELT_IMAGE_URL: // image
        case ELT_IMAGE_WIDTH: // image
        case ELT_IMAGE_HEIGHT: // image
        case ELT_RATING: { // channel
        }
        // TODO: add <hour> and <day?> tags
        // case ELT_SKIPHOURS: // channel
        // case ELT_SKIPDAYS: // channel
        case ELT_AUTHOR: // item
        case ELT_COMMENTS: // item
            // case ELT_ENCLOSURE: // item
        case ELT_GUID: // item
        case ELT_SOURCE: // item
            valueB.append(new String(ch, start, length));
        }
    }

    @Override
    @SuppressWarnings("unused")
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        checkStopCondition();
        final Integer endElt = elements.get(localName);
        if (endElt == null) {
            return;
        }
        // @formatter:off
        switch (endElt) {
        case ELT_CHANNEL: /* inChannel = */ inItems = false; return;
        case ELT_ITEM:
            if (currentItemB.getFirstEnclosure() == null && largestThumbnail != null) {
                currentItemB.addEnclosureUrl(largestThumbnail.url, 0, largestThumbnail.mimeType);
            }
            largestThumbnail = null;
            feedB.addItem(currentItemB.build());
            return;
        }
        if (valueB == null) {
            return;
        }
        String value = valueB.toString();
        if (opts.getMustPreserveWhitespace() == false) {
            value = value.trim();
        }
        switch (endElt) {
        case ELT_TITLE: {Object t = inItems == false ? (inImage == false ? feedB.title(value) : feedB.imageTitle(value)) : currentItemB.title(value); break;} // channel & image & item
        case ELT_LINK: {Object t = inItems == false ? (inImage == false ? feedB.link(value) : feedB.imageLink(value)) : currentItemB.link(value); break;} // channel & image & item
        case ELT_DESCRIPTION: {Object t = inItems == false ? (inImage == false ? feedB.description(value) : currentItemB.description(value)) : currentItemB.description(value); break;} // channel & image & item
        case ELT_LANGUAGE: feedB.language(value); break; // channel
        case ELT_COPYRIGHT: feedB.copyright(value); break; // channel
        case ELT_MANAGINGEDITOR: feedB.managingEditor(value); break; // channel
        case ELT_WEBMASTER: feedB.webMaster(value); break; // channel
        case ELT_PUBDATE: {Object t = inItems == false ? feedB.pubDate(value) : currentItemB.pubDate(value); break;} // channel & item
        case ELT_LASTBUILDDATE: feedB.lastBuildDate(value); // channel
        case ELT_CATEGORY: {Object t = inItems == false ? feedB.addCategory(value, categoryDomain) : currentItemB.addCategory(value, categoryDomain); break;}// channel & item
        case ELT_GENERATOR: feedB.generator(value); break; // channel
        case ELT_DOCS: feedB.docs(value); break; // channel
        case ELT_CLOUD: // channel // TODO y: yeah...
        case ELT_TTL: try {feedB.ttl(Integer.parseInt(value));} catch (NumberFormatException e) {feedB.ttl(Integer.MAX_VALUE);} break; // channel
        case ELT_IMAGE: inImage = false; break; // channel
        case ELT_IMAGE_URL: feedB.imageUrl(value); // image
        case ELT_IMAGE_WIDTH: try {feedB.imageWidth(Integer.parseInt(value));} catch (NumberFormatException e) {feedB.imageWidth(Image.DEFAULT_WIDTH);} // image
        case ELT_IMAGE_HEIGHT: try {feedB.imageHeight(Integer.parseInt(value));} catch (NumberFormatException e) {feedB.imageHeight(Image.DEFAULT_HEIGHT);} // image
        case ELT_RATING: feedB.rating(value); break; // channel
        // case ELT_SKIPHOURS: // channel // TODO y: ...
        // case ELT_SKIPDAYS: // channel // TODO y: ...
        case ELT_AUTHOR: currentItemB.author(value); break; // item
        case ELT_COMMENTS: currentItemB.comments(value); break; // item
        case ELT_GUID: currentItemB.guidValue(value); break; // item
        case ELT_SOURCE: currentItemB.sourceValue(value); // item
        }
        // @formatter:off
    }

    @Override
    Feed getProductFeed() {
        return feedB.build();
    }

}
