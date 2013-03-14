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

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * http://www.rssboard.org/rss-specification
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class Item implements NonNullFields, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * The title of the item.
     */
    public final String title;
    /**
     * The URL of the item.
     */
    public final String link;
    /**
     * The item synopsis.
     */
    public final String description;
    /**
     * Email address of the author of the item.
     */
    public final String author;
    /**
     * Includes the item in one or more categories.
     */
    public final List<Category> categories;
    /**
     * URL of a page for comments relating to the item.
     */
    public final String comments;
    /**
     * Describes a media object that is attached to the item.
     */
    public final List<Enclosure> enclosures;
    /**
     * The guid value is a string that uniquely identifies the item
     */
    public final Guid guid;
    /**
     * Indicates when the item was published.
     */
    public final String pubDate;
    /**
     * The pubDate field parsed using the RFC822 format.
     */
    public final long pubDateMillis;
    /**
     * The RSS channel that the item came from. Its value is the name of the RSS channel that the item came from, derived from
     * its {@code <title>}. It has one required attribute, {@code url}, which links to the XMLization of the source.
     */
    public final Source source;
    /**
     * The meta values hash map can be used for any additional item data for which there are no dedicated fields. For example
     * feeds often include unstandardized information inside the &lt;item&gt; tag. One can use the meta values map to hold that
     * for him.
     */
    public final Map<String, String> metaValues;

    /**
     * Construct an item. Each field is checked against null and replaced with a default non-null value if needed.
     * 
     * @param title
     * @param link
     * @param description
     * @param author
     * @param categories
     * @param comments
     * @param enclosures
     * @param guid
     * @param pubDate
     * @param source
     */
    @SuppressWarnings("unchecked")
    public Item(String title, String link, String description, String author, List<Category> categories, String comments,
            List<Enclosure> enclosures, Guid guid, String pubDate, Source source, Map<String, String> metaValues) {
        this.title = title != null ? title : "";
        this.link = link != null ? link : "";
        this.description = description != null ? description : "";
        this.author = author != null ? author : "";
        this.categories = (List<Category>) (categories != null ? Collections.unmodifiableList(categories) : Collections
                .emptyList());
        this.comments = comments;
        this.enclosures = (List<Enclosure>) (enclosures != null ? Collections.unmodifiableList(enclosures) : Collections
                .emptyList());
        this.guid = guid != null ? guid : new Guid("", false);
        this.pubDate = pubDate != null ? pubDate : "";
        this.source = source != null ? source : new Source("", "");
        long tmp = 0;
        try {
            tmp = StringUtils.isEmpty(pubDate) == false ? DateUtils.parseRFC822(pubDate).getTime() : tmp;
        } catch (ParseException ignored) {
        }
        this.pubDateMillis = tmp;
        if (metaValues == null) {
            metaValues = new HashMap<String, String>();
        }
        this.metaValues = Collections.unmodifiableMap(metaValues);
    }

    @Override
    public String toString() {
        return "Item [title=" + title + ", link=" + link + "]";
    }

}
