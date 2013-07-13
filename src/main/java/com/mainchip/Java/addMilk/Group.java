/*
 * Group.java
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

import java.util.Vector;

/**
 * An wrapper containing the information about an RTM Group.
 * 
 * @author Dustin Stroup
 */
public class Group {
	private Vector<User> _users;
	private String _id, _name;
	private Transaction _trans;
	
	/**
	 * @param id	The ID of the group.
	 * @param name	The name of the group.
	 */
	public Group(String id, String name){
		_id = id;
		_name = name;
	}
	
	/**
	 * Returns the transaction information associated with an API call, if any.
	 * 
	 * @return		Transaction information if an API call returned any, otherwise null.
	 */
	public Transaction getTransaction() {
		return _trans;
	}

	/**
	 * Sets the transaction information.
	 * 
	 * @param trans	The transaction object to set.
	 */
	void setTransaction(Transaction trans) {
		_trans = trans;
	}
	
	/**
	 * Returns a Vector containing the users in this group.
	 * 
	 * @return		A Vector containing the users in this group.  Returns null if the group is empty.
	 */
	public Vector<User> getUsers() {
		return _users;
	}
	
	/**
	 * Set the list of users in this group.
	 * 
	 * @param users	The list of users to set.
	 */
	void setUsers(Vector<User> users) {
		_users = users;
	}
	
	/**
	 * Returns the ID associated with this group.
	 * 
	 * @return		A string containing the ID of this group.
	 */
	public String getID() {
		return _id;
	}
	
	/**
	 * Returns the name of this group.
	 * 
	 * @return		The name of this group.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Add a user to this group.
	 * 
	 * @param user	The user to add to the group.
	 */
	void addUser(User user){
		_users.add(user);
	}
}
