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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Data class that has all the channel attributes and the item list itself.
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class Feed implements NonNullFields {
    // required elements: title, link & description
    /**
     * The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same
     * information as your RSS file, the title of your channel should be the same as the title of your website.
     */
    public final String title;
    /**
     * The URL to the HTML website corresponding to the channel.
     */
    public final String link;
    /**
     * Phrase or sentence describing the channel.
     */
    public final String description;
    // optional elements follow
    /**
     * The language the channel is written in. This allows aggregators to group all Italian language sites, for example, on a
     * single page. A list of allowable values for this element, as provided by Netscape, is here:
     * http://www.rssboard.org/rss-language-codes . You may also use values defined by the W3C:
     * http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes .
     */
    public final String language;
    /**
     * Copyright notice for content in the channel.
     */
    public final String copyright;
    /**
     * Email address for person responsible for editorial content. Example:
     * {@code <copyright>geo@herald.com (George Matesky)</copyright>}.
     */
    public final String managingEditor;
    /**
     * Email address for person responsible for technical issues relating to channel.
     */
    public final String webMaster;
    /**
     * The publication date for the content in the channel. For example, the New York Times publishes on a daily basis, the
     * publication date flips once every 24 hours. That's when the pubDate of the channel changes. All date-times in RSS
     * conform to the Date and Time Specification of RFC 822 (http://asg.web.cmu.edu/rfc/rfc822.html), with the exception that
     * the year may be expressed with two characters or four characters (four preferred).
     */
    public final String pubDate;
    /**
     * The last time the content of the channel changed.
     */
    public final String lastBuildDate;
    /**
     * Specify one or more categories that the channel belongs to. Follows the same rules as the <item>-level category element.
     */
    public final List<Category> categories;
    /**
     * A string indicating the program used to generate the channel.
     */
    public final String generator;
    /**
     * A URL that points to the documentation for the format used in the RSS file. It's probably a pointer to this page:
     * http://www.rssboard.org/rss-specification . It's for people who might stumble across an RSS file on a Web server 25
     * years from now and wonder what it is.
     */
    public final String docs;
    /**
     * Allows processes to register with a cloud to be notified of updates to the channel, implementing a lightweight
     * publish-subscribe protocol for RSS feeds. More info here.
     */
    public final Cloud cloud;
    /**
     * ttl stands for time to live. It's a number of minutes that indicates how long a channel can be cached before refreshing
     * from the source.
     */
    public final int ttl;
    /**
     * Specifies a GIF, JPEG or PNG image that can be displayed with the channel.
     */
    public final Image image;
    /**
     * The PICS rating for the channel. See http://www.w3.org/PICS/ for more information on this.
     */
    public final String rating;
    /**
     * A hint for aggregators telling them which hours they can skip. This element contains up to 24 <hour> sub-elements whose
     * value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not
     * read the channel on hours listed in the &lt;skipHours&gt; element. The hour beginning at midnight is hour zero.
     */
    public final Collection<Integer> skipHours;
    /**
     * A hint for aggregators telling them which days they can skip. This element contains up to seven <day> sub-elements whose
     * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators may not read the channel during
     * days listed in the &lt;skipDays&gt; element.
     */
    public final Collection<String> skipDays;
    /**
     * List of items.
     * 
     * @see Item
     */
    public final List<Item> items;

    /**
     * This constructor checks each argument for null value and replaces it with a default non-null value.
     * 
     * @param title
     * @param link
     * @param description
     */
    @SuppressWarnings("unchecked")
    public Feed(String title, String link, String description, String language, String copyright, String managingEditor,
            String webMaster, String pubDate, String lastBuildDate, List<Category> categories, String generator, String docs,
            Cloud cloud, int ttl, Image image, String rating, Collection<Integer> skipHours, Collection<String> skipDays,
            List<Item> items) {
        super();
        this.title = title != null ? title : StringUtils.EMPTY;
        this.link = link != null ? link : StringUtils.EMPTY;
        this.description = description != null ? description : StringUtils.EMPTY;
        this.language = language != null ? language : StringUtils.EMPTY;
        this.copyright = copyright != null ? copyright : StringUtils.EMPTY;
        this.managingEditor = managingEditor != null ? managingEditor : StringUtils.EMPTY;
        this.webMaster = webMaster != null ? webMaster : StringUtils.EMPTY;
        this.pubDate = pubDate != null ? pubDate : StringUtils.EMPTY;
        this.lastBuildDate = lastBuildDate != null ? lastBuildDate : StringUtils.EMPTY;
        this.categories = (List<Category>) (categories != null ? Collections.unmodifiableList(categories) : Collections
                .emptyList());
        this.generator = generator != null ? generator : StringUtils.EMPTY;
        this.docs = docs != null ? docs : StringUtils.EMPTY;
        this.cloud = cloud != null ? cloud : new Cloud();
        this.ttl = ttl > 0 ? ttl : Integer.MAX_VALUE;
        this.image = image != null ? image : new Image(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        this.rating = rating != null ? rating : StringUtils.EMPTY;
        this.skipHours = (Collection<Integer>) (skipHours != null ? skipHours : Collections.emptySet());
        this.skipDays = (Collection<String>) (skipDays != null ? skipDays : Collections.emptySet());
        this.items = (List<Item>) (items != null ? Collections.unmodifiableList(items) : Collections.emptyList());
    }
}
