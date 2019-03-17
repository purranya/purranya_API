package controllers;

import Utils.PasswordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.User;
import models.transfer_models.LoginStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Component
@RestController
public class AutenticationController
{
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        if(userService==null)
            userService = new UserService();

        System.out.println(String.format("logowanie %s/%s",username,password));

        User u = userService.getUser(username, PasswordUtils.sha256(password));

        ObjectMapper om = new ObjectMapper();
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
}
