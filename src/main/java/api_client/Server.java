package api_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.status_models.INSERT_STATUS;
import models.status_models.LOGIN_STATUS;
import models.status_models.REGISTRE_STATUS;
import models.transfer_models.*;

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

        LOGIN_STATUS ls = null;
        try
        {
            ls = om.readValue(response, LOGIN_STATUS.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return ls.equals(LOGIN_STATUS.LOGIN_SUCCESS);
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

        REGISTRE_STATUS rs = null;
        try
        {
            rs = om.readValue(response, REGISTRE_STATUS.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return rs.equals(REGISTRE_STATUS.REGISTER_SUCCESS);
    }

    public static UserCalendarIndex getCalendarIndex(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("json",om.writeValueAsString(l));
            response = new HTTPSClient().post("https://127.0.0.1:8443//calendar/getindex",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return null;
        }

        UserCalendarIndex index = null;

        try
        {
            index = om.readValue(response, UserCalendarIndex.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return null;
        }

        return index;
    }

    public static boolean addCalendar(String username, String password, Calendar calendar)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        AddCalendar ac = new AddCalendar(l,calendar);

        String response = null;
        try
        {
            params.put("json",om.writeValueAsString(ac));
            response = new HTTPSClient().post("https://127.0.0.1:8443//calendar/add",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        INSERT_STATUS answer = null;

        try
        {
            answer = om.readValue(response, INSERT_STATUS.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return answer.equals(INSERT_STATUS.INSERT_OK);
    }
}
