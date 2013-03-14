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

/**
 * RSS feed element that uniquely identifies the item. The fields of this class are guaranteed to be not null. If a field value
 * is missing it will be represented with an empty string.
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class Guid implements NonNullFields {

    public final String value;
    /**
     * If the {@code guid} element has an attribute named {@code isPermaLink} with a value of {@code true}, the reader may
     * assume that it is a permalink to the item, that is, a url that can be opened in a Web browser, that points to the full
     * item described by the {@code <item>} element. {@code isPermaLink} is optional, its default value is {@code true}. If its
     * value is {@code false}, the {@code guid} may not be assumed to be a url, or a url to anything in particular. See
     * http://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt for more information on this.
     */
    public final boolean isPermaLink;

    /**
     * Same as invoking Guid#Guid(value, true).
     * 
     * @param value
     */
    public Guid(String value) {
        this(value, true);
    }

    /**
     * The {@code value} argument is checked against null and replaced with an empty string, if needed.
     * 
     * @param value
     * @param isPermaLink
     */
    public Guid(String value, boolean isPermaLink) {
        super();
        this.value = value != null ? value : "";
        this.isPermaLink = isPermaLink;
    }

}
