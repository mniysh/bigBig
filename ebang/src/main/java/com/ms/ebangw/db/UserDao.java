package com.ms.ebangw.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.L;

import java.sql.SQLException;
import java.util.List;


public class UserDao {
    private Dao<User, Integer> userDaoOpe;
    private DatabaseHelper helper;

    public  UserDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            userDaoOpe = helper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param user
     * @throws SQLException
     */
    public boolean add(User user) {

        try {
//			userDaoOpe.createOrUpdate(user);
            int i = userDaoOpe.create(user);
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            L.d("add User失败");
            e.printStackTrace();
        }
        return false;

    }

    public boolean update(User user) {
        try {
            int update = userDaoOpe.update(user);
            if (update > 0) {
                return true;
            }
        } catch (SQLException e) {
            L.d("update  User失败");
            e.printStackTrace();
        }
        return false;
    }

    public void delete(User user) {
        try {
            userDaoOpe.delete(user);
        } catch (SQLException e) {
            L.d("delete User失败");
            e.printStackTrace();
        }
    }
    
    

    public User getUserById(int id) {
        try {
            return userDaoOpe.queryForId(id);
        } catch (SQLException e) {
            L.d("获取User失败");
            e.printStackTrace();
        }
        return null;
    }

    public User getUser() {
        try {
            List<User> users = userDaoOpe.queryForAll();
            if (null != users && users.size() > 0) {
                return users.get(0);
            }
            
        } catch (SQLException e) {
            L.d("获取User失败");
            e.printStackTrace();
        }
        return null;
    }

    public void removeAll() {

        List<User> users;
        try {
            users = userDaoOpe.queryForAll();
            if (null != users && users.size() > 0) {
                int count = users.size();
                for (int i = 0; i < count; i++) {
                    User user = users.get(i);
                    delete(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
