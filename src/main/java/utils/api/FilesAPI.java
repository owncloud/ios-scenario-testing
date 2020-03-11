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

        String url = urlServer+davEndpoint+user+"/"+itemName+"/";

        Request request = deleteRequest(url);

        try {
            Response response = httpClient.newCall(request).execute();
            response.body().close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void createFolder(String folderName) {

        String url = urlServer+davEndpoint+user+"/"+folderName+"/";

        Request request = davRequest(url, "MKCOL", null);

        try {
            Response response = httpClient.newCall(request).execute();
            response.body().close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean itemExist(String itemName) {

        String url = urlServer+davEndpoint+user+"/"+itemName;

        Response response = null;

        Request request = davRequest(url, "PROPFIND", null);

        try {
            response = httpClient.newCall(request).execute();
            response.body().close();
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

    public ArrayList<OCFile> listItems(String path) {
        Response response = null;
        try {
            String url = urlServer + davEndpoint + user + path;

            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                    basicPropfindBody);

            Request request = davRequest(url, "PROPFIND", body);
            response = httpClient.newCall(request).execute();
            response.body().close();

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
        httpResponse.body().close();
        return handler.getListFiles();
    }
}