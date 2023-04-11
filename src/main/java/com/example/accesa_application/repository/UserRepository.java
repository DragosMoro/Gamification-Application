package com.example.accesa_application.repository;

import com.example.accesa_application.domain.User;
import com.example.accesa_application.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IUserRepository<Integer>{
    private final JdbcUtils dbUtils;
    public UserRepository(Properties props)
    {
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void add(User<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Users(username, email, password, no_of_quests, points) values (?,?,?,?,?)")) {
            preStmt.setString(1, elem.getUsername());
            preStmt.setString(2, elem.getEmail());
            preStmt.setString(3, elem.getPassword());
            preStmt.setInt(4, elem.getNumberOfQuestsCompleted());
            preStmt.setInt(5, elem.getPoints());
            preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public void update(Integer id, User<Integer> elem) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Users set username = ?, email = ?, password = ?, no_of_quests = ?, points = ? where id_user = ?")) {
            preparedStatement.setString(1, elem.getUsername());
            preparedStatement.setString(2, elem.getEmail());
            preparedStatement.setString(3, elem.getPassword());
            preparedStatement.setInt(4, elem.getNumberOfQuestsCompleted());
            preparedStatement.setInt(5, elem.getPoints());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }
    @Override
    public void delete(Integer id) {
    Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Users where id_user = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public User<Integer> findAfterId(Integer id) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where id_user = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    int noOfQuests = resultSet.getInt("no_of_quests");
                    int points = resultSet.getInt("points");
                    User<Integer> user = new User<>(username, email, password, noOfQuests, points);
                    user.setId(id);
                    return user;
                }
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<User<Integer>> getAll() {
        Connection connection = dbUtils.getConnection();
        List<User<Integer>> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Users")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int id = resultSet.getInt("id_user");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    int noOfQuests = resultSet.getInt("no_of_quests");
                    int points = resultSet.getInt("points");
                    User<Integer> user = new User<>(username, email, password, noOfQuests, points);
                    user.setId(id);
                    users.add(user);
                }
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return users;
    }

    @Override
    public User<Integer> getUserByEmail(String email) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int id = resultSet.getInt("id_user");
                    String password = resultSet.getString("password");
                    int noOfQuests = resultSet.getInt("no_of_quests");
                    int points = resultSet.getInt("points");
                    User<Integer> user = new User<>(username, email, password, noOfQuests, points);
                    user.setId(id);
                    return user;
                }
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public User<Integer> getUserByUsername(String username) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_user");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    int noOfQuests = resultSet.getInt("no_of_quests");
                    int points = resultSet.getInt("points");
                    User<Integer> user = new User<>(username, email, password, noOfQuests, points);
                    user.setId(id);
                    return user;
                }
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }
}
