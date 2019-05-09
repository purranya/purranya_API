package api_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.transfer_models.OperationStatus;
import models.transfer_models.*;

import java.util.HashMap;

public class Server
{
    public static boolean logIn(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("login",om.writeValueAsString(l));
            response = new HTTPSClient().post("https://127.0.0.1:8443/login",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        OperationStatus ls = null;
        try
        {
            ls = om.readValue(response, OperationStatus.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return ls.equals(OperationStatus.OPERATION_SUCCESS);
    }

    public static boolean register(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("login",om.writeValueAsString(l));
            response = new HTTPSClient().post("https://127.0.0.1:8443/register",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        OperationStatus answer = null;
        try
        {
            answer = om.readValue(response, OperationStatus.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return answer.equals(OperationStatus.OPERATION_SUCCESS);
    }

    public static UserCalendarIndex getCalendarIndex(String username, String password)
    {
        HashMap<String,String> params = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        Login l = new Login(username,password);

        String response = null;
        try
        {
            params.put("login",om.writeValueAsString(l));
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

        String response = null;
        try
        {
            params.put("login",om.writeValueAsString(l));
            params.put("model",om.writeValueAsString(calendar));
            response = new HTTPSClient().post("https://127.0.0.1:8443//calendar/add",params);
        } catch (Exception e)
        {
            System.err.println("Could not connect to server");
            e.printStackTrace();
            return false;
        }

        OperationStatus answer = null;

        try
        {
            answer = om.readValue(response, OperationStatus.class);
        } catch ( Exception e )
        {
            System.err.println("Server response parsing failed");
            e.printStackTrace();
            return false;
        }

        return answer.equals(OperationStatus.OPERATION_SUCCESS);
    }
}
