package utils.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import utils.entities.OCShare;

public class ShareSAXHandler extends DefaultHandler {

    private OCShare share;
    private ArrayList<OCShare> allShares;
    private StringBuilder text = new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String node, Attributes attributes) {
        text.setLength(0);
        switch (node) {
            case ("ocs"): {
                allShares = new ArrayList<>();
            }
            case ("element"): {
                share = new OCShare();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String node) {
        String value = text.toString().trim();
        switch (node) {
            case ("id"): {
                share.setId(value);
                break;
            }
            case ("uid_file_owner"): {
                share.setOwner(value);
                break;
            }
            case ("share_type"): {
                share.setType(value);
                break;
            }
            case ("share_with"): {
                share.setShareeName(value);
                break;
            }
            case ("name"): {
                share.setLinkName(value);
                break;
            }
            case ("path"): {
                share.setItemName(text.substring(1, value.length()));
                break;
            }
            case ("permissions"): {
                share.setPermissions(value);
                break;
            }
            case ("expiration"): {
                share.setExpiration(value);
                break;
            }
            case ("element"): {
                allShares.add(share);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    public OCShare getShare() {
        return share;
    }

    public ArrayList<OCShare> getAllShares() {
        return allShares;
    }

}
