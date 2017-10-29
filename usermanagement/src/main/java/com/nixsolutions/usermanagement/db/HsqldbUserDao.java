package com.nixsolutions.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import com.nixsolutions.usermanagement.User;

public class HsqldbUserDao implements UserDao {
	
	


	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private ConnectionFactory connectionFactory;
	
	
	public HsqldbUserDao(){
	}
	public HsqldbUserDao(ConnectionFactory connectionFactory){
		this.connectionFactory = connectionFactory;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseException("Number of the instered rows: "+ n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()){
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}


	public void update(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseException("Number of the updated rows: "+ n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()){
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		

	}


	public void delete(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
			int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseException("Number of the deleted rows: "+ n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()){
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		


	}


	public User find(Long id1) throws DatabaseException {
		try {
			//TODO
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT firstname,lastname,dateofbirth FROM users WHERE id = id1");
			
			
			int n = statement.executeUpdate();
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return null;
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}


	public Collection findAll() throws DatabaseException {
		Collection result = new LinkedList();
		try{
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()){
				User user= new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e){
			throw new DatabaseException(e);
		}
		
		return result;
	}

}
