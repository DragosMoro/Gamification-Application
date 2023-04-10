package com.example.accesa_application.repository;

import com.example.accesa_application.domain.Item;
import com.example.accesa_application.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemRepository implements IItemRepository<Integer>{

    private final JdbcUtils dbUtils;
    public ItemRepository(Properties props)
    {
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void add(Item<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Items(name, price) values (?,?)")) {
            preStmt.setString(1, elem.getName());
            preStmt.setInt(2, elem.getPrice());
            int result = preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

        }

    @Override
    public void update(Integer id, Item<Integer> elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Items set name = ?, price=? where id_item = ?")) {
            preStmt.setString(1, elem.getName());
            preStmt.setInt(2, elem.getPrice());
            preStmt.setInt(3, id);
            int result = preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }

    }

    @Override
    public void delete(Integer id) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Items where id_item = ?")) {
            preStmt.setInt(1, id);
            int result = preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
    }

    @Override
    public Item<Integer> findAfterId(Integer integer) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Items where id_item = ?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    int price = result.getInt("price");
                    Item<Integer> item = new Item<>(name, price);
                    item.setId(integer);
                    return item;
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Iterable<Item<Integer>> getAll() {
        Connection con = dbUtils.getConnection();
        List<Item<Integer>> items = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Items")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id_item");
                    String name = result.getString("name");
                    int price = result.getInt("price");
                    Item<Integer> item = new Item<>(name, price);
                    item.setId(id);
                    items.add(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return items;
    }
}
