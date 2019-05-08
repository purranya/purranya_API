package controllers;

import Utils.PasswordUtils;
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
import service.CalendarService;
import service.UserService;

import java.util.ArrayList;

@Component
@RestController
public class CallendarController
{
    UserService userService;
    CalendarService calendarService;

    @RequestMapping(value = "/calendar/getindex",method = RequestMethod.POST)
    public String getCalendarIndex(@RequestParam("json") String params) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(params, Login.class);
        System.out.println(l);

        User u = userService.getUser(l.getUsername(), PasswordUtils.sha256(l.getPassword()));
        ArrayList<Calendar> calendarArrayList = new ArrayList<>();
        System.out.println(u);

        if(u!=null)
        {
            calendarArrayList = calendarService.getByUser(u);
        }

        UserCalendarIndex index = new UserCalendarIndex(calendarArrayList);
        return om.writeValueAsString(index);
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

        User u = userService.getUser(ac.getLogin().getUsername(), PasswordUtils.sha256(ac.getLogin().getPassword()));

        if(u!=null)
        {
            if(calendarService.add(ac.getCalendar(), u))
                return om.writeValueAsString(INSERT_STATUS.INSERT_OK);
            else
                return om.writeValueAsString(INSERT_STATUS.INSERT_ERROR);
        }
        else
            return om.writeValueAsString(INSERT_STATUS.INSERT_ERROR);


    }
}
