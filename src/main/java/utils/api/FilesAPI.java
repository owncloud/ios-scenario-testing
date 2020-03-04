package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.entities.OCFile;
import utils.parser.FileSAXHandler;

public class FilesAPI extends CommonAPI {

    private final String davEndpoint = "/remote.php/dav/files/";

    public FilesAPI(){
        super();
    }

    public void removeItem(String itemName) {

        String removeURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(removeURL+itemName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void createFolder(String folderName) {

        String createURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(createURL+folderName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .method("MKCOL", null)
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public boolean itemExist(String itemName) {
        String createURL = urlServer+davEndpoint+user+"/";
        Response response = null;

        Request request = new Request.Builder()
                .url(createURL+itemName)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .method("PROPFIND", null)
                .build();

        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

        switch (response.code()/100){
            case(2): { //Response 2xx, item exists
                return true;
            }
            case(4): { //Response 4xx, item does not exist
                return false;
            }
            default: {
                return false;
            }
        }
    }

    public ArrayList<OCFile> listItems() {
        try {
            String createURL = urlServer + davEndpoint + user + "/";
            Response response = null;

            String xml = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
                    "<propfind xmlns=\"DAV:\" xmlns:CAL=\"urn:ietf:params:xml:ns:caldav\" xmlns:CARD=\"urn:ietf:params:xml:ns:carddav\" xmlns:SABRE=\"http://sabredav.org/ns\" xmlns:OC=\"http://owncloud.org/ns\">\n" +
                    "  <prop>\n" +
                    "    <displayname />\n" +
                    "    <getcontenttype />\n" +
                    "    <resourcetype />\n" +
                    "    <getcontentlength />\n" +
                    "    <getlastmodified />\n" +
                    "    <creationdate />\n" +
                    "    <getetag />\n" +
                    "    <quota-used-bytes />\n" +
                    "    <quota-available-bytes />\n" +
                    "    <OC:permissions />\n" +
                    "    <OC:id />\n" +
                    "    <OC:size />\n" +
                    "    <OC:privatelink />\n" +
                    "  </prop>\n" +
                    "</propfind>";

            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);


            Request request = new Request.Builder()
                    .url(createURL)
                    .addHeader("OCS-APIREQUEST", "true")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Authorization", "Basic " + credentialsB64)
                    .addHeader("Host", host)
                    .method("PROPFIND", body)
                    .build();
            response = httpClient.newCall(request).execute();
            return getList(response);
        } catch (IOException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<OCFile> getList(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException {
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        FileSAXHandler handler = new FileSAXHandler();
        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        return handler.getListFiles();
    }
}