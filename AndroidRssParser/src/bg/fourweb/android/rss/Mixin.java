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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Namespace for various RSS builder mixins.
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class Mixin {

    // ---
    // category builder

    public static interface CategoryBuilder<T> /* extends Builder<List<Category>> */{
        public T addCategory(String value);

        public T addCategory(String value, String domain);

        public List<Category> buildCategories();
    }

    public static class CategoryBuilderImpl<T> implements CategoryBuilder<T> {
        /**
         * Hold categories. Each element of the list contains a string array with the following values:
         * <ul>
         * <li>0: {@code <category>} element value</li>
         * <li>1: {@code <category> domain} attribute</li>
         * </ul>
         */
        private List<Category> categories;

        @Override
        public T addCategory(String value) {
            addCategory(value, null);
            return null;
        }

        @Override
        public T addCategory(String value, String domain) {
            if (categories == null) {
                categories = new LinkedList<Category>();
            }
            categories.add(new Category(value, domain));
            return null;
        }

        @Override
        public List<Category> buildCategories() {
            return categories;
        }
    }

    // ---
    // enclosure builder

    public static interface EnclosureBuilder<T> /* extends Builder<List<Enclosure>> */{

        public T addEnclosureUrl(String url, int length, String mimeType);

        public List<Enclosure> buildEnclosures();

        public Enclosure getFirstEnclosure();

    }

    public static class EnclosureBuilderImpl<T> implements EnclosureBuilder<T> {
        private List<Enclosure> enclosures;

        @Override
        public T addEnclosureUrl(String url, int length, String mimeType) {
            if (enclosures == null) {
                enclosures = new LinkedList<Enclosure>();
            }
            enclosures.add(new Enclosure(url, length, mimeType));
            return null;
        }

        @Override
        public List<Enclosure> buildEnclosures() {
            return enclosures;
        }

        @Override
        public Enclosure getFirstEnclosure() {
            return enclosures != null && enclosures.size() > 0 ? enclosures.get(0) : null;
        }
    }

    // ---
    // guid builder

    public static interface GuidBuilder<T> /* extends Builder<Guid> */{
        public Guid buildGuid();

        public T guid(String value, boolean isPermaLink);

        public T guid(String value, String isPermaLink);

        public T guidIsPermaLink(boolean isPermaLink);

        public T guidIsPermaLink(String isPermaLink);

        public T guidValue(String value);
    }

    public static class GuidBuilderImpl<T> implements GuidBuilder<T> {
        private boolean isPermaLink;
        private String value;

        @Override
        public Guid buildGuid() {
            return new Guid(value, isPermaLink);
        }

        @Override
        public T guid(String value, boolean isPermaLink) {
            this.value = value;
            guidIsPermaLink(isPermaLink);
            return null;
        }

        @Override
        public T guid(String value, String isPermaLink) {
            this.value = value;
            guidIsPermaLink(isPermaLink);
            return null;
        }

        @Override
        public T guidIsPermaLink(boolean value) {
            isPermaLink = value;
            return null;
        }

        @Override
        public T guidIsPermaLink(String value) {
            if (StringUtils.isEmpty(value)) {
                // this is the default value, see the RSS 2.0 specification for more information
                isPermaLink = true;
            } else {
                isPermaLink = Boolean.parseBoolean(value.toLowerCase());
            }
            return null;
        }

        @Override
        public T guidValue(String value) {
            this.value = value;
            return null;
        }
    }

    // ---
    // source builder

    public static interface SourceBuilder<T> {
        public Source buildSource();

        public T source(String value, String url);

        public T sourceUrl(String url);

        public T sourceValue(String value);
    }

    public static class SourceBuilderImpl<T> implements SourceBuilder<T> {
        private String value, url;

        @Override
        public Source buildSource() {
            return new Source(value, url);
        }

        @Override
        public T source(String value, String url) {
            this.value = value;
            this.url = url;
            return null;
        }

        @Override
        public T sourceUrl(String url) {
            this.url = url;
            return null;
        }

        @Override
        public T sourceValue(String value) {
            this.value = value;
            return null;
        }
    }

    // ---
    // image builder

    public static interface ImageBuilder<T> {
        public Image buildImage();

        public T imageUrl(String url);

        public T imageTitle(String title);

        public T imageLink(String link);

        public T imageWidth(int width);

        public T imageHeight(int height);

        public T imageDescription(String description);
    }

    public static class ImageBuilderImpl<T> implements ImageBuilder<T> {

        private String url;
        private String title;
        private String link;
        private int width, height;
        private String description;

        @Override
        public Image buildImage() {
            return new Image(url, title, link, width, height, description);
        }

        @Override
        public T imageUrl(String url) {
            this.url = url;
            return null;
        }

        @Override
        public T imageTitle(String title) {
            this.title = title;
            return null;
        }

        @Override
        public T imageLink(String link) {
            this.link = link;
            return null;
        }

        @Override
        public T imageWidth(int width) {
            this.width = width;
            return null;
        }

        @Override
        public T imageHeight(int height) {
            this.height = height;
            return null;
        }

        @Override
        public T imageDescription(String description) {
            this.description = description;
            return null;
        }

    }

}
