package utils.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import utils.entities.OCShare;

public class ShareSAXHandler extends DefaultHandler {

    private OCShare share;
    private static String text = null;


    @Override
    public void startElement(String uri, String localName, String node, Attributes attributes)
            throws SAXException {
        if (node.equals("element")){
            share = new OCShare();
        }

    }

    @Override
    public void endElement(String uri, String localName, String node) throws SAXException {
        if (node.equals("id")){
            share.setId(text);
        } else if (node.equals("uid_owner")){
            share.setOwner(text);
        } else if (node.equals("share_type")){
            share.setType(text);
        } else if (node.equals("share_with")){
            share.setShareeName(text);
        } else if (node.equals("name")){
            share.setItemName(text);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text = String.copyValueOf(ch, start, length).trim();
    }

    public OCShare getShare(){
        return share;
    }

}
