package bg.fourweb.android.rss;

import org.xml.sax.helpers.DefaultHandler;

import bg.fourweb.android.rss.Parser.StopCondition;
import bg.fourweb.android.rss.Parser.StopException;

public abstract class AbsRssHandler extends DefaultHandler {

    protected ParserOptions opts;
    protected StopCondition stopCondition = new StopCondition() {
        @Override
        public boolean shouldStop() {
            return false;
        }
    };

    public AbsRssHandler(ParserOptions opts) {
        if (opts == null) {
            throw new NullPointerException();
        }
        this.opts = opts;
    }

    protected void checkStopCondition() throws StopException {
        if (stopCondition.shouldStop()) {
            throw new StopException();
        }
    }

    public ParserOptions getOpts() {
        return opts;
    }

    abstract Feed getProductFeed();

    public void setStopCondition(StopCondition stopCondition) {
        if (stopCondition == null) {
            throw new NullPointerException();
        }
        this.stopCondition = stopCondition;
    }

}
