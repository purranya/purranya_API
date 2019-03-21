package service;

import app.DBInfo;
import models.db_models.Calendar;
import models.db_models.Label;

import java.sql.*;

public class LabelService {
    private Connection connection;
    private ResultSet resultSet;

    private static String CREATE_TABLE_SQL = "CREATE TABLE Label (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"color_r\" smallint CHECK(\"color_r\">=0 and \"color_r\"<256) NOT NULL,\n" +
            "    \"color_b\" smallint CHECK(\"color_b\">=0 and \"color_b\"<256) NOT NULL,\n" +
            "    \"color_g\" smallint CHECK(\"color_g\">=0 and \"color_g\"<256) NOT NULL,\n" +
            "    \"calendar_id\" bigint NOT NULL REFERENCES Calendar(\"id\")\n" +
            ");";
    private static String SELECT_SQL = "SELECT \"id\", \"name\", \"color_r\", \"color_g\", \"color_b\", \"calendar_id\" FROM Label" +
            " WHERE \"name\" = ? and \"color_r\" = ? and \"color_g\" = ? and \"color_b\" = ? and \"calendar_id\" = ?";
    private static String ADD_SQL = "INSERT INTO Label(\"name\", \"color_r\", \"color_g\", \"color_b\", \"calendar_id\") VALUES(?, ?, ?, ?, ?)";
    private static String UPDATE_SQL = "UPDATE Label SET \"name\" = ?, \"color_r\" = ?, \"color_g\" = ?, \"color_b\" = ? WHERE \"id\" = ?";
    private static String DELETE_SQL = "DELETE FROM Label WHERE \"id\" = ?";

    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement SELECT_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public LabelService() throws SQLException {
        DBInfo dbInfo = new DBInfo();
        boolean tableExists = false;

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Label".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
            CREATE_TABLE_PSTM.executeUpdate();
        }
    }

    public boolean select(Calendar calendar, Label label) {
        try {
            SELECT_PSTM = connection.prepareStatement(SELECT_SQL);

            SELECT_PSTM.setString(1, label.getName());
            SELECT_PSTM.setShort(2, label.getColor_r());
            SELECT_PSTM.setShort(3, label.getColor_g());
            SELECT_PSTM.setShort(4, label.getColor_b());
            SELECT_PSTM.setLong(5, calendar.getId());
            SELECT_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Calendar calendar, Label label) {
        try {
            ADD_PSTM = connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1, label.getName());
            ADD_PSTM.setShort(2, label.getColor_r());
            ADD_PSTM.setShort(3, label.getColor_g());
            ADD_PSTM.setShort(4, label.getColor_b());
            ADD_PSTM.setLong(5, calendar.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Label label, String newName, short newColor_r, short newColor_g, short newColor_b) {
        try {
            UPDATE_PSTM = connection.prepareStatement(UPDATE_SQL);

            UPDATE_PSTM.setString(1, newName);
            UPDATE_PSTM.setShort(2, newColor_r);
            UPDATE_PSTM.setShort(3, newColor_g);
            UPDATE_PSTM.setShort(4, newColor_b);
            UPDATE_PSTM.setLong(5, label.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Label label) {
        try {
            DELETE_PSTM = connection.prepareStatement(DELETE_SQL);

            DELETE_PSTM.setLong(1, label.getId());
            DELETE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
