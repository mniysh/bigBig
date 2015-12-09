package com.ms.ebangw.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.ms.ebangw.bean.EMUser;

import java.sql.SQLException;
import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-09 09:57
 */
public class EMUserDao {
    private Dao<EMUser, Integer> dao;
    private DatabaseHelper helper;

    public  EMUserDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            dao = helper.getDao(EMUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param emUser
     * @throws SQLException
     */
    public boolean add(EMUser emUser) {

        try {
            Dao.CreateOrUpdateStatus orUpdate = dao.createOrUpdate(emUser);
            if (orUpdate.isUpdated() || orUpdate.isCreated()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean update(EMUser emUser) {
        try {
            Dao.CreateOrUpdateStatus orUpdate = dao.createOrUpdate(emUser);
            if (orUpdate.isCreated() || orUpdate.isUpdated()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(EMUser emUser) {
        try {
            dao.delete(emUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public EMUser getUserById(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EMUser> getAllEmUser() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public void removeAll() {

        List<EMUser> list;
        try {
            list = dao.queryForAll();
            if (null != list && list.size() > 0) {
                int count = list.size();
                for (int i = 0; i < count; i++) {
                    EMUser emUser = list.get(i);
                    delete(emUser);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}  
