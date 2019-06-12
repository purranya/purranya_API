package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.db_models.Event;
import models.db_models.Label;
import models.db_models.User;
import models.transfer_models.Login;
import models.transfer_models.OperationStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.CalendarService;
import services.EventService;
import services.LabelService;
import services.UserService;
import utils.PasswordUtils;

@Component
@RestController
public class LabelController {
    private LabelService labelService;
    private CalendarService calendarService;
    private UserService userService;

    @RequestMapping(value = "/label/add", method = RequestMethod.POST)
    public String addLabel(@RequestParam("login") String login, @RequestParam("model") String model) throws Exception {
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if(userService == null)
            userService = new UserService();
        ObjectMapper objectMapper = new ObjectMapper();
        Login loginOM = objectMapper.readValue(login, Login.class);
        Label labelOM = objectMapper.readValue(model, Label.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(labelOM.getCalendar_id());

        if (user != null && calendar != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (labelService.add(labelOM))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/label/edit",method = RequestMethod.POST)
    public String editLabel(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception {
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if(userService == null)
            userService = new UserService();
        ObjectMapper objectMapper = new ObjectMapper();
        Login loginOM = objectMapper.readValue(login, Login.class);
        Label labelOM = objectMapper.readValue(model, Label.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(labelOM.getCalendar_id());

        if (user != null && calendar != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (labelService.update(labelOM))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/label/delete",method = RequestMethod.POST)
    public String deleteLabel(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception {
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if(userService == null)
            userService = new UserService();
        ObjectMapper objectMapper = new ObjectMapper();
        Login loginOM = objectMapper.readValue(login, Login.class);
        Label labelOM = objectMapper.readValue(model, Label.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(labelOM.getCalendar_id());

        if (user != null && calendar != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (labelService.delete(labelOM.getId()))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }
}
