package service;

import app.DBInfo;

import java.sql.*;

public class EventService {
    private Connection connection;
    private ResultSet resultSet;

    private static String CREATE_TABLE_SQL = "CREATE TABLE Event (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"start_date\" timestamp NOT NULL,\n" +
            "    \"end_date\" timestamp NOT NULL,\n" +
            "    \"calendar_id\" bigint NOT NULL REFERENCES Calendar(\"id\"),\n" +
            "    \"label_id\" bigint REFERENCES Label(\"id\")\n" +
            ");";
    private static String SELECT_SQL = "";
    private static String ADD_SQL = "";
    private static String UPDATE_SQL = "";
    private static String DELETE_SQL = "";

    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement SELECT_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public EventService() throws SQLException {
        DBInfo dbInfo = new DBInfo();
        boolean tableExists = false;

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Event".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
            CREATE_TABLE_PSTM.executeUpdate();
        }
    }


}
