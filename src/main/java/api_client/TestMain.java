package api_client;

public class TestMain
{
    public static void main(String[] args)
    {
        System.out.println("Test Login");
        if(Server.isPresent())
            if(Server.logIn("kamur","kamil1234"))
                System.out.println("Zalogowano!");
            else
                System.out.println("Niezalogowano");
        else
            System.out.println("Brak połączenia z serwerem");


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
    }
}
