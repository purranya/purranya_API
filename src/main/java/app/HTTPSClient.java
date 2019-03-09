package app;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Map;

public class HTTPSClient
{
    public String get(String url, Map<String,String> parameters)
    {
        StringBuffer response = new StringBuffer();

        try
        {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname,sslSession)->{ return hostname.equals("localhost") || hostname.equals("127.0.0.1"); });
            HttpsURLConnection conn = (HttpsURLConnection)(new URL(url)).openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while(reader.ready())
            {
                response.append(reader.readLine());
            }
        }
        catch ( MalformedURLException e )
        {
            System.out.println("Malformed URL!");
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            System.out.println("Could not open connection!");
            e.printStackTrace();
        }
        finally
        {
            return response.toString();
        }
    }
}
