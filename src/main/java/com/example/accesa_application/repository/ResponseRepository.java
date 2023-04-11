package com.example.accesa_application.repository;

import com.example.accesa_application.domain.Response;
import com.example.accesa_application.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ResponseRepository implements IResponseRepository<Integer>{
    private final JdbcUtils dbUtils;
    public ResponseRepository(Properties props)
    {
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void add(Response<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Responses(id_user, id_quest,message,status) values (?,?,?,?)")) {
            preStmt.setInt(1, elem.getIdUser());
            preStmt.setInt(2, elem.getIdQuest());
            preStmt.setString(3, elem.getMessage());
            preStmt.setString(4, elem.getStatus());
            preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public void update(Integer id, Response<Integer> elem) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Responses set id_user = ?, id_quest = ?, message = ?, status = ? where id_response = ?")) {
            preparedStatement.setInt(1, elem.getIdUser());
            preparedStatement.setInt(2, elem.getIdQuest());
            preparedStatement.setString(3, elem.getMessage());
            preparedStatement.setString(4, elem.getStatus());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Responses where id_response = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public Response<Integer> findAfterId(Integer id) {
Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Responses where id_response = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idUser = resultSet.getInt("id_user");
                    int idQuest = resultSet.getInt("id_quest");
                    String message = resultSet.getString("message");
                    String status = resultSet.getString("status");
                    Response<Integer> response = new Response<>(idUser,idQuest, message, status);
                    response.setId(id);
                    return response;
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<Response<Integer>> getAll() {
        Connection connection = dbUtils.getConnection();
        List<Response<Integer>> responses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Responses")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_response");
                    int idUser = resultSet.getInt("id_user");
                    int idQuest = resultSet.getInt("id_quest");
                    String message = resultSet.getString("message");
                    String status = resultSet.getString("status");
                    Response<Integer> response = new Response<>(idUser, idQuest, message, status);
                    response.setId(id);
                    responses.add(response);
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return responses;
    }
}
