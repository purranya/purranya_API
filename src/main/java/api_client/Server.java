package api_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.transfer_models.Login;
import models.transfer_models.LoginStatus;
import models.transfer_models.Presence;
import models.transfer_models.RegisterStatus;

import java.util.HashMap;

public class Server
{
    public static boolean isPresent()
    {
        String response = null;
        try
        {
            response = new HTTPSClient().get("https://127.0.0.1:8443/presense", null);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        ObjectMapper om = new ObjectMapper();
        Presence p = null;
        try
        {
            p = om.readValue(response, Presence.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return p.isPresent();
    }

    public static boolean logIn(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("json",om.writeValueAsString(l));
            response = new HTTPSClient().post("https://127.0.0.1:8443/login",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        LoginStatus ls = null;
        try
        {
            ls = om.readValue(response, LoginStatus.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return ls.equals(LoginStatus.LOGIN_SUCCESS);
    }

    public static boolean register(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("json",om.writeValueAsString(l));
            response = new HTTPSClient().post("https://127.0.0.1:8443/register",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        RegisterStatus rs = null;
        try
        {
            rs = om.readValue(response, RegisterStatus.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return rs.equals(RegisterStatus.REGISTER_SUCCESS);
    }
}
