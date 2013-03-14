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
 * RSS enclosure element. See http://www.rssboard.org/rss-specification#ltenclosuregtSubelementOfLtitemgt for more details.<br>
 * <br>
 * Example:<br>
 * {@code <enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3" length="12216320" type="audio/mpeg" />}
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class Enclosure implements NonNullFields {
    /**
     * {@code url} says where the enclosure is located.
     */
    public final String url;
    /**
     * {@code length} says how big the enclosure is in bytes.
     */
    public final int length;
    /**
     * {@code type} says what the enclosure type is, a standard MIME type.
     */
    public final String type;

    /**
     * Construct an enclosure. Each argument is checked against null and replaced with default non-null value if needed.
     * 
     * @param url
     * @param length
     * @param type
     */
    public Enclosure(String url, int length, String type) {
        super();
        this.url = url != null ? url : "";
        this.length = length;
        this.type = type != null ? type : "";
    }

    @Override
    public String toString() {
        return "Enclosure [url=" + url + ", length=" + length + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + length;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Enclosure other = (Enclosure) obj;
        if (length != other.length)
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

}
