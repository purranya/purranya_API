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

import java.io.IOException;
import java.util.ArrayList;

@Component
@RestController
public class NoteController
{
    UserService userService;
    NoteService noteService;

    @RequestMapping(value = "/note/getindex",method = RequestMethod.POST)
    public String getNoteIndex(@RequestParam("login") String login) throws IOException
    {
        if(noteService == null)
            noteService = new NoteService();

        ObjectMapper om = new ObjectMapper();
        String answer;
        if(userService==null)
            userService = new UserService();

        Login l = null;

        l = om.readValue(login,Login.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u==null)
        {
            UserNoteIndex index = new UserNoteIndex(new ArrayList<>());
            answer = om.writeValueAsString(index);
        }
        else
        {
            ArrayList<Note> notes = noteService.getByUser(u.getId());
            UserNoteIndex index = new UserNoteIndex(notes);
            answer = om.writeValueAsString(index);
        }

        return answer;
    }

    @RequestMapping(value = "/note/add",method = RequestMethod.POST)
    public String addNote(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(noteService==null)
            noteService = new NoteService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);
        Note n = om.readValue(model,Note.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            n.setUser_id(u.getId());
            if(noteService.add(n))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/note/edit",method = RequestMethod.POST)
    public String editNote(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(noteService==null)
            noteService = new NoteService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);
        Note n = om.readValue(model,Note.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            Note note = noteService.getById(n.getId());
            if(note==null)
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!note.getUser_id().equals(u.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!note.getUser_id().equals(n.getUser_id()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(noteService.update(n))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

    @RequestMapping(value = "/note/delete",method = RequestMethod.POST)
    public String deleteNote(@RequestParam("login") String login,@RequestParam("model") String model) throws Exception
    {
        if(userService==null)
            userService = new UserService();
        if(noteService==null)
            noteService = new NoteService();

        ObjectMapper om = new ObjectMapper();
        Login l = om.readValue(login, Login.class);
        Note n = om.readValue(model,Note.class);

        User u = userService.getByLogin(l.getUsername(), PasswordUtils.sha256(l.getPassword()));

        if(u!=null)
        {
            Note note = noteService.getById(n.getId());
            if(note==null)
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!note.getUser_id().equals(u.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(!note.getUser_id().equals(n.getUser_id()))
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);

            if(noteService.delete(note.getId()))
                return om.writeValueAsString(OperationStatus.OPERATION_SUCCESS);
            else
                return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
        }
        else
            return om.writeValueAsString(OperationStatus.OPERATION_FAILED);
    }

}
