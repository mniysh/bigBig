package com.ms.ebangw.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.ms.ebangw.bean.User;

import java.sql.SQLException;


public class UserDao{
	private Dao<User, Integer> userDaoOpe;
	private DatabaseHelper helper;

	public void UserDao(Context context){
		try
		{
			helper = DatabaseHelper.getHelper(context);
			userDaoOpe = helper.getDao(User.class);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}


	/**
	 * 增加一个用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public  void setUser(User user)
	{
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
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void update(User user) {
		try {
			userDaoOpe.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(User user) {
		try {
			userDaoOpe.delete(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User getUser(int id)
	{
		try
		{
			return userDaoOpe.queryForId(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
