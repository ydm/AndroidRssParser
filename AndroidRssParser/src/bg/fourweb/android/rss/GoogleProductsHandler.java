package bg.fourweb.android.rss;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.webkit.MimeTypeMap;

public class GoogleProductsHandler extends RssHandler {

    // @formatter:off
    private static final int ELT_G_ID               = 1;
    private static final int ELT_G_PRODUCT_CATEGORY = 2;
    private static final int ELT_G_PRODUCT_TYPE     = 3;
    private static final int ELT_G_IMAGE_LINK       = 4;
    private static final int ELT_G_CONDITION        = 5;
    private static final int ELT_G_AVAILABILITY     = 6;
    private static final int ELT_G_PRICE            = 7;
    private static final int ELT_G_BRAND            = 8;
    private static final int ELT_G_GENDER           = 9;
    private static final int ELT_G_AGE_GROUP        = 10;
    private static final int ELT_G_COLOR            = 11;
    private static final int ELT_G_SIZE             = 12;
    
    private static final Map<String, Integer> elements = new HashMap<String, Integer>();
    static {
        elements.put("id"                      , ELT_G_ID);
        elements.put("google_product_category" , ELT_G_PRODUCT_CATEGORY);
        elements.put("product_type"            , ELT_G_PRODUCT_TYPE);
        elements.put("image_link"              , ELT_G_IMAGE_LINK);
        elements.put("condition"               , ELT_G_CONDITION);
        elements.put("availability"            , ELT_G_AVAILABILITY);
        elements.put("price"                   , ELT_G_PRICE);
        elements.put("brand"                   , ELT_G_BRAND);
        elements.put("gender"                  , ELT_G_GENDER);
        elements.put("age_group"               , ELT_G_AGE_GROUP);
        elements.put("color"                   , ELT_G_COLOR);
        elements.put("size"                    , ELT_G_SIZE);
    }
    // @formatter:on

    private int currentElement;
    private String currentElementTagName;
    private StringBuilder valueB;

    public GoogleProductsHandler(ParserOptions opts) {
        super(opts);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        Integer tmp = elements.get(localName);
        if (tmp == null) {
            // unknown element
            currentElement = -1;
            return;
        }

        currentElement = tmp;
        switch (currentElement) {
        case ELT_G_ID:
        case ELT_G_PRODUCT_CATEGORY:
        case ELT_G_PRODUCT_TYPE:
        case ELT_G_IMAGE_LINK:
        case ELT_G_CONDITION:
        case ELT_G_AVAILABILITY:
        case ELT_G_PRICE:
        case ELT_G_BRAND:
        case ELT_G_GENDER:
        case ELT_G_AGE_GROUP:
        case ELT_G_COLOR:
        case ELT_G_SIZE:
            valueB = new StringBuilder();
            currentElementTagName = localName;
            break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        switch (currentElement) {
        case ELT_G_ID:
        case ELT_G_PRODUCT_CATEGORY:
        case ELT_G_PRODUCT_TYPE:
        case ELT_G_IMAGE_LINK:
        case ELT_G_CONDITION:
        case ELT_G_AVAILABILITY:
        case ELT_G_PRICE:
        case ELT_G_BRAND:
        case ELT_G_GENDER:
        case ELT_G_AGE_GROUP:
        case ELT_G_COLOR:
        case ELT_G_SIZE:
            if (valueB != null) {
                valueB.append(new String(ch, start, length));
            } else {
                // most probably bad XML
            }
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        String v = valueB != null ? valueB.toString() : StringUtils.EMPTY;
        switch (currentElement) {
        case ELT_G_IMAGE_LINK:
            if (StringUtils.isEmpty(v) == false) {
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(v));
                currentItemB.addEnclosureUrl(v, 0, mimeType);
            }
        case ELT_G_ID:
        case ELT_G_PRODUCT_CATEGORY:
        case ELT_G_PRODUCT_TYPE:
        case ELT_G_CONDITION:
        case ELT_G_AVAILABILITY:
        case ELT_G_PRICE:
        case ELT_G_BRAND:
        case ELT_G_GENDER:
        case ELT_G_AGE_GROUP:
        case ELT_G_COLOR:
        case ELT_G_SIZE:
            currentItemB.metaValues.put(currentElementTagName, v);
            break;
        }
    }

}
