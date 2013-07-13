/*
 * TaskList.java
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
 * A wrapper containing information about an RTM TaskList.
 * 
 * @author Dustin Stroup
 */
public class TaskList {
	private String _id, _name, _position, _filter;
	private boolean _deleted, _locked, _archived, _smart;
	private Vector<Taskseries> _series;
	private Transaction _trans;
	
	public TaskList(){
	}
	
	/**
	 * @param id			The ID associated with this TaskList.
	 */
	public TaskList(String id) {
		_id = id;
	}

	/**
	 * @param id			The ID associated with this TaskList.
	 * @param name			The name of this TaskList.
	 * @param position		The position of this TaskList.
	 * @param smart			Whether or not this TaskList is a "Smart List".
	 * @param deleted		Whether or not this TaskList is deleted.
	 * @param locked		Whether or not this TaskList is locked.
	 * @param archived		Whether or not this TaskList is archived.
	 */
	public TaskList(String id, String name, String position, String smart, String deleted, String locked, String archived){
		_id = id;
		_name = name;
		_position = position;
		_smart = smart.equals("1");
		_deleted = deleted.equals("1");
		_locked = locked.equals("1");
		_archived = archived.equals("1");
	}
	
	/**
	 *	Returns the ID associated with this TaskList.
	 *
	 * @return				The ID associated with this TaskList.
	 */
	public String getID() {
		return _id;
	}
	
	/**
	 *	Returns the name of this TaskList.
	 *
	 * @return				The name of this TaskList.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns the position of a TaskList.  Currently only used internally to RTM for the Inbox and Sent lists.
	 *
	 * @return				The position of the list.
	 */
	public String getPosition() {
		return _position;
	}
	
	/**
	 * Indicates whether or not this is a "Smart List".
	 *
	 * @return				True if this is a "Smart List", otherwise returns false.
	 */
	public boolean isSmart() {
		return _smart;
	}
	
	/**
	 *	Returns the filter associated with this TaskList, if any.
	 *
	 * @return				The filter associated with this TaskList, if any.
	 */
	public String getFilter() {
		return _filter;
	}
	
	/**
	 * Sets the filter associated with this TaskList.
	 *
	 * @param filter		The filter to be set.
	 */
	void setFilter(String filter) {
		_filter = filter;
	}
	
	/**
	 * Indicates the deleted status of this TaskList.
	 *
	 * @return				Returns true if this TaskList has been deleted, otherwise returns false.
	 */
	public boolean isDeleted() {
		return _deleted;
	}
	
	/**
	 * Indicates the locked status of this TaskList.
	 *
	 * @return				Returns true if this TaskList is locked, otherwise returns false.
	 */
	public boolean isLocked() {
		return _locked;
	}
	
	/**
	 * Indicates the archived status of this TaskList.
	 *
	 * @return				Returns true if this TaskList is archived, otherwise returns false.
	 */
	public boolean isArchived() {
		return _archived;
	}
	
	/**
	 * Returns the transaction associated with the API call that returned this TaskList.
	 *
	 * @return				The transaction associated with the API call that returned this TaskList.
	 */
	public Transaction getTransaction(){
		return _trans;
	}
	
	/**
	 * Sets the transaction associated with the API call that returned this TaskList.
	 *
	 * @param trans			The transaction to set.
	 */
	void setTransaction(Transaction trans){
		_trans = trans;
	}
	
	/**
	 * Returns the list of Taskseries in this TaskList.
	 * 
	 * @return 				A vector of the Taskseries in this TaskList.
	 */
	public Vector<Taskseries> getSeries() {
		return _series;
	}

	/**
	 * Sets the vector of Taskseries for this list.
	 * 
	 * @param series 		The vector of Taskseries to set.
	 */
	void setSeries(Vector<Taskseries> series) {
		_series = series;
	}
	
	/**
	 * Add a Taskseries to this list.
	 * 
	 * @param series		The Taskseries to add to this list.
	 */
	void addSeries(Taskseries series){
		if(_series == null){
			_series = new Vector<Taskseries>();
		}
		
		_series.add(series);
	}
}
