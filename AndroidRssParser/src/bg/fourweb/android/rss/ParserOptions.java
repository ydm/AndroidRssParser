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

public class ParserOptions {
    /* package */static final ParserOptions DEFAULT = new ParserOptions();

    static {
        DEFAULT.setCharset("utf-8");
        DEFAULT.setMaxNumItems(Integer.MAX_VALUE);
        DEFAULT.setMustPreserveWhitespace(false);
    }

    private String charset = "utf-8";
    private int connectionTimeout = 10000; // 10 seconds
    private int maxNumItems = Integer.MAX_VALUE;
    private boolean mustPreserveWhitespace;

    public String getCharset() {
        return charset;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getMaxNumItems() {
        return maxNumItems;
    }

    public boolean getMustPreserveWhitespace() {
        return mustPreserveWhitespace;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setMaxNumItems(int maxNumItems) {
        this.maxNumItems = maxNumItems;
    }

    public void setMustPreserveWhitespace(boolean mustPreserveWhitespace) {
        this.mustPreserveWhitespace = mustPreserveWhitespace;
    }
}
