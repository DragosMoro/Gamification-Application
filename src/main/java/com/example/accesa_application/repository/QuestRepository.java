package com.example.accesa_application.repository;

import com.example.accesa_application.domain.Quest;
import com.example.accesa_application.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuestRepository implements IQuestRepository<Integer>{
    private final JdbcUtils dbUtils;
    public QuestRepository(Properties props)
    {
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void add(Quest<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Quests(id_user,name, description, points, completed) values (?,?,?,?,?)")) {
            preStmt.setInt(1, elem.getUserId());
            preStmt.setString(2, elem.getName());
            preStmt.setString(3, elem.getDescription());
            preStmt.setInt(4, elem.getPoints());
            preStmt.setBoolean(5, elem.isCompleted());
            preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

    }

    @Override
    public void update(Integer id, Quest<Integer> elem) {
    Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Quests set id_user = ?, name = ?, description = ?, points = ?, completed = ? where id_quest = ?")) {
            preparedStatement.setInt(1, elem.getUserId());
            preparedStatement.setString(2, elem.getName());
            preparedStatement.setString(3, elem.getDescription());
            preparedStatement.setInt(4, elem.getPoints());
            preparedStatement.setBoolean(5, elem.isCompleted());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public void delete(Integer id) {
    Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Quests where id_quest = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

    }

    @Override
    public Quest<Integer> findAfterId(Integer id) {
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Quests where id_quest = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idUser = resultSet.getInt("id_user");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    int points = resultSet.getInt("points");
                    boolean completed = resultSet.getBoolean("completed");
                    Quest<Integer> quest = new Quest<>(idUser, name, description, points, completed);
                    quest.setId(id);
                    return quest;
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<Quest<Integer>> getAll() {
        Connection connection = dbUtils.getConnection();
        List<Quest<Integer>> quests = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Quests")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_quest");
                    int idUser = resultSet.getInt("id_user");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    int points = resultSet.getInt("points");
                    boolean completed = resultSet.getBoolean("completed");
                    Quest<Integer> quest = new Quest<>(idUser, name, description, points, completed);
                    quest.setId(id);
                    quests.add(quest);
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        return quests;
    }
}
