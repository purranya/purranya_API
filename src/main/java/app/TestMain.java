package app;

import api_client.HTTPSClient;
import api_client.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.transfer_models.LoginStatus;
import models.transfer_models.Presence;

import java.util.HashMap;

public class TestMain
{
    public static void main(String[] args)
    {
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
                System.out.println("Zalogowano!");
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
    }
}
