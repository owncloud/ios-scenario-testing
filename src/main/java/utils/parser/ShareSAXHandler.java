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
    public void endElement(String uri, String localName, String node)
            throws SAXException {
        switch (node) {
            case ("id"):{
                share.setId(text);
                break;
            }
            case ("uid_owner"):{
                share.setOwner(text);
                break;
            }
            case ("share_type"):{
                share.setType(text);
                break;
            }
            case ("share_with"):{
                share.setShareeName(text);
                break;
            }
            case ("name"):{
                share.setLinkName(text);
                break;
            }
            case ("itemName"):{
                share.setItemName(text.substring(1, text.length()));
                break;
            }
            case ("permissions"):{
                share.setPermissions(text);
                break;
            }
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
