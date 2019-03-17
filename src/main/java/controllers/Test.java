package controllers;

import models.transfer_models.Presence;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class Test
{
    @RequestMapping("/")
    public String index() {
        return "tested";
    }

    @RequestMapping("/presense")
    public Presence presence()
    {
        return new Presence();
    }

    @RequestMapping("/string")
    public String string(String n)
    {
        if(n.equals(""))
            return "<h1>Obiekt: null</h1>";
        else
            return "<h1>Obiekt: " + n + "!</h1>";
    }
    public String httpTest()
    {
        return "Success";
    }
}
