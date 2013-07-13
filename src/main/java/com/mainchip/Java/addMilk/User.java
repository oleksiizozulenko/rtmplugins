/*
 * User.java
 * Copyright (C) 2010  Dustin Stroup

 * 
 * This file is part of Java.addMilk.
 *
 * Java.addMilk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java.addMilk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java.addMilk.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mainchip.Java.addMilk;

/**
 * A wrapper containing information about an RTM User.
 * 
 * @author Dustin Stroup
 */
public class User {
	private String _id, _username, _fullname;
	private Transaction _trans;
	
	/**
	 * @param id			The ID associated with this User.
	 */
	public User(String id){
		_id = id;
	}
	
	/**
	 * @param id			The ID associated with this User.
	 * @param username		This Users username.
	 * @param fullname		This Users full name.
	 */
	public User(String id, String username, String fullname){
		_id = id;
		_username = username;
		_fullname = fullname;
	}

	/**
	 * Returns the ID associated with this User.
	 *
	 * @return				The ID associated with this User.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Sets this Users username.
	 *
	 * @param username		The username to set.
	 */
	void setUsername(String username) {
		_username = username;
	}

	/**
	 * Returns this Users username.
	 *
	 * @return				This Users username.
	 */
	public String getUsername() {
		return _username;
	}

	/**
	 * Returns this Users full name.
	 *
	 * @return				This Users full name.
	 */
	public String getFullname() {
		return _fullname;
	}
	
	/**
	 * Sets the Transaction associated with the API call that returned this user data.
	 *
	 * @param trans			The Transaction to set.
	 */
	void setTransaction(Transaction trans){
		_trans = trans;
	}
	
	/**
	 * Returns the Transaction associated with the API call that returned this user data.
	 *
	 * @return				The Transaction associated with the API call that returned this user data.
	 */
	public Transaction getTransaction(){
		return _trans;
	}
}
