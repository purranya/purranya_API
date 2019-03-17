package api_client;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HTTPSClient
{
    static {
        TrustManager[] noSSLCheck = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        HostnameVerifier noHostNameCheck = (a,b) -> true;

        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, noSSLCheck, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(noHostNameCheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String url, HashMap<String,String> parameters)
    {
        StringBuffer response = new StringBuffer();

        String getParams = "";

        if(parameters!=null)
        {
            for(Map.Entry<String,String> entry : parameters.entrySet())
            {
                if(getParams.equals(""))
                {
                    getParams += String.format("?%s=%s",entry.getKey(),entry.getValue());
                }
                else
                {
                    getParams += String.format("&%s=%s",entry.getKey(),entry.getValue());
                }
            }
        }

        try
        {
            HttpsURLConnection conn = (HttpsURLConnection)(new URL(url+getParams)).openConnection();

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

    public String post(String url,Map<String,String> parameters)
    {
        StringBuffer response = new StringBuffer();

        String postParams = "";

        if(parameters!=null)
        {
            for(Map.Entry<String,String> entry : parameters.entrySet())
            {
                if(postParams.equals(""))
                {
                    postParams += String.format("%s=%s",entry.getKey(),entry.getValue());
                }
                else
                {
                    postParams += String.format("&%s=%s",entry.getKey(),entry.getValue());
                }
            }
        }

        try
        {
            HttpsURLConnection conn = (HttpsURLConnection)(new URL(url)).openConnection();
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes(postParams);
            writer.flush();
            writer.close();



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
        return response.toString();
    }
}
