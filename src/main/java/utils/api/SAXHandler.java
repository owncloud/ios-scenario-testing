package utils.api;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private PublicShare publicShare;
    private static String text = null;


    @Override
    public void startElement(String uri, String localName, String node, Attributes attributes)
            throws SAXException {
            if (node.equals("element")){
                publicShare = new PublicShare();
            }

    }

    @Override
    public void endElement(String uri, String localName, String node) throws SAXException {
            if (node.equals("id")){
                publicShare.setId(text);
            } else if (node.equals("uid_owner")){
                publicShare.setOwner(text);
            } else if (node.equals("share_type")){
                publicShare.setType(text);
            } else if (node.equals("share_with")){
                publicShare.setShareeName(text);
            }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text = String.copyValueOf(ch, start, length).trim();
    }

    public PublicShare getPublicShare(){
        return publicShare;
    }

}

