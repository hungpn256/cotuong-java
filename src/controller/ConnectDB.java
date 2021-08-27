package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;



public class ConnectDB {
	private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
    public ConnectDB(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    
    public void connect() throws SQLException {
        if (this.jdbcConnection == null || this.jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
            	System.out.println("loi ket noi");
                throw new SQLException(e);
            }
            System.out.println("start connect");
            this.jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("connected");
        }
    }
    
    protected void disconnect() throws SQLException {
        if (this.jdbcConnection != null && !this.jdbcConnection.isClosed()) {
            this.jdbcConnection.close();
        }
    }
	
    public boolean register(String username,String password) throws SQLException {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        this.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        this.disconnect();
        return rowInserted;
    }
    
    public User Login(String username,String password) throws SQLException, Exception {
    	User user = null;
    	String sql = "SELECT * FROM user WHERE username = ?";
        this.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, username);
        
        ResultSet resultSet = statement.executeQuery();
        
        if(resultSet.next()) {
        	String UName = resultSet.getString("username");
        	String UPassword = resultSet.getString("password");
        	int win = resultSet.getInt("win");
        	if(UPassword == password) {
        		user = new User(UName,win);
        	}
        	else throw new Exception("mat khau khong dung");
        }
        else throw new Exception("khong ton tai tai khoan");
        statement.close();
        this.disconnect();
        return user;
    }
    public boolean updateUser(User user) throws SQLException, Exception {
    	String sql = "UPDATE user FROM book SET win = ?";
    	sql += " WHERE username = ?";
        this.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setInt(2, user.getWin());
        
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        this.disconnect();
        return rowUpdated;
    }
    public List<User> getTopWinner()  throws SQLException{
    	List<User> listUser = new ArrayList<>();
    	String sql = "SELECT TOP(10) * FROM user ORDER BY win desc";
    	 Statement statement = this.jdbcConnection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql);
         while(resultSet.next()) {
        	 String UName = resultSet.getString("username");
        	 int win = resultSet.getInt("win");
        	 User u = new User(UName,win);
        	 listUser.add(u);
         }
         statement.close();
         disconnect();
         return listUser;
    }
    
}
