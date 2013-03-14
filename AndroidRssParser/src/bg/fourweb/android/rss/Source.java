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
 * RSS channel that the item came from
 * 
 * @author Yordan Miladinov <yordan@4web.bg>
 * 
 */
public class Source implements NonNullFields {

    /**
     * The RSS channel that the item came from. Its value is the name of the RSS channel that the item came from, derived from
     * its {@code <title>}. It has one required attribute, {@code url}, which links to the XMLization of the source.
     */
    public final String value;
    /**
     * The {@code <source>} element has one required attribute, {@code url}, which links to the XMLization of the source.
     */
    public final String url;

    public Source(String value, String url) {
        super();
        this.value = value != null ? value : "";
        this.url = url != null ? url : "";
    }

}
