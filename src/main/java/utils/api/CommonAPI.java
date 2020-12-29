package utils.api;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.log.Log;


public class CommonAPI {

    protected final OkHttpClient httpClient = getUnsafeOkHttpClient();

    protected String urlServer = System.getProperty("server");
    protected String userAgent = LocProperties.getProperties().getProperty("userAgent");
    protected String host = urlServer.split("//")[1];

    protected String user = LocProperties.getProperties().getProperty("userName1");
    protected String password = LocProperties.getProperties().getProperty("passw1");
    protected String shareeUser = LocProperties.getProperties().getProperty("userToShare");
    protected String shareePassword = LocProperties.getProperties().getProperty("userToSharePwd");
    protected String credentialsB64 = Base64.getEncoder().encodeToString((user+":"+password).getBytes());
    protected String credentialsB64Sharee = Base64.getEncoder().encodeToString((shareeUser+":"+shareePassword).getBytes());

    protected final String davEndpoint = "/remote.php/dav/files/";

    protected String basicPropfindBody = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
            "<propfind xmlns=\"DAV:\" xmlns:CAL=\"urn:ietf:params:xml:ns:caldav\"" +
            " xmlns:CARD=\"urn:ietf:params:xml:ns:carddav\" " +
            " xmlns:SABRE=\"http://sabredav.org/ns\" " +
            " xmlns:OC=\"http://owncloud.org/ns\">\n" +
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

    public CommonAPI(){
    }

    public String checkAuthMethod()
            throws IOException {
        Log.log(Level.FINE, "Checking available auth methods");
        String url = urlServer + davEndpoint;
        Log.log(Level.FINE, "Request to: " + url);
        Request request = davRequestUnauth(url, "GET");
        Response response = httpClient.newCall(request).execute();
        Headers headers = response.headers();
        response.close();
        List<String> allHeaders = headers.values("Www-Authenticate");
        for (String header : allHeaders){
            Log.log(Level.FINE, "Header to check: " + header);
            if (header.contains("Bearer")) {
                if (isOidc(urlServer)) {
                    Log.log(Level.FINE, "Auth method: OIDC");
                    return "OIDC";
                }
                Log.log(Level.FINE, "Auth method: Bearer");
                return "Bearer";
            }
        }
        Log.log(Level.FINE, "Auth method: Basic");
        return "Basic";
    }

    protected boolean isOidc(String url)
            throws IOException {
        String urlCheck = url+"/.well-known/openid-configuration";
        Request request = getRequest(url, true);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Body lenght: " + response.body().contentLength());
        boolean withBody = response.body().contentLength() > 0;
        response.close();
        if (withBody)
            return true;
        else
            return false;
    }

    public String getCapabilities(String url)
            throws IOException {
        String urlCheck = urlServer+"/ocs/v2.php/cloud/capabilities?format=json";
        Request request = getRequest(urlCheck, false);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Capabilities: " + response.body());
        String capabilities =  response.body().string();
        response.close();
        return capabilities;
    }

    protected Request davRequest(String url, String method, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+ credentialsB64)
                .addHeader("Host", host)
                .method(method, body)
                .build();
        return request;
    }

    protected Request davRequestUnauth(String url, String method) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Host", host)
                .method(method, null)
                .build();
        return request;
    }

    protected Request postRequest(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+ credentialsB64)
                .addHeader("Host", host)
                .post(body)
                .build();
        Log.log(Level.FINE, "RE: " + request.toString());
        return request;
    }

    protected Request deleteRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();
        return request;
    }

    protected Request getRequest(String url, boolean isSharee) {
        String credentials = (isSharee) ? credentialsB64Sharee : credentialsB64;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentials)
                .addHeader("Host", host)
                .get()
                .build();
        return request;
    }

    //overloaded, to use with specific credentials
    protected Request getRequest(String url, String credentials) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentials)
                .addHeader("Host", host)
                .get()
                .build();
        return request;
    }

    // Returns an okHttpClient resistent to non-secured connections. It will be posible to check
    // server APIs with no errors due to handshake problems.
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                               String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                               String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
