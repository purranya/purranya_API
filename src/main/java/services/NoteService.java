package services;

import app.DBInfo;
import models.db_models.Note;

import java.sql.*;
import java.util.ArrayList;

public class NoteService {
    private Connection connection;
    private ResultSet resultSet;

    private final String SQL_SELECT_BY_ID = "SELECT \"id\", \"name\", \"comment\", \"appuser_id\" FROM Note WHERE \"id\" = ? LIMIT 1";
    private final String SQL_SELECT_USER_ID = "SELECT \"id\", \"name\", \"comment\", \"appuser_id\" FROM Note WHERE \"appuser_id\" = ?";
    private final String SQL_ADD = "INSERT INTO Note(\"name\", \"comment\", \"appuser_id\") VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Note SET \"name\" = ?, \"comment\" = ?, \"appuser_id\" = ? WHERE \"id\" = ?";
    private final String SQL_DELETE = "DELETE FROM Note WHERE \"id\" = ?";

    private PreparedStatement SELECT_BY_ID_PSTM = null;
    private PreparedStatement SELECT_BY_USER_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public NoteService() {
        DBInfo dbInfo = new DBInfo();

        try
        {
            connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public NoteService(Connection connection) {
        this.connection = connection;
    }

    public Note getById(Long id) {
        if(id == null)
            return null;

        try {
            if (SELECT_BY_ID_PSTM == null)
                SELECT_BY_ID_PSTM = connection.prepareStatement(SQL_SELECT_BY_ID);

            SELECT_BY_ID_PSTM.setLong(1,id);
            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();

            if(rs.next())
            {
                Note n = new Note();
                n.setId(rs.getLong(1));
                n.setName(rs.getString(2));
                n.setComment(rs.getString(3));
                n.setUser_id(rs.getLong(4));
                return n;
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Note note) {
        if(note == null)
            return false;

        try {
            if(ADD_PSTM==null)
                ADD_PSTM = connection.prepareStatement(SQL_ADD);

            ADD_PSTM.setString(1, note.getName());
            ADD_PSTM.setString(2, note.getComment());
            ADD_PSTM.setLong(3, note.getUser_id());
            int added = ADD_PSTM.executeUpdate();
            return added>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Note note) {
        if(note == null)
            return false;

        try {
            if(UPDATE_PSTM==null)
                UPDATE_PSTM = connection.prepareStatement(SQL_UPDATE);

            UPDATE_PSTM.setString(1, note.getName());
            UPDATE_PSTM.setString(2, note.getComment());
            UPDATE_PSTM.setLong(3, note.getUser_id());
            UPDATE_PSTM.setLong(4,note.getId());
            int updated = UPDATE_PSTM.executeUpdate();
            return updated>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        if(id == null)
            return false;

        try {
            if(DELETE_PSTM==null)
                DELETE_PSTM = connection.prepareStatement(SQL_DELETE);

            DELETE_PSTM.setLong(1, id);
            int deleted = DELETE_PSTM.executeUpdate();
            return deleted>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Note> getByUser(Long id)
    {
        if(id == null)
            return null;

        try {
            if (SELECT_BY_USER_PSTM == null)
                SELECT_BY_USER_PSTM = connection.prepareStatement(SQL_SELECT_USER_ID);

            SELECT_BY_USER_PSTM.setLong(1,id);
            ResultSet rs = SELECT_BY_USER_PSTM.executeQuery();
            ArrayList<Note> notes = new ArrayList<>();

            while(rs.next())
            {
                Note n = new Note();
                n.setId(rs.getLong(1));
                n.setName(rs.getString(2));
                n.setComment(rs.getString(3));
                n.setUser_id(rs.getLong(4));
                notes.add(n);
            }
            return notes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
