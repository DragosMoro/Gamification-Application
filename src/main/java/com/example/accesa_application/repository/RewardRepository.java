package com.example.accesa_application.repository;

import com.example.accesa_application.domain.Reward;
import com.example.accesa_application.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RewardRepository  implements IRewardRepository<Integer>{
    private final JdbcUtils dbUtils;
    public RewardRepository(Properties props)
    {
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void add(Reward<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Rewards(id_user,id_item) values (?,?)")) {
            preStmt.setInt(1, elem.getIdUser());
            preStmt.setInt(2, elem.getIdItem());
            int result = preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        }

    @Override
    public void update(Integer integer, Reward<Integer> elem) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Rewards set id_user = ?, id_item=? where id_reward = ?")) {
            preparedStatement.setInt(1, elem.getIdUser());
            preparedStatement.setInt(2, elem.getIdItem());
            preparedStatement.setInt(3, integer);
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Rewards where id_reward = ?")) {
            preparedStatement.setInt(1, integer);
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

    }

    @Override
    public Reward<Integer> findAfterId(Integer id) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Rewards where id_reward = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    int idUser = result.getInt("id_user");
                    int idItem = result.getInt("id_item");
                    Reward<Integer> reward = new Reward<>(idUser, idItem);
                    reward.setId(id);
                    return reward;
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<Reward<Integer>> getAll() {
        Connection connection = dbUtils.getConnection();
        List<Reward<Integer>> rewards = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Rewards")) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id_reward");
                    int idUser = result.getInt("id_user");
                    int idItem = result.getInt("id_item");
                    Reward<Integer> reward = new Reward<>(idUser, idItem);
                    reward.setId(id);
                    rewards.add(reward);
                }
                return rewards;
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return rewards;
    }
}
