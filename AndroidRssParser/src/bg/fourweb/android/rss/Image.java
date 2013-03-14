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

import org.apache.commons.lang3.StringUtils;

/**
 * &lt;image&gt; is an optional sub-element of &lt;channel&gt;, which contains three required and three optional sub-elements.<br>
 * <br>
 * &lt;url&gt; is the URL of a GIF, JPEG or PNG image that represents the channel.<br>
 * <br>
 * 
 * &lt;title&gt; describes the image, it's used in the ALT attribute of the HTML &lt;img&gt; tag when the channel is rendered
 * in HTML.<br>
 * <br>
 * 
 * &lt;link&gt; is the URL of the site, when the channel is rendered, the image is a link to the site. (Note, in practice the
 * image &lt;title&gt; and &lt;link&gt; should have the same value as the channel's &lt;title&gt; and &lt;link&gt;.<br>
 * <br>
 * 
 * Optional elements include &lt;width&gt; and &lt;height&gt;, numbers, indicating the width and height of the image in pixels.
 * &lt;description&gt; contains text that is included in the TITLE attribute of the link formed around the image in the HTML
 * rendering.<br>
 * <br>
 * 
 * Maximum value for width is 144, default value is 88.<br>
 * <br>
 * 
 * Maximum value for height is 400, default value is 31.<br>
 * <br>
 * 
 * @author &lt;yordan@4web.bg&&gt;
 * 
 */
public class Image implements NonNullFields {
    public static final int DEFAULT_WIDTH = 88;
    public static final int DEFAULT_HEIGHT = 31;
    public static final int MIN_WIDTH = 0;
    public static final int MIN_HEIGHT = 0;
    public static final int MAX_WIDTH = 144;
    public static final int MAX_HEIGHT = 400;
    /**
     * &lt;url&gt; is the URL of a GIF, JPEG or PNG image that represents the channel.
     */
    public final String url;
    /**
     * &lt;title&gt; describes the image, it's used in the ALT attribute of the HTML &lt;img&gt; tag when the channel is
     * rendered in HTML.
     */
    public final String title;
    /**
     * &lt;link&gt; is the URL of the site, when the channel is rendered, the image is a link to the site. (Note, in practice
     * the image &lt;title&gt; and &lt;link&gt; should have the same value as the channel's &lt;title&gt; and &lt;link&gt;.
     */
    public final String link;
    /**
     * Optional elements include &lt;width&gt; and &lt;height&gt;, numbers, indicating the width and height of the image in
     * pixels.<br>
     * <br>
     * Maximum value for width is 144, default value is 88.<br>
     * <br>
     * 
     * Maximum value for height is 400, default value is 31.<br>
     * <br>
     */
    public final int width, height;
    /**
     * &lt;description&gt; contains text that is included in the TITLE attribute of the link formed around the image in the
     * HTML rendering.
     */
    public final String description;

    public Image(String url, String title, String link) {
        this(url, title, link, DEFAULT_WIDTH, DEFAULT_HEIGHT, StringUtils.EMPTY);
    }

    public Image(String url, String title, String link, int width, int height) {
        this(url, title, link, width, height, StringUtils.EMPTY);
    }

    public Image(String url, String title, String link, int width, int height, String description) {
        super();
        this.url = url != null ? url : StringUtils.EMPTY;
        this.title = title != null ? title : StringUtils.EMPTY;
        this.link = link != null ? link : StringUtils.EMPTY;
        this.width = Math.min(MAX_WIDTH, Math.max(MIN_WIDTH, width));
        this.height = Math.min(MAX_HEIGHT, Math.max(MIN_HEIGHT, height));
        this.description = description != null ? description : StringUtils.EMPTY;
    }
}
