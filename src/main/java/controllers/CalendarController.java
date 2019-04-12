package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.db_models.Calendar;
import models.db_models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CalendarService;
import service.UserService;

@Component
@RestController
public class CalendarController
{
    UserService userService;
    CalendarService calendarService;

    @RequestMapping(value = "/calendar/getindex",method = RequestMethod.POST)
    public String getCalendarIndex(@RequestParam("json") String params)
    {
        ObjectMapper om = new ObjectMapper();

        return params;
    }
}
