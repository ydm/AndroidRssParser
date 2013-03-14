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
 * Includes the item in one or more categories.
 * 
 * @author Yordan Miladinov <yordan@4web.bg>
 * 
 */
public class Category implements NonNullFields {

    /**
     * The {@code <category>} element has one optional attribute, {@code domain}, a string that identifies a categorization
     * taxonomy.
     */
    public final String domain;
    /**
     * The value of the element is a forward-slash-separated string that identifies a hierarchic location in the indicated
     * taxonomy. Processors may establish conventions for the interpretation of categories.
     */
    public final String value;

    public Category(String value) {
        this(value, null);
    }

    /**
     * Each value is being checked if it's null and replaced by an empty string.
     * 
     * @param value
     * @param domain
     */
    public Category(String value, String domain) {
        super();
        this.value = value != null ? value : "";
        this.domain = domain != null ? domain : "";
    }

    @Override
    public String toString() {
        return "Category [domain=" + domain + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((domain == null) ? 0 : domain.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Category other = (Category) obj;
        if (domain == null) {
            if (other.domain != null)
                return false;
        } else if (!domain.equals(other.domain))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

}
