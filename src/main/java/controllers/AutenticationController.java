package controllers;

import utils.PasswordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.User;
import models.transfer_models.Login;
import models.transfer_models.LoginStatus;
import models.transfer_models.RegisterStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Component
@RestController
public class AutenticationController
{
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("json") String params)
    {
        if(userService==null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = null;

        try{
            l = om.readValue(params,Login.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        User u = userService.getUser(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        try
        {
            if (u != null)
                return om.writeValueAsString(LoginStatus.LOGIN_SUCCESS);
            else
                return om.writeValueAsString(LoginStatus.LOGIN_FAILED);
        } catch ( Exception e )
        {
            e.printStackTrace();
            return "LOGIN_ERROR";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("json") String params)
    {
        if(userService==null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = null;

        try{
            l = om.readValue(params,Login.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        User u = new User(null,l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        try
        {
            boolean added = userService.addUser(u);
            if(added)
                return om.writeValueAsString(RegisterStatus.REGISTER_SUCCESS);
            else
                return om.writeValueAsString(RegisterStatus.REGISTER_FAILED);
        } catch ( Exception e )
        {
            try
            {
                return om.writeValueAsString(RegisterStatus.REGISTER_FAILED);
            } catch ( Exception e2 )
            {
                e.printStackTrace();
            }
        }

        return "REGISTER_FAILED";
    }
}
