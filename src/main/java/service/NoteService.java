package service;

import app.DBInfo;
import models.db_models.Note;

import java.sql.*;

public class NoteService {
    private Connection connection;
    private ResultSet resultSet;

    private final String CREATE_TABLE_SQL = "CREATE TABLE Note (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(2048) CHECK(length(\"comment\")>0 and length(\"comment\")<2049),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";
    private final String SELECT_SQL = "SELECT \"id\", \"name\", \"comment\", \"appuser_id\" FROM Note WHERE \"name\" = ? and \"comment\" = ? and \"appuser_id\" = ? LIMIT 1";
    private final String ADD_SQL = "INSERT INTO Note(\"name\", \"comment\", \"appuser_id\") VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE Note SET \"name\" = ?, \"comment\" = ? WHERE \"id\" = ?";
    private final String DELETE_SQL = "DELETE FROM Note WHERE \"id\" = ?";

    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement SELECT_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public NoteService() throws SQLException {
        DBInfo dbInfo = new DBInfo();
        boolean tableExists = false;

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Note".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
            CREATE_TABLE_PSTM.executeUpdate();
        }
    }

    public boolean add(Note note) {
        try {
            ADD_PSTM = connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1, note.getName());
            ADD_PSTM.setString(2, note.getComment());
            ADD_PSTM.setLong(3, note.getUser_id());
            ADD_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean select(Note note) {
        try {
            SELECT_PSTM = connection.prepareStatement(SELECT_SQL);

            SELECT_PSTM.setString(1, note.getName());
            SELECT_PSTM.setString(2, note.getComment());
            SELECT_PSTM.setLong(3, note.getUser_id());
            SELECT_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Note note, String newName, String newComment) {
        try {
            UPDATE_PSTM = connection.prepareStatement(UPDATE_SQL);

            UPDATE_PSTM.setString(1, newName);
            UPDATE_PSTM.setString(2, newComment);
            UPDATE_PSTM.setLong(3, note.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Note note) {
        try {
            DELETE_PSTM = connection.prepareStatement(DELETE_SQL);

            DELETE_PSTM.setLong(1, note.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
