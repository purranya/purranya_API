package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.*;
import models.transfer_models.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.*;
import utils.PasswordUtils;

@Component
@RestController
public class UserController {
    UserService userService;

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("login") String login, @RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);

        User user = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if (user != null) {
            if (userService.add(user))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam("login") String login, @RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);

        User user = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if (user != null) {
            if (userService.update(user))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("login") String login, @RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);

        User user = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if (user != null) {
            if (userService.delete(user.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }
}
