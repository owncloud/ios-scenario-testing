package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.entities.OCFile;
import utils.log.Log;
import utils.parser.FileSAXHandler;

public class FilesAPI extends CommonAPI {

    public FilesAPI() throws IOException {
        super();
    }

    public void removeItem(String itemName)
            throws IOException {
        Log.log(Level.FINE, "Starts: Remove Item: " + itemName);
        String chunks[] = itemName.split("/");
        String url = urlServer + getEndpoint() + "/"+chunks[0]+"/";
        Log.log(Level.FINE, "Starts: Request remove item from server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url);
        Response response = httpClient.newCall(request).execute();
        response.close();
    }

    public void createFolder(String folderName)
            throws IOException {
        Log.log(Level.FINE, "Starts: Request create folder: " + folderName);
        String url = urlServer + getEndpoint() + "/"+folderName+"/";
        Log.log(Level.FINE, "URL: " + url);
        Request request = davRequest(url, "MKCOL", null);
        Response response = httpClient.newCall(request).execute();
        response.close();
    }

    public void pushFile(String fileName)
            throws IOException {
        Log.log(Level.FINE, "Starts: Push file: " + fileName);
        String url = urlServer + getEndpoint() + "/"+fileName+"/";
        Log.log(Level.FINE, "Starts: Request create file");
        Log.log(Level.FINE, "URL: " + url);
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                "text Example");
        Request request = davRequest(url, "PUT", body);
        Response response = httpClient.newCall(request).execute();
        response.close();
    }

    public boolean itemExist(String itemName)
            throws IOException {
        Log.log(Level.FINE, "Starts: Item exists: " + itemName);
        String url = urlServer + getEndpoint() + "/" + itemName;
        Log.log(Level.FINE, "URL: " + url);
        Response response;
        Request request = davRequest(url, "PROPFIND", null);
        response = httpClient.newCall(request).execute();
        response.close();
        switch (response.code()/100){
            case(2): {
                Log.log(Level.FINE, "Response "+response.code()+". Item exists");
                return true;
            }
            case(4): {
                Log.log(Level.FINE, "Response "+response.code()+" "
                        +response.message()+". Item does not exist");
                return false;
            }
            default: {
                Log.log(Level.WARNING, "Response neither 4xx nor 2xx. " +
                        "Something went wrong");
                return false;
            }
        }
    }

    public boolean isFolder(String itemName)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: isFolder: " + itemName);
        String url = urlServer + getEndpoint() + user + "/" + itemName;
        Log.log(Level.FINE, "URL: " + url);
        Response response;
        Request request = davRequest(url, "PROPFIND", null);
        response = httpClient.newCall(request).execute();
        boolean isFolder = getList(response).get(0).getType() == null;
        response.close();
        return isFolder;
    }

    public boolean isFavorite(String itemName)
            throws IOException, ParserConfigurationException, SAXException {
        Log.log(Level.FINE, "Starts: Check if item is favorite");
        String url = urlServer + getEndpoint() + "/" + itemName;
        Log.log(Level.FINE, "URL: " + url);
        Response response;
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                basicPropfindBody);
        Request request = davRequest(url, "PROPFIND", body);
        response = httpClient.newCall(request).execute();
        boolean isFavorite = getList(response).get(0).getFavorite().equals("1");
        response.close();
        return isFavorite;
    }

    public void setFavorite(String itemName)
            throws IOException {
        Log.log(Level.FINE, "Starts: To set item as favorite: " + itemName);
        String url = urlServer + getEndpoint() + "/" + itemName;
        Log.log(Level.FINE, "URL: " + url);
        Response response;
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                setFavoriteBody);
        Request request = davRequest(url, "PROPPATCH", body);
        response = httpClient.newCall(request).execute();
        response.close();
    }

    public ArrayList<OCFile> listItems(String path)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Request to fetch list of items from server");
        Response response;
        String url = urlServer + getEndpoint() + "/" + path;
        Log.log(Level.FINE, "URL: " + url);
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                basicPropfindBody);
        Request request = davRequest(url, "PROPFIND", body);
        response = httpClient.newCall(request).execute();
        ArrayList<OCFile> listItems = getList(response);
        response.close();
        return listItems;
    }

    private ArrayList<OCFile> getList(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException {
        //Create SAX parser
        Log.log(Level.FINE, "Starts: Get List");
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        FileSAXHandler handler = new FileSAXHandler();
        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        httpResponse.body().close();
        return handler.getListFiles();
    }
}