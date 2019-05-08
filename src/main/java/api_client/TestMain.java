package api_client;

import models.db_models.Calendar;
import models.transfer_models.UserCalendarIndex;

public class TestMain
{
    public static void main(String[] args)
    {
        /*
        System.out.println("Test Login");
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
                System.out.println("Zalogowano!");
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
        */

        /*
        System.out.println("Test Rejestracja");
        if(Server.isPresent())
            if(Server.register("kamur","kamil1234"))
                System.out.println("Zarejestrowano!");
            else
                System.out.println("Niezarejestrowano");
        else
            System.out.println("Brak połączenia z serwerem");
        */

        /*
        System.out.println("Test index");
        if(Server.isPresent())
            if(Server.logIn("secondUser","uzytkownik123"))
            {
                System.out.println("Zalogowano\nIndex:");
                UserCalendarIndex index = Server.getCalendarIndex("secondUser","uzytkownik123");
                if(index.getCalendars().size()==0)
                    System.out.println("Brak kalendarzy");
                else
                    for(Calendar c : index.getCalendars())
                    {
                        System.out.printf("%s [%s]\n",c.getName(),c.getComment());
                    }
            }
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
            */

        /*
        System.out.println("Test add");
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
            {
                System.out.println(Server.addCalendar("kamur","kamil1234",new Calendar(0L,"Kalendarz2","xdddd")));
            }
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
            {
                System.out.println("Zalogowano\nIndex:");
                UserCalendarIndex index = Server.getCalendarIndex("kamur","kamil1234");
                if(index.getCalendars().size()==0)
                    System.out.println("Brak kalendarzy");
                else
                    for(Calendar c : index.getCalendars())
                    {
                        System.out.printf("%s [%s]\n",c.getName(),c.getComment());
                    }
            }
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");
            */

    }
}
