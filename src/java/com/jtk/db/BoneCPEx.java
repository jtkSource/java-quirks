package com.jtk.db;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BoneCPEx {

    private static Logger logger = LoggerFactory.getLogger(BoneCPEx.class);

    public static void main(String[] args) {

        BoneCPDataSource boneCPDataSource = BoneCPFactory.invoke();

        try (Connection connection = boneCPDataSource.getConnection()) {

            metaData(connection);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            logger.info("Transaction Isolation Level {} ", getTransactionIsolation(connection.getTransactionIsolation()));

            String sql = "INSERT INTO `jtk`.`Contacts` " +
                    "(`FirstName`,`LastName`,`Nickname`,`Number`,`e-mail`,`Street Address`) VALUES (?,?,?,?,?,?);";
            PreparedStatement queryStatement = connection
                    .prepareStatement(sql,
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE,
                            ResultSet.HOLD_CURSORS_OVER_COMMIT);
            insertContacts(queryStatement, "Jubin", "Kuriakose", "jtk", "+65 846864607",
                    "jubintk@gmail.com", "Mars, Milkyway");
            connection.setSavepoint("saveInsert");
            insertContacts(queryStatement, "Feba", "James", "fj", "+65 91804961",
                    "feba.james@ymail.com", "Venus, Milkyway");
            Savepoint beforeLastInsert = connection.setSavepoint("beforeLastInsert");
            insertContacts(queryStatement, "Ben", "Thomas", "ben", "+65 2333344",
                    "ben.thomas@ymail.com", "Mars, Milkyway");

            Statement stmt = connection
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE,
                            ResultSet.HOLD_CURSORS_OVER_COMMIT);
            logger.info("result set holdability {}", stmt.getResultSetHoldability());
            logger.info("result set scroll sensitivity {}", stmt.getResultSetType());
            logger.info("result set concur {}", stmt.getResultSetConcurrency());
            ResultSet contactsRs = stmt.executeQuery("select `FirstName`,`LastName`,`Nickname`,`Number`,`e-mail`,`Street Address` from Contacts");
            readContacts(contactsRs);
            logger.info("rollback to beforeSecondInsert and commit...");
            connection.rollback(beforeLastInsert);
            connection.commit();
            logger.info("===================================================================================");
            // result set needs to be queried again after the commit to reflect the changes to the result set.
            contactsRs = stmt.executeQuery("select `FirstName`,`LastName`,`Nickname`,`Number`,`e-mail`,`Street Address` from Contacts");
            readContacts(contactsRs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void metaData(Connection connection) throws SQLException {
        DatabaseMetaData mysqlMetaData = connection.getMetaData();
        logger.info("Database: {}:{}", mysqlMetaData.getDatabaseProductName(), mysqlMetaData.getDatabaseProductVersion());
        logger.info("Database version: {}.{}", mysqlMetaData.getDatabaseMajorVersion(), mysqlMetaData.getDatabaseMinorVersion());
        logger.info("Driver version: {}", mysqlMetaData.getDriverVersion());
        logger.info("Default transaction isolation level {}", getTransactionIsolation(mysqlMetaData.getDefaultTransactionIsolation()));
        logger.info(" Support  TRANSACTION_SERIALIZABLE {} ", mysqlMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE));
        connection.setAutoCommit(false);

    }

    private static void insertContacts(PreparedStatement queryStatement, String firstName, String lastName, String nickname,
                                       String number, String email, String address) throws SQLException {
        queryStatement.setString(1, firstName);

        queryStatement.setString(2, lastName);

        queryStatement.setString(3, nickname);

        queryStatement.setString(4, number);

        queryStatement.setString(5, email);

        queryStatement.setString(6, address);

        logger.info("Insert {} row ", queryStatement.executeUpdate());

    }

    private static void readContacts(ResultSet contactsRs) throws SQLException {
        if (!contactsRs.isBeforeFirst())
            contactsRs.beforeFirst();
        while (contactsRs.next()) {
            logger.info("Details : {} | {} | {}",
                    contactsRs.getString("FirstName"),
                    contactsRs.getString("Number"),
                    contactsRs.getString("Street Address"));
        }
    }

    public static String getTransactionIsolation(int i) {
        if (i == Connection.TRANSACTION_NONE)
            return "TRANSACTION_NONE";
        else if (i == Connection.TRANSACTION_READ_COMMITTED)
            return "TRANSACTION_READ_COMMITTED";
        else if (i == Connection.TRANSACTION_READ_UNCOMMITTED)
            return "TRANSACTION_READ_UNCOMMITTED";
        else if (i == Connection.TRANSACTION_REPEATABLE_READ)
            return "TRANSACTION_REPEATABLE_READ";
        else if (i == Connection.TRANSACTION_SERIALIZABLE)
            return "TRANSACTION_SERIALIZABLE";
        return "";
    }

    private static class BoneCPFactory {
        private static BoneCPDataSource invoke() {
            BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
            //boneCPDataSource.setDriverClass("com.mysql.cj.jdbc.Driver"); // unnecessary
            boneCPDataSource.setJdbcUrl("jdbc:mysql://192.168.56.102:3306/jtk");
            boneCPDataSource.setUsername("jubin");
            boneCPDataSource.setPassword("jubin@123");
            boneCPDataSource.setMinConnectionsPerPartition(5);
            boneCPDataSource.setMaxConnectionsPerPartition(10);
            boneCPDataSource.setPartitionCount(2);
            boneCPDataSource.setPoolName("boner");
            return boneCPDataSource;
        }
    }
}
