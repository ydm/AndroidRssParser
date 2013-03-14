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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.Builder;

/**
 * Build an Item using the builder pattern. Objects of this class <b>are not</b> thread safe.<br>
 * For more information on the pattern read https://en.wikipedia.org/wiki/Builder_pattern
 * 
 * @author Yordan Miladinov &lt;yordan@4web.bg&gt;
 * 
 */
public class ItemBuilder implements Builder<Item>, Mixin.CategoryBuilder<ItemBuilder>, Mixin.EnclosureBuilder<ItemBuilder>,
        Mixin.GuidBuilder<ItemBuilder>, Mixin.SourceBuilder<ItemBuilder> {

    // simple string values
    private String title, link, description, author, comments, pubDate;

    public ItemBuilder title(String value) {
        title = value;
        return this;
    }

    public ItemBuilder link(String value) {
        link = value;
        return this;
    }

    public ItemBuilder description(String value) {
        description = value;
        return this;
    }

    public ItemBuilder author(String value) {
        author = value;
        return this;
    }

    public ItemBuilder comments(String value) {
        comments = value;
        return this;
    }

    public ItemBuilder pubDate(String value) {
        pubDate = value;
        return this;
    }

    @Override
    public Item build() {
        return new Item(title, link, description, author, buildCategories(), comments, buildEnclosures(), buildGuid(),
                pubDate, buildSource(), metaValues);
    }

    // ---
    // mixins follow

    // category
    // @formatter:off
    private final Mixin.CategoryBuilder<ItemBuilder> categoryMixin = new Mixin.CategoryBuilderImpl<ItemBuilder>();
    @Override public ItemBuilder addCategory(String value) {categoryMixin.addCategory(value); return this;}
    @Override public ItemBuilder addCategory(String value, String domain) {categoryMixin.addCategory(value, domain); return this;}
    @Override public List<Category> buildCategories() {return categoryMixin.buildCategories();}
    // @formatter:on

    // enclosure
    // @formatter:off
    private final Mixin.EnclosureBuilder<ItemBuilder> enclosureMixin = new Mixin.EnclosureBuilderImpl<ItemBuilder>();
    @Override public ItemBuilder addEnclosureUrl(String url, int length, String mimeType) {enclosureMixin.addEnclosureUrl(url, length, mimeType); return this;}
    @Override public Enclosure getFirstEnclosure() {return enclosureMixin.getFirstEnclosure();}
    @Override public List<Enclosure> buildEnclosures() {return enclosureMixin.buildEnclosures();}
    // @formatter:on

    // guid
    // @formatter:off
    private final Mixin.GuidBuilder<ItemBuilder> guidMixin = new Mixin.GuidBuilderImpl<ItemBuilder>();
    @Override public ItemBuilder guidValue(String value) {guidMixin.guidValue(value); return this;}
    @Override public ItemBuilder guid(String value, boolean isPermaLink) {guidMixin.guid(value, isPermaLink); return this;}
    @Override public ItemBuilder guid(String value, String isPermaLink) {guidMixin.guid(value, isPermaLink); return this;}
    @Override public ItemBuilder guidIsPermaLink(boolean value) {guidMixin.guidIsPermaLink(value); return this;}
    @Override public ItemBuilder guidIsPermaLink(String value) {guidMixin.guidIsPermaLink(value); return this;}
    @Override public Guid buildGuid() {return guidMixin.buildGuid();}
    // @formatter:on

    // source
    // @formatter:off
    private final Mixin.SourceBuilder<ItemBuilder> sourceMixin = new Mixin.SourceBuilderImpl<ItemBuilder>();
    @Override public Source buildSource() {return sourceMixin.buildSource();}
    @Override public ItemBuilder source(String value, String url) {sourceMixin.source(value, url); return this;}
    @Override public ItemBuilder sourceUrl(String url) {sourceMixin.sourceUrl(url); return this;}
    @Override public ItemBuilder sourceValue(String value) {sourceMixin.sourceValue(value); return this;}
    // @formatter:on

    // meta values
    /* package */final Map<String, String> metaValues = new HashMap<String, String>();

}
