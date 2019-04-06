package modell;

import controller.enums.NotificationTypeEnum;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import controller.logic.AdHocEvent;
import controller.logic.Event;
import controller.logic.RegularEvent;
import controller.strategy.Minus1dayStrategy;
import controller.strategy.Minus7DayStrategy;
import controller.strategy.NotificationStrategy;

public class DerbyDB {

    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:derbyDB;create=true";
    final String USERNAME = "";
    final String PASSW = "";
    private Connection connection = null;
    private Statement statement = null;
    private DatabaseMetaData metaData = null;
    private ResultSet resultset;
    private PreparedStatement prepareStatement = null;
    private static DerbyDB INSTANCE;

    public static DerbyDB getInstance() throws SQLException {
        if (INSTANCE == null) {
            try {
                INSTANCE = new DerbyDB();
            } catch (SQLException ex) {
                throw new SQLException(ex);
            }
            return INSTANCE;
        }

        return INSTANCE;
    }

    private DerbyDB() throws SQLException {
        openDerby();

        try {
            resultset = metaData.getTables(null, "APP", "ESEMENYEK", null);
            if (!resultset.next()) {
                statement.execute("create table ESEMENYEK(EV INTEGER,HONAP VARCHAR(20),NAP INTEGER,"
                        + "JELZESKOD INTEGER,DATUMKOD INTEGER,SZOVEG VARCHAR(101) NOT NULL DEFAULT 'QQQ' , STRATEGY VARCHAR(20)"
                        + ",ESEMENYTIPUS VARCHAR(20),PRIMARY KEY(SZOVEG))");
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
        closeDerby();

    }

    public void autoRemoveExperiedEvent(int actualYear, int actualDateCode) throws SQLException {
        openDerby();
        try {

            String sqlCommand = "delete from ESEMENYEK WHERE EV<=" + actualYear + " and EV>0 and DATUMKOD<" + actualDateCode;
            System.out.println(sqlCommand);
            statement.execute(sqlCommand);
        } catch (SQLException ex) {
            throw new SQLException();
        }
        closeDerby();

    }

    boolean saveToDataBase(Event e) throws SQLException {

        String esemTipusString = null;
        String sqlCommand = null;
        int year = e.getYear();

        try {

            openDerby();
            if (e instanceof AdHocEvent) {
                esemTipusString = ((AdHocEvent) e).getNotifyType();

            } else {
                esemTipusString = ((RegularEvent) e).getEsemenyTipusString();
                year = ((RegularEvent) e).getYear();
            }
//            sqlCommand = "insert into ESEMENYEK values (" + year + "," + "'" + esemeny.getHonap()
//                    + "'" + "," + esemeny.getNap() + "," + esemeny.getJelzesKod() + "," + esemeny.getDatumKod() + ","
//                    + "'" + esemeny.getEsemenyText() + "'" + "," + "'" + esemeny.getJlzTipusStrategy().getJELZESTIPUS()
//                    + "'" + "," + "'" + esemTipusString + "'" + ")";
//            System.out.println(sqlCommand);
            sqlCommand = "insert into ESEMENYEK values(?,?,?,?,?,?,?,?)";
            if (connection != null) {
                prepareStatement = connection.prepareStatement(sqlCommand);
                System.out.println("prepareStatement is ok!");
                prepareStatement.setInt(1, year);
                prepareStatement.setString(2, e.getMonth());
                prepareStatement.setInt(3, e.getDay());
                prepareStatement.setInt(4, e.getNotificationCode());
                prepareStatement.setInt(5, e.getDateCode());
                prepareStatement.setString(6, e.getNotiText());
                prepareStatement.setString(7, e.getNotifyTypeStrategy().getNotifyType());
                prepareStatement.setString(8, esemTipusString);
                prepareStatement.execute();
            }

            return true;
        } catch (SQLException ex) {

            throw new SQLException("Ugyanolyan esemeny kétszer nem szerepelhet");

        } finally {
            closeDerby();
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            System.out.println("prepareStatement close");
        }

    }

    boolean deleteEventFromDatabase(String delText) throws SQLException {

        String sqlCommand = "delete from ESEMENYEK where SZOVEG=" + "'" + delText + "'";

        try {
            openDerby();

            statement.execute(sqlCommand);

            closeDerby();
            return true;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    List<Event> dbToEventList() throws SQLException {
        NotificationStrategy js = null;

        ResultSet executeQuery = null;
        String sqlCommand = "SELECT * FROM ESEMENYEK FETCH FIRST 100 ROWS ONLY";
        List<Event> resultList = new ArrayList<>();

        try {
            openDerby();
            executeQuery = statement.executeQuery(sqlCommand);

            while (executeQuery.next()) {
                int tmpYear = executeQuery.getInt(1);

                int tmpDay = executeQuery.getInt(3);

                int tmpDateCode = executeQuery.getInt(5);

                String tmpText = executeQuery.getString(6);

                String tmpStrategy = executeQuery.getString(7);
                String tmpEventType = executeQuery.getString(8);

                js = (tmpStrategy.equals(Minus1dayStrategy.getNotifyStrategy())) ? new Minus1dayStrategy() : new Minus7DayStrategy();

                if (tmpEventType.equals(NotificationTypeEnum.getREGULAR())) {
                    resultList.add(new RegularEvent(tmpYear, createMonthCode(tmpDateCode, tmpDay), tmpDay, js, tmpText));
                } else {
                    resultList.add(new AdHocEvent(tmpYear, createMonthCode(tmpDateCode, tmpDay), tmpDay, js, tmpText));
                }

            }
            closeDerby();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return resultList;
    }

    private int createMonthCode(int tmpDateCode, int tnpDay) {
        return (tmpDateCode - tnpDay) / 100;
    }

    private void openDerby() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("connection ok!");
        } catch (SQLException ex) {
            throw new SQLException();
        }

        if (connection != null) {
            try {
                statement = connection.createStatement();
                System.out.println("statement ok!!");
            } catch (SQLException ex) {
                throw new SQLException();
            }
        }

        try {
            if (connection != null) {
                metaData = connection.getMetaData();
            }
            System.out.println("metadata ok");
        } catch (SQLException ex) {
            throw new SQLException();
        }

    }

    private void closeDerby() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
            }
            System.out.println("connection close");
            if (statement != null) {
                statement.close();
            }
            System.out.println("statement close");

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

}
