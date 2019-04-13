package api_client;

import models.db_models.Calendar;
import models.transfer_models.UserCalendarIndex;

public class TestMain
{
    public static void main(String[] args)
    {
        /*
        System.out.println("Test Rejestracja");
        if(Server.isPresent())
            if(Server.register("kamur","kamil1234"))
                System.out.println("Zarejestrowano!");
            else
                System.out.println("Niezarejestrowano");
        else
            System.out.println("Brak połączenia z serwerem");

        System.out.println("Test Login");
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
                System.out.println("Zalogowano!");
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
        */
        System.out.println("Test index");
        UserCalendarIndex index = Server.getCalendarIndex("secondUser","uzytkownik123");
        System.out.println("---Kalendarze---");
        if(index!=null)
            for(Calendar c : index.getCalendars())
                System.out.println(c.getName());
        System.out.println("----------------");
    }
}
