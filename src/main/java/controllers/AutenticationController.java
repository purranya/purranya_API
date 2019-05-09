package controllers;

import utils.PasswordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.User;
import models.transfer_models.Login;
import models.transfer_models.OperationStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import services.UserService;

@Component
@RestController
public class AutenticationController
{
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("login") String login)
    {
        if(userService==null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = null;

        try{
            l = om.readValue(login,Login.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        try
        {
            if (u != null)
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } catch ( Exception e )
        {
            e.printStackTrace();
            return "OPERATION_FAILED";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("login") String login)
    {
        if(userService==null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = null;

        try{
            l = om.readValue(login,Login.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        User u = new User(null,l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        try
        {
            boolean added = userService.add(u);
            if(added)
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } catch ( Exception e )
        {
            try
            {
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
            } catch ( Exception e2 )
            {
                e.printStackTrace();
            }
        }

        return "OPERATION_FAILED";
    }
}
