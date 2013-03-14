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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.Builder;

public class FeedBuilder implements Builder<Feed>, Mixin.CategoryBuilder<FeedBuilder>, Mixin.ImageBuilder<FeedBuilder> {

    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private String managingEditor;
    private String webMaster;
    private String pubDate;
    private String lastBuildDate;
    // categories are handled by mixin
    private String generator;
    private String docs;
    // TODO: cloud
    private int ttl;
    // image is handled by mixin
    private String rating;
    private final Collection<Integer> skipHours = new HashSet<Integer>();
    private final Collection<String> skipDays = new HashSet<String>();
    private final List<Item> items = new LinkedList<Item>();

    // @formatter:off
    public FeedBuilder title(String title) {this.title = title; return this;}
    public FeedBuilder link(String link) {this.link = link; return this;}
    public FeedBuilder description(String description) {this.description = description; return this;}
    public FeedBuilder language(String language) {this.language = language; return this;}
    public FeedBuilder copyright(String copyright) {this.copyright = copyright; return this;}
    public FeedBuilder managingEditor(String managingEditor) {this.managingEditor = managingEditor; return this;}
    public FeedBuilder webMaster(String webMaster) {this.webMaster = webMaster; return this;}
    public FeedBuilder pubDate(String pubDate) {this.pubDate = pubDate; return this;}
    public FeedBuilder lastBuildDate(String lastBuildDate) {this.lastBuildDate = lastBuildDate; return this;}
    public FeedBuilder generator(String generator) {this.generator = generator; return this;}
    public FeedBuilder docs(String docs) {this.docs = docs; return this;}
    public FeedBuilder ttl(int ttl) {this.ttl = ttl; return this;}
    public FeedBuilder rating(String rating) {this.rating = rating; return this;}
    public FeedBuilder addSkipHour(int h) {skipHours.add(h); return this;}
    public FeedBuilder addSkipDay(String d) {skipDays.add(d); return this;}
    public FeedBuilder addItem(Item item) {items.add(item); return this;}
    // @formatter:on

    // ---
    // mixins follow

    // cloud?
    private final Cloud buildCloud() {
        return new Cloud();
    }

    // category
    // @formatter:off
    private final Mixin.CategoryBuilder<FeedBuilder> categoryMixin = new Mixin.CategoryBuilderImpl<FeedBuilder>();
    @Override public FeedBuilder addCategory(String value) {categoryMixin.addCategory(value); return this;}
    @Override public FeedBuilder addCategory(String value, String domain) {categoryMixin.addCategory(value, domain); return this;}
    @Override public List<Category> buildCategories() {return categoryMixin.buildCategories();}
    // @formatter:on

    // image
    // @formatter:off
    private final Mixin.ImageBuilder<FeedBuilder> imageMixin = new Mixin.ImageBuilderImpl<FeedBuilder>();
    @Override public Image buildImage() {return imageMixin.buildImage();}
    @Override public FeedBuilder imageUrl(String url) {imageMixin.imageUrl(url); return this;}
    @Override public FeedBuilder imageTitle(String title) {imageMixin.imageTitle(title); return this;}
    @Override public FeedBuilder imageLink(String link) {imageMixin.imageLink(link); return this;}
    @Override public FeedBuilder imageWidth(int width) {imageMixin.imageWidth(width); return this;}
    @Override public FeedBuilder imageHeight(int height) {imageMixin.imageHeight(height); return this;}
    @Override public FeedBuilder imageDescription(String description) {imageMixin.imageDescription(description); return this;}
    // @formatter:on

    @Override
    public Feed build() {
        return new Feed(title, link, description, language, copyright, managingEditor, webMaster, pubDate, lastBuildDate,
                buildCategories(), generator, docs, buildCloud(), ttl, buildImage(), rating, skipHours, skipDays, items);
    }
}
