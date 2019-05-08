package controllers;

import utils.PasswordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.User;
import models.transfer_models.Login;
import models.status_models.LOGIN_STATUS;
import models.status_models.REGISTRE_STATUS;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import services.UserService;

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

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        try
        {
            if (u != null)
                return om.writeValueAsString(LOGIN_STATUS.LOGIN_SUCCESS);
            else
                return om.writeValueAsString(LOGIN_STATUS.LOGIN_FAILED);
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
            boolean added = userService.add(u);
            if(added)
                return om.writeValueAsString(REGISTRE_STATUS.REGISTER_SUCCESS);
            else
                return om.writeValueAsString(REGISTRE_STATUS.REGISTER_FAILED);
        } catch ( Exception e )
        {
            try
            {
                return om.writeValueAsString(REGISTRE_STATUS.REGISTER_FAILED);
            } catch ( Exception e2 )
            {
                e.printStackTrace();
            }
        }

        return "REGISTER_FAILED";
    }
}
