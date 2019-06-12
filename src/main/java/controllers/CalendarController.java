package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import models.db_models.*;
import models.transfer_models.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.*;
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
        om.registerModule(new JodaModule());
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
        om.registerModule(new JodaModule());
        Login l = om.readValue(login, Login.class);
        Calendar c = om.readValue(model,Calendar.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            c.setUser_id(u.getId());
            if(calendarService.add(c))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/calendar/edit",method = RequestMethod.POST)
    public String editCalendar(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JodaModule());
        Login l = om.readValue(login, Login.class);
        Calendar c = om.readValue(model,Calendar.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            Calendar calendar = calendarService.getById(c.getId());
            if(calendar==null)
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!calendar.getUser_id().equals(u.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!calendar.getUser_id().equals(c.getUser_id()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(calendarService.update(c))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/calendar/delete",method = RequestMethod.POST)
    public String deleteCalendar(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JodaModule());
        Login l = om.readValue(login, Login.class);
        Calendar c = om.readValue(model,Calendar.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            Calendar calendar = calendarService.getById(c.getId());
            if(calendar==null)
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!calendar.getUser_id().equals(u.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!calendar.getUser_id().equals(c.getUser_id()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(calendarService.delete(calendar.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/calendar/get",method = RequestMethod.POST)
    public String getCalendar(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(calendarService==null)
            calendarService = new CalendarService();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JodaModule());
        Login l = om.readValue(login, Login.class);
        Calendar c = om.readValue(model,Calendar.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            Calendar calendar = calendarService.getById(c.getId());
            if(calendar==null)
                return om.writeValueAsString(null);

            if(!calendar.getUser_id().equals(u.getId()))
                return om.writeValueAsString(null);

            if(!calendar.getUser_id().equals(c.getUser_id()))
                return om.writeValueAsString(null);

            EventService eventService = new EventService();
            LabelService labelService = new LabelService();

            ArrayList<Event> events = eventService.getByCalendar(calendar.getId());
            ArrayList<Label> labels = labelService.getByCalendar(calendar.getId());

            UserCalendar us = new UserCalendar(calendar,labels,events);

            return om.writeValueAsString(us);
        }
        else
            return om.writeValueAsString(null);
    }
}
