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
    public void add(User user) {
        /*//事务操作
		TransactionManager.callInTransaction(helper.getConnectionSource(),
				new Callable<Void>()
				{

					@Override
					public Void call() throws Exception
					{
						return null;
					}
				});*/
        try {
//			userDaoOpe.createOrUpdate(user);
            userDaoOpe.create(user);
        } catch (SQLException e) {
            L.d("add User失败");
            e.printStackTrace();
        }

    }

    public boolean update(User user) {
        try {
            Dao.CreateOrUpdateStatus orUpdate = userDaoOpe.createOrUpdate(user);
            if (orUpdate.isCreated()) {
                return true;
            }
            if (orUpdate.isUpdated()) {
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

}
