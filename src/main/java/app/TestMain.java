package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Presence;

public class TestMain
{
    public static void main(String[] args)
    {
        System.setProperty("javax.net.ssl.trustStore","/home/kamil/gity/purranya_API/src/main/resources/certificate.jks");
        String response = new HTTPSClient().get("https://127.0.0.1:8443/presense",null);
        System.out.println(response);
        ObjectMapper om = new ObjectMapper();
        try
        {
            Presence p = om.readValue(response, Presence.class);
            System.out.println(String.format("Połączenie: %b",p.isPresent()));
        } catch ( Exception e )
        {
            e.printStackTrace();
        }

    }
}
