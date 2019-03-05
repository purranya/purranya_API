package controllers;

import models.Note;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test
{
    public String httpObjectPass(Note n)
    {
        if(n==null)
            return "Obiekt: null";
        else
            return "Obiekt: " + n.getId() + ", " + n.getName() + ", " + n.getComment() + "!";
    }
    public String httpTest()
    {
        return "Success";
    }
}
