package ru.itmo.sortvis.osm;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class OsmService {

    public static final String baseUrlString = "https://api.openstreetmap.org/";

    private static final HttpClient client = new DefaultHttpClient();

    public void get(double left, double bottom, double right, double top) throws IOException {
        HttpGet httpGet = new HttpGet(String.format("%s/api/0.6/map?bbox=%f,%f,%f,%f", baseUrlString, left, bottom, right, top));

        HttpResponse response = client.execute(httpGet);
        Header[] allHeaders = response.getAllHeaders();

//        Files.createTempFile("sortfis", "", file)
//        Files.newOutputStream()
//        response.getEntity().writeTo();
        System.out.println(Arrays.toString(allHeaders));
    }

    public static void main(String[] args) throws IOException {
        new OsmService().get(1,1,1,1);
    }
}
