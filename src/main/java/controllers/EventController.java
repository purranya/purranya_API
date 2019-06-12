package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
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
public class EventController {
    private UserService userService;
    private LabelService labelService;
    private CalendarService calendarService;
    private EventService eventService;

    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public String addCalendar(@RequestParam("login") String login, @RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if (eventService == null)
            eventService = new EventService();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        Login loginOM = objectMapper.readValue(login, Login.class);
        Event eventOM = objectMapper.readValue(model, Event.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(eventOM.getCalendar_id());
        Label label = labelService.getById(eventOM.getLabel_id());

        if (user != null && calendar != null && label != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (eventService.add(eventOM))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/event/edit",method = RequestMethod.POST)
    public String editEvent(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if (eventService == null)
            eventService = new EventService();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        Login loginOM = objectMapper.readValue(login, Login.class);
        Event eventOM = objectMapper.readValue(model, Event.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(eventOM.getCalendar_id());
        Label label = labelService.getById(eventOM.getLabel_id());

        if (user != null && calendar != null && label != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (eventService.update(eventOM))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/event/delete",method = RequestMethod.POST)
    public String deleteEvent(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception {
        if (userService == null)
            userService = new UserService();
        if (calendarService == null)
            calendarService = new CalendarService();
        if (labelService == null)
            labelService = new LabelService();
        if (eventService == null)
            eventService = new EventService();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        Login loginOM = objectMapper.readValue(login, Login.class);
        Event eventOM = objectMapper.readValue(model, Event.class);

        User user = userService.getByLogin(
                loginOM.getUsername(), PasswordUtils.sha256(loginOM.getPassword()));
        Calendar calendar = calendarService.getById(eventOM.getCalendar_id());
        Label label = labelService.getById(eventOM.getLabel_id());

        if (user != null && calendar != null && label != null &&
                calendar.getUser_id().equals(user.getId())) {
            if (eventService.delete(eventOM.getId()))
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
        } else
            return objectMapper.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }
}
