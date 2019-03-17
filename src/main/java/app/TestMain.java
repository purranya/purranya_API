package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.transfer_models.LoginStatus;
import models.transfer_models.Presence;

import java.util.HashMap;

public class TestMain
{
    public static void main(String[] args)
    {
        String response = new HTTPSClient().get("https://127.0.0.1:8443/presense",null);
        ObjectMapper om = new ObjectMapper();
        try
        {
            Presence p = om.readValue(response, Presence.class);
            System.out.println(String.format("Połączenie: %b",p.isPresent()));
        } catch ( Exception e )
        {
            e.printStackTrace();
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("username","kamur");
        params.put("password","kamil1234");

        String response2 = new HTTPSClient().post("https://127.0.0.1:8443/login",params);
        try
        {
            LoginStatus ls = om.readValue(response2, LoginStatus.class);
            System.out.println(String.format("Login: %s",ls.toString()));
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
