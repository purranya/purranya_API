package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.db_models.User;
import models.transfer_models.OperationStatus;
import models.transfer_models.Login;
import models.transfer_models.UserCalendarIndex;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.CalendarService;
import services.UserService;
import utils.PasswordUtils;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RestController
public class CalendarController
{
    UserService userService;
    CalendarService calendarService;

    @RequestMapping(value = "/calendar/getindex",method = RequestMethod.POST)
    public String getCalendarIndex(@RequestParam("login") String login) throws IOException
    {
        if(calendarService == null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        String answer;
        if(userService==null)
            userService = new UserService();

        Login l = null;

        l = om.readValue(login,Login.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u==null)
        {
            UserCalendarIndex index = new UserCalendarIndex(new ArrayList<>());
            answer = om.writeValueAsString(index);
        }
        else
        {
            ArrayList<Calendar> calendars = calendarService.getByUserId(u.getId());
            UserCalendarIndex index = new UserCalendarIndex(calendars);
            answer = om.writeValueAsString(index);
        }

        return answer;
    }

    @RequestMapping(value = "/calendar/add",method = RequestMethod.POST)
    public String addCalendar(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);
        Calendar c = om.readValue(model,Calendar.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            c.setUser_id(u.getId());
            if(calendarService.add(c))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }
}
