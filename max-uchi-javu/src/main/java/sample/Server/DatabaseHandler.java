package sample.Server;

import sample.Client.Configs;

import javax.xml.crypto.Data;
import java.sql.*;

import static java.lang.Math.round;

public class DatabaseHandler extends Configs {
    Connection dbConn;


    public Connection getDbConn() throws ClassNotFoundException, SQLException{
        String conntString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";
        dbConn = DriverManager.getConnection(conntString, dbUser, dbPass);
        return dbConn;
    }


    public DataObject RegUser(DataObject msg){
        ResultSet rs = null;
        String enter = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_USERNAME + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(enter);
            ps.setString(1, msg.getLogin());
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(Const.USER_USERNAME));
                System.out.println(msg.getLogin());
                if (rs.getString(Const.USER_USERNAME).equals(msg.getLogin())) {
                    System.out.println("keke");
                    msg.setResult(false);
                    return msg;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," +
                Const.USER_USERNAME + "," + Const.USER_PASSWORD + "," +
                Const.USER_LOCATION + "," + Const.USER_GENDER + ")" + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getFirstName());
            ps.setString(2, msg.getLastName());
            ps.setString(3, msg.getLogin());
            ps.setString(4, msg.getPassword());
            ps.setString(5, msg.getLocation());
            ps.setString(6, msg.getGender());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setResult(true);
        return msg;
    }


    public DataObject ChechEnter(DataObject msg) {
        ResultSet rs = null;
        String enter = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_USERNAME + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(enter);
            ps.setString(1, msg.getLogin());
            ps.setString(2, msg.getPassword());
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs.next()) {
                msg.setResult(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) { }
        if (msg.isResult()) {
            try {
                msg.setId(rs.getInt(Const.USER_ID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    public DataObject[] getCompany(DataObject msg) {
        ResultSet rs = null;
       String getIncomes = "SELECT * FROM " + Const.COMPANY_TABLE  + " INNER JOIN " + Const.USER_TABLE + " ON "
               + Const.USER_TABLE + "." + Const.USER_ID + "=" + Const.COMPANY_TABLE + "." + Const.USER_ID + " INNER JOIN " + Const.CAPITAL_TABLE +
               " ON " + Const.CAPITAL_TABLE + "." + Const.CAPITAL_COMPANY_ID + "=" + Const.COMPANY_TABLE + "." + Const.COMPANY_ID +
               " INNER JOIN " + Const.BORCAPITAL_TABLE +
               " ON " + Const.BORCAPITAL_TABLE + "." + Const.BORCAPITAL_COMPANY_ID + "=" + Const.COMPANY_TABLE + "." + Const.COMPANY_ID +
               " WHERE "+ Const.USER_TABLE + "." + Const.USER_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
           ps.setInt(1, msg.getId());
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataObject arr[] = new DataObject[100];
        int counter=0;
        try {
            while (rs.next()) {
                arr[counter] = new DataObject();
                arr[counter].setId(rs.getInt(Const.USER_ID));
                arr[counter].setFirstName(rs.getString(Const.USER_FIRSTNAME));
                arr[counter].setLastName(rs.getString(Const.USER_LASTNAME));
                arr[counter].setCompanyName(rs.getString(Const.COMPANY_NAME));
                arr[counter].setCompanyTag(rs.getString(Const.COMPANY_TAG));
                arr[counter].setCompanyId(rs.getInt(Const.COMPANY_ID));
                arr[counter].setAvailableSum(rs.getString(Const.CAPITAL_AVL_SUM));
                arr[counter].setGratInvest(rs.getString(Const.CAPITAL_GRAT_INV));
                arr[counter].setCredit(rs.getString(Const.BORCAPITAL_CREDIT));
                arr[counter].setPercent(rs.getString(Const.BORCAPITAL_PERCENT));
                arr[counter].setWithoutPercent(rs.getString(Const.BORCAPITAL_WTH_PERCENT));
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){ }
        DataObject res[] = new DataObject[counter+1];
        for (int i=0; i<res.length-1; i++){
            res[i] = new DataObject();
            res[i] = arr[i];
        }
        res[res.length-1] = new DataObject();
        res[res.length-1].setCounter(counter+1);
        return res;
    }

    public DataObject addCompany(DataObject msg) throws SQLException {
        String insert = "INSERT INTO " + Const.COMPANY_TABLE + "(" +
                Const.COMPANY_NAME + "," + Const.COMPANY_TAG + "," + Const.USER_ID + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getCompanyName());
            ps.setString(2, msg.getCompanyTag());
            ps.setInt(3, msg.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        String enter = "SELECT * FROM " + Const.COMPANY_TABLE +
                " WHERE " + Const.COMPANY_NAME + "=? AND " + Const.COMPANY_TAG + "=? AND " + Const.USER_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(enter);
            ps.setString(1, msg.getCompanyName());
            ps.setString(2, msg.getCompanyTag());
            ps.setInt(3, msg.getId());
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (rs.next()) {
            msg.setCompanyId(rs.getInt(Const.COMPANY_ID));
        }
        return msg;
    }

    public void addCapital(DataObject msg) {
        String insert = "INSERT INTO " + Const.CAPITAL_TABLE + "(" +
                Const.CAPITAL_AVL_SUM + "," + Const.CAPITAL_GRAT_INV + "," + Const.COMPANY_ID + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getAvailableSum());
            ps.setString(2, msg.getGratInvest());
            ps.setInt(3, msg.getCompanyId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addBorrowedCapital(DataObject msg) {
        String insert = "INSERT INTO " + Const.BORCAPITAL_TABLE + "(" +
                Const.BORCAPITAL_CREDIT + "," + Const.BORCAPITAL_PERCENT + "," + Const.BORCAPITAL_COMPANY_ID + "," + Const.BORCAPITAL_WTH_PERCENT + ")" + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(insert);
            ps.setString(1, msg.getCredit());
            ps.setString(2, msg.getPercent());
            ps.setInt(3, msg.getCompanyId());
            ps.setString(4, msg.getWithoutPercent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DataObject confirmRisk(DataObject msg) throws SQLException {
        ResultSet rs = null;
        String capital = " SELECT * FROM " + Const.CAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(capital);
            ps.setInt(1, msg.getCompanyId());
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double sum = 0;
        if (rs.next()) {
            sum += Integer.valueOf(rs.getString(Const.CAPITAL_AVL_SUM)) + Integer.valueOf(rs.getString(Const.CAPITAL_GRAT_INV));
        }
        ResultSet result = null;
        String borrowed = " SELECT * FROM " + Const.BORCAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(borrowed);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double sum2 = 0;
        if (result.next()) {
            sum2 += Double.valueOf(result.getString(Const.BORCAPITAL_WTH_PERCENT)) + (Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT)) * (Double.valueOf(result.getString(Const.BORCAPITAL_PERCENT)) /100));
        }
        double multy = sum2/sum;
        String level;
        if (multy > 1) {
            level = "Высокий";
        }
        else if (multy > 0.5 && multy < 1){
            level = "Средний";
        }
        else {
            level = "Низкий";
        }
        msg.setMultiply(String.valueOf(multy));
        String risk = "INSERT INTO " + Const.TOTALRISK_TABLE + "(" +
                Const.TOTALRISK_MULTIPLY + "," + Const.TOTALRISK_LEVEL + "," + Const.COMPANY_ID + ")" + "VALUES(?,?,?) ON DUPLICATE KEY UPDATE " + Const.TOTALRISK_MULTIPLY + "=?, " + Const.TOTALRISK_LEVEL + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(risk);
            ps.setString(1, msg.getMultiply());
            ps.setString(2, level);
            ps.setInt(3, msg.getCompanyId());
            ps.setString(4, msg.getMultiply());
            ps.setString(5, level);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public DataObject bisnes(DataObject msg) {
        ResultSet result = null;
        String risk = " SELECT * FROM " + Const.TOTALRISK_TABLE + " INNER JOIN " + Const.CAPITAL_TABLE + " ON " + Const.CAPITAL_TABLE + "." + Const.COMPANY_ID + " = " + Const.TOTALRISK_TABLE + "."
                + Const.TOTALRISK_COMPANY_ID + " INNER JOIN " + Const.BORCAPITAL_TABLE + " ON " + Const.BORCAPITAL_TABLE + "." + Const.BORCAPITAL_COMPANY_ID + " = " + Const.TOTALRISK_TABLE + "." + Const.TOTALRISK_COMPANY_ID +
                " WHERE " + Const.TOTALRISK_TABLE + "." + Const.COMPANY_ID + " = ?";
        double diff = 0;
        try {

            PreparedStatement ps = getDbConn().prepareStatement(risk);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
            double myCapital = 0;
            double borrowedCapital = 0;
            if (result.next()) {
                borrowedCapital += Double.valueOf(result.getString(Const.BORCAPITAL_WTH_PERCENT)) + (Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT)) * (Double.valueOf(result.getString(Const.BORCAPITAL_PERCENT)) /100));
                myCapital += Double.valueOf(result.getString(Const.CAPITAL_GRAT_INV)) + (Double.valueOf(result.getString(Const.CAPITAL_AVL_SUM)));
            if (Double.valueOf(result.getString(Const.TOTALRISK_MULTIPLY)) > 0.5) {
                System.out.println("kek");
                diff = borrowedCapital / 0.49 - myCapital;
                System.out.println(borrowedCapital / 0.49);
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setMultiply(String.valueOf(diff));
        return msg;
    }
    public DataObject riskGetInfo(DataObject msg) {
        ResultSet result = null;
        String risk = " SELECT * FROM " + Const.TOTALRISK_TABLE + " INNER JOIN " + Const.COMPANY_TABLE + " ON " + Const.COMPANY_TABLE + "." + Const.COMPANY_ID + " = " + Const.TOTALRISK_TABLE + "."
                + Const.TOTALRISK_COMPANY_ID + " WHERE " + Const.COMPANY_TABLE + "." + Const.COMPANY_ID + " = ?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(risk);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
            if (result.next()) {
                msg.setMultiply(result.getString(Const.TOTALRISK_MULTIPLY));
                msg.setLevel(result.getString(Const.TOTALRISK_LEVEL));
                msg.setCompanyName(result.getString(Const.COMPANY_NAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public DataObject diagLine(DataObject msg) {
        ResultSet result = null;
        String borrowed = " SELECT * FROM " + Const.BORCAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        double firstYear = 0;
        double secondYear = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(borrowed);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
            if (result.next()) {
                firstYear = Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT));
                secondYear = (Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT)) * (Double.valueOf(result.getString(Const.BORCAPITAL_PERCENT)) /100));
                msg.setPercent(result.getString(Const.BORCAPITAL_PERCENT));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setLevel(String.valueOf(firstYear));
        msg.setMultiply(String.valueOf(secondYear));
        return msg;
    }

    public DataObject margin(DataObject msg) {
        ResultSet result = null;
        String borrowed = " SELECT * FROM " + Const.BORCAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        double firstYear = 0;
        double secondYear = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(borrowed);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
            if (result.next()) {
                secondYear = (Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT)) * (Double.valueOf(result.getString(Const.BORCAPITAL_PERCENT)) /100));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setLevel(String.valueOf(secondYear));
        return msg;
    }

    public void editCompany(DataObject msg){
        String getIncomes = "UPDATE " + Const.COMPANY_TABLE + " SET " + Const.COMPANY_NAME + "=?, "
                + Const.COMPANY_TAG + "=? WHERE " + Const.COMPANY_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getCompanyName());
            ps.setString(2, msg.getCompanyTag());
            ps.setInt(3, msg.getCompanyId());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCapital(DataObject msg) {
        String getIncomes = "UPDATE " + Const.CAPITAL_TABLE + " SET " + Const.CAPITAL_AVL_SUM + "=?, "
                + Const.CAPITAL_GRAT_INV + "=? WHERE " + Const.COMPANY_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getAvailableSum());
            ps.setString(2, msg.getGratInvest());
            ps.setInt(3, msg.getCompanyId());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBorrowedCapital(DataObject msg) {
        String getIncomes = "UPDATE " + Const.BORCAPITAL_TABLE + " SET " + Const.BORCAPITAL_PERCENT + "=?, "
                + Const.BORCAPITAL_CREDIT + "=?, " + Const.BORCAPITAL_WTH_PERCENT + "=? WHERE " + Const.COMPANY_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, msg.getPercent());
            ps.setString(2, msg.getCredit());
            ps.setString(3, msg.getWithoutPercent());
            ps.setInt(4, msg.getCompanyId());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany(DataObject msg){
        String getIncomes = "DELETE FROM " + Const.COMPANY_TABLE + " WHERE " + Const.COMPANY_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setInt(1, msg.getCompanyId());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DataObject diagCircle(DataObject msg) {
        ResultSet rs = null;
        double myCapital = 0;
        String capital = " SELECT * FROM " + Const.CAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(capital);
            ps.setInt(1, msg.getCompanyId());
            rs = ps.executeQuery();
            if (rs.next()) {
                myCapital += Double.valueOf(rs.getString(Const.CAPITAL_AVL_SUM)) + Double.valueOf(rs.getString(Const.CAPITAL_GRAT_INV));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ResultSet result = null;
        String borrowed = " SELECT * FROM " + Const.BORCAPITAL_TABLE + " WHERE " + Const.COMPANY_ID + " = ?";
        double borrowedCapital = 0;
        try {
            PreparedStatement ps = getDbConn().prepareStatement(borrowed);
            ps.setInt(1, msg.getCompanyId());
            result = ps.executeQuery();
            if (result.next()) {
                borrowedCapital += Double.valueOf(result.getString(Const.BORCAPITAL_WTH_PERCENT)) + (Double.valueOf(result.getString(Const.BORCAPITAL_CREDIT)) * (Double.valueOf(result.getString(Const.BORCAPITAL_PERCENT))/100));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        msg.setCredit(String.valueOf(myCapital));
        msg.setPercent(String.valueOf(borrowedCapital));
        return msg;
    }

    public void DelUser(DataObject msg){
        String getIncomes = "DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + "=?";
        try {
            PreparedStatement ps = getDbConn().prepareStatement(getIncomes);
            ps.setString(1, String.valueOf(msg.getId()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
