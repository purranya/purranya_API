package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.db_models.User;
import models.transfer_models.AddCalendar;
import models.transfer_models.INSERT_STATUS;
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
    public String getCalendarIndex(@RequestParam("json") String params) throws IOException
    {
        if(calendarService == null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        String answer;
        if(userService==null)
            userService = new UserService();

        Login login = null;

        login = om.readValue(params,Login.class);

        User u = userService.getByLogin(login.getUsername(), PasswordUtils.sha256(login.getPassword()));

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
    public String addCalendar(@RequestParam("json") String params) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        AddCalendar ac = om.readValue(params, AddCalendar.class);

        User u = userService.getByLogin(ac.getLogin().getUsername(), PasswordUtils.sha256(ac.getLogin().getPassword()));

        if(u!=null)
        {
            ac.getCalendar().setUser_id(u.getId());
            if(calendarService.add(ac.getCalendar()))
                return om.writeValueAsString(INSERT_STATUS.INSERT_OK);
            else
                return om.writeValueAsString(INSERT_STATUS.INSERT_ERROR);
        }
        else
            return om.writeValueAsString(INSERT_STATUS.INSERT_ERROR);
    }
}
