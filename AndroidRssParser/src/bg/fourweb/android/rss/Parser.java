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

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;
import bg.fourweb.android.vercompat.CompatHttp;

public class Parser {

    final static class ProgressInputStream extends InputStream {

        private int nRead = 0;
        private int nextStep;

        private final int step;
        private final int CONTENT_LENGTH;
        private final InputStream delegate;

        public ProgressInputStream(InputStream delegate, int contentLength) {
            this.delegate = delegate;
            if (contentLength <= 0) {
                CONTENT_LENGTH = 1; // because it's used as divisor
            } else {
                CONTENT_LENGTH = contentLength;
            }
            nextStep = step = CONTENT_LENGTH / 100 * 10;
        }

        @Override
        public int read() throws IOException {
            try {
                return delegate.read();
            } finally {
                if (++nRead >= nextStep) {
                    nextStep += step;
                    notifyProgressObservers(100 * nRead / CONTENT_LENGTH + 1);
                }
            }
        }

        // ---
        // progress functions
        // @formatter:off
        private final List<ProgressObserver> observers = new LinkedList<ProgressObserver>();
        public void addProgressObserver(ProgressObserver obs) {observers.add(obs);}
        public int countProgressObservers() {return observers.size();}
        public void deleteProgressObserver(ProgressObserver obs) {observers.remove(obs);}
        public void deleteProgressObservers() {observers.clear();}
        /** If there is no content length specified, update with the current number of bytes read. */
        protected void notifyProgressObservers(int progress) {for (final ProgressObserver o : observers) {o.update(progress);}}
        // @formatter:on

    }

    public static interface StopCondition {
        public boolean shouldStop();
    }

    public static class StopException extends RuntimeException {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public StopException() {
            super();
        }

        public StopException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public StopException(String detailMessage) {
            super(detailMessage);
        }

        public StopException(Throwable throwable) {
            super(throwable);
        }

    };

    public static interface ProgressObserver {
        public void update(int progress);
    }

    private static final boolean DBG = true;
    private static final String TAG = "Parser";

    private AbsRssHandler h;
    private ParserOptions opts = ParserOptions.DEFAULT;
    private StopCondition stopCond;

    public Parser() {
    }

    public void setStopCondition(StopCondition stopCond) {
        this.stopCond = stopCond;
    }

    public void setOpts(ParserOptions opts) {
        this.opts = opts;
    }

    public void setHandler(AbsRssHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        }
        this.h = handler;
    }

    private AbsRssHandler prepareHandler() {
        if (h == null) {
            h = new RssHandler(opts);
            if (stopCond != null) {
                h.setStopCondition(stopCond);
            }
        }
        return h;
    }

    public Feed parse(final String url) {
        return parse(url, null);
    }

    public Feed parse(final String url, ProgressObserver obs) {
        final HttpClient client = CompatHttp.getHttpClient();
        
        // configure the HTTP client
        client.getParams().setIntParameter("http.socket.timeout", opts.getConnectionTimeout());

        try {
            final HttpUriRequest get = new HttpGet(url);
            final HttpResponse resp = client.execute(get);

            // prepare content length (it's needed for progress indication)
            int contentLength = 0;
            final Header[] contentLengthHeaders = resp.getHeaders("Content-Length");
            if (ArrayUtils.isEmpty(contentLengthHeaders) == false) {
                final Header contentLengthHeader = contentLengthHeaders[0];
                final String contentLengthValue = contentLengthHeader.getValue();
                if (StringUtils.isEmpty(contentLengthValue) == false) {
                    try {
                        contentLength = Integer.parseInt(contentLengthValue);
                    } catch (NumberFormatException ignored) {
                        // NOP
                    }
                }
            }

            // prepare progress observers
            final ProgressInputStream body = new ProgressInputStream(resp.getEntity().getContent(), contentLength);
            if (obs != null) {
                body.addProgressObserver(obs);
            }

            // handler
            h = prepareHandler();

            // parse
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(body, h);
            return h.getProductFeed();
        } catch (Throwable e) {
            if (DBG) {
                Log.w(TAG, "", e);
            }
        } finally {
            CompatHttp.closeHttpClient(client);
        }
        return null;
    }

}
