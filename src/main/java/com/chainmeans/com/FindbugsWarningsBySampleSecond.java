package com.chainmeans.com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindbugsWarningsBySampleSecond {
    public static void main(final String[] args) {
    	 
        // Prepare database
        Statement createStatement = null;
        Connection connection = null;
        try {
            System.out.println("Findbugs Sample prepare small in memory database");
            connection = getConnection_dmiConstantDbPasswordCORRECT();
            createStatement = connection.createStatement();
            createStatement.execute("create table T_ADVICE (answer varchar(255), "
                    + "owner varchar(255))");
            createStatement.execute("insert into T_ADVICE ( answer, owner ) values "
                    + "('Don''t Panic', 'Joe')");
            createStatement.execute("insert into T_ADVICE ( answer, owner ) values "
                    + "('Keep Smiling', 'John')");
 
            System.out.println("\nFindbugs Sample for DMI_CONSTANT_DB_PASSWORD");
            // WRONG
            FindbugsWarningsBySampleSecond.getConnection_dmiConstantDbPasswordWRONG();
            // CORRECT
            FindbugsWarningsBySampleSecond.getConnection_dmiConstantDbPasswordCORRECT();
 
            System.out.println("\nFindbugs Sample for DMI_EMPTY_DB_PASSWORD");
            // WRONG
            FindbugsWarningsBySampleSecond.getConnection_dmiEmptyDbPasswordWRONG();
            // CORRECT
            FindbugsWarningsBySampleSecond.getConnection_dmiConstantDbPasswordCORRECT();
 
            System.out.println("\nFindbugs Sample for SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE");
            // WRONG
            FindbugsWarningsBySampleSecond.sqlNonconstantStringPassedToExecuteWRONG("Joe");
            // CORRECT
            FindbugsWarningsBySampleSecond.sqlNonconstantStringPassedToExecuteCORRECT("Joe");
 
            System.out.println("\nFindbugs Sample for OBL_UNSATISFIED_OBLIGATION");
            // WRONG
            FindbugsWarningsBySampleSecond.oblUnsatisfiedObligationWRONG("Joe");
            // CORRECT
            FindbugsWarningsBySampleSecond.oblUnsatisfiedObligationCORRECT("Joe");
 
            System.out.println("\nFindbugs Sample for OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE");
            // WRONG
            FindbugsWarningsBySampleSecond.oblUnsatisfiedObligationExceptionEdgeWRONG("Joe");
            // CORRECT
            FindbugsWarningsBySampleSecond.oblUnsatisfiedObligationExceptionEdgeCORRECT("Joe");
 
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != createStatement) {
                try {
                    createStatement.close();
                } catch (final SQLException e) {
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                }
            }
 
        }
 
    }
 
    private static Connection getConnection_dmiConstantDbPasswordWRONG() throws SQLException {
 
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (final ClassNotFoundException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        }
        connection = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "APP",
                "my-secret-password");
        System.out.println("   - DriverManager.getConnection(\"jdbc:derby:database;"
                + "create=true\", \"test\", \"admin\"))");
 
        return connection;
    }
 
    private static Connection getConnection_dmiEmptyDbPasswordWRONG() throws SQLException {
 
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (final ClassNotFoundException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        }
        connection = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "APP", "");
        System.out.println("   - DriverManager.getConnection(\"jdbc:derby:database;create=true\","
                + " \"test\", \"\"))");
        return connection;
    }
 
    private static Connection getConnection_dmiConstantDbPasswordCORRECT() throws SQLException {
 
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (final ClassNotFoundException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        }
        connection = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "APP",
                getSecurePassword());
        System.out.println("   - DriverManager.getConnection(\"jdbc:derby:database;"
                + "create=true\", \"test\", pwdDecoder()))");
        return connection;
    }
 
    static String getSecurePassword() {
        // Here we should fetch and decode the password from a secured resource
        return "my-sec" + "ret-password";
    }
 
    private static void sqlNonconstantStringPassedToExecuteWRONG(final String owner) {
 
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner= '" + owner + "'";
            statement = getConnection_dmiConstantDbPasswordCORRECT().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("   - Result (Statement):" + resultSet.getString("ANSWER"));
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
 
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
 
    private static void sqlNonconstantStringPassedToExecuteCORRECT(final String owner) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
            statement = getConnection_dmiConstantDbPasswordCORRECT().prepareStatement(query);
            statement.setString(1, owner);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("   - Result (PreparedStatement):"
                        + resultSet.getString("ANSWER"));
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
 
    private static void oblUnsatisfiedObligationWRONG(final String owner) {
 
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
            statement = getConnection_dmiConstantDbPasswordCORRECT().prepareStatement(query);
            statement.setString(1, owner);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("   - Result (PreparedStatement):"
                        + resultSet.getString("ANSWER"));
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
 
    private static void oblUnsatisfiedObligationCORRECT(final String owner) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
            statement = getConnection_dmiConstantDbPasswordCORRECT().prepareStatement(query);
            statement.setString(1, owner);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("   - Result (PreparedStatement):"
                        + resultSet.getString("ANSWER"));
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
 
    private static void oblUnsatisfiedObligationExceptionEdgeWRONG(final String owner) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
            statement = getConnection_dmiConstantDbPasswordCORRECT().prepareStatement(query);
            statement.setString(1, owner);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("   - Result (PreparedStatement):"
                        + resultSet.getString("ANSWER"));
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
 
    private static void oblUnsatisfiedObligationExceptionEdgeCORRECT(final String owner) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
            statement = getConnection_dmiConstantDbPasswordCORRECT().prepareStatement(query);
            statement.setString(1, owner);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("   - Result (PreparedStatement):"
                        + resultSet.getString("ANSWER"));
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        } catch (final SQLException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }
        }
    }
}
