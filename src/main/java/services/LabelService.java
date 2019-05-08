package services;

import app.DBInfo;
import models.db_models.Label;

import java.sql.*;
import java.util.ArrayList;

public class LabelService {
    private Connection connection;

    private static String SQL_SELECT_BY_ID = "SELECT \"id\", \"name\", \"color_r\", \"color_g\", \"color_b\", \"calendar_id\" FROM Label WHERE \"id\"=? LIMIT 1";
    private static String SQL_ADD = "INSERT INTO Label(\"name\", \"color_r\", \"color_g\", \"color_b\", \"calendar_id\") VALUES(?, ?, ?, ?, ?)";
    private static String SQL_UPDATE = "UPDATE Label SET \"name\" = ?, \"color_r\" = ?, \"color_g\" = ?, \"color_b\" = ?, \"calendar_id\" = ? WHERE \"id\" = ?";
    private static String SQL_DELETE = "DELETE FROM Label WHERE \"id\" = ?";
    private static String SQL_SELECT_BY_CALENDAR = "SELECT \"id\", \"name\", \"color_r\", \"color_g\", \"color_b\", \"calendar_id\" FROM Label WHERE \"calendar_id\"=?";

    private PreparedStatement SELECT_BY_ID_PSTM = null;
    private PreparedStatement SELECT_BY_CALENDAR_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public LabelService(){
        DBInfo dbInfo = new DBInfo();

        try
        {
            connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public LabelService(Connection connection) {
        this.connection = connection;
    }

    public Label getById(Long id){
        if(id == null)
            return null;

        try {
            if(SELECT_BY_ID_PSTM==null)
                SELECT_BY_ID_PSTM = connection.prepareStatement(SQL_SELECT_BY_ID);

            SELECT_BY_ID_PSTM.setLong(1, id);
            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();

            if(rs.next())
            {
                Label l = new Label();
                l.setId(rs.getLong(1));
                l.setName(rs.getString(2));
                l.setColor_r(rs.getInt(3));
                l.setColor_g(rs.getInt(4));
                l.setColor_b(rs.getInt(5));
                l.setCalendar_id(rs.getLong(6));
                return l;
            }
            else
                return null;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Label label) {
        if(label ==  null)
            return false;

        try {
            if(ADD_PSTM==null)
            ADD_PSTM = connection.prepareStatement(SQL_ADD);

            ADD_PSTM.setString(1, label.getName());
            ADD_PSTM.setInt(2, label.getColor_r());
            ADD_PSTM.setInt(3, label.getColor_g());
            ADD_PSTM.setInt(4, label.getColor_b());
            ADD_PSTM.setLong(5, label.getCalendar_id());
            int added = ADD_PSTM.executeUpdate();
            return added>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Label label) {
        if(label == null)
            return false;

        try {
            if(UPDATE_PSTM==null)
                UPDATE_PSTM = connection.prepareStatement(SQL_UPDATE);

            UPDATE_PSTM.setString(1, label.getName());
            UPDATE_PSTM.setInt(2, label.getColor_r());
            UPDATE_PSTM.setInt(3, label.getColor_g());
            UPDATE_PSTM.setInt(4, label.getColor_b());
            UPDATE_PSTM.setLong(5,label.getCalendar_id());
            UPDATE_PSTM.setLong(6, label.getId());
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

    public ArrayList<Label> getByCalendar(Long id)
    {
        if(id == null)
            return null;

        try {
            if(SELECT_BY_CALENDAR_PSTM==null)
                SELECT_BY_CALENDAR_PSTM = connection.prepareStatement(SQL_SELECT_BY_CALENDAR);

            SELECT_BY_CALENDAR_PSTM.setLong(1, id);
            ResultSet rs = SELECT_BY_CALENDAR_PSTM.executeQuery();
            ArrayList<Label> labels = new ArrayList<>();
            while(rs.next())
            {
                Label l = new Label();
                l.setId(rs.getLong(1));
                l.setName(rs.getString(2));
                l.setColor_r(rs.getInt(3));
                l.setColor_g(rs.getInt(4));
                l.setColor_b(rs.getInt(5));
                l.setCalendar_id(rs.getLong(6));
                labels.add(l);
            }
            return labels;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
