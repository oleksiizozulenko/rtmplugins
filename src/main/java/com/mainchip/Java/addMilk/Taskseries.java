/*
 * Taskseries.java
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
 * A wrapper containing information about an RTM Taskseries.
 * 
 * @author Dustin Stroup
 */
public class Taskseries {
	private String _id, _created, _modified, _name, _source, _url, _locationID;
	private Rrule _rrule;
	private Vector<Note> _notes;
	private Vector<Task> _tasks;
	private Vector<User> _participants;
	private Vector<String> _tags;
	
	/**
	 * @param id			The ID associated with this Taskseries.
	 * @param created		A timestamp indicating when this Taskseries was created.
	 * @param modified		A timestamp indicating the last time this Taskseries was modified.
	 * @param name			The name of this Taskseries.
	 * @param source		The source of this Taskseries.
	 * @param url			The URL associated with this Taskseries.
	 * @param locationID	The ID of the location associated with this Taskseries.
	 */
	public Taskseries(String id, String created, String modified, String name,
			String source, String url, String locationID) {
		_id = id;
		_created = created;
		_modified = modified;
		_name = name;
		_source = source;
		_url = url;
		_locationID = locationID;
	}

	/**
	 * @param id			The ID associated with this Taskseries.
	 * @param created		A timestamp indicating when this Taskseries was created.
	 * @param modified		A timestamp indicating the last time this Taskseries was modified.
	 * @param name			The name of this Taskseries.
	 * @param source		The source of this Taskseries.
	 * @param url			The URL associated with this Taskseries.
	 * @param locationID	The ID of the location associated with this Taskseries.
	 * @param tags			A comma delimited list of tags for this Taskseries.
	 * @param participants	A list of participants for this Taskseries.
	 * @param rrule			The recurrence rule for this Taskseries.
	 * @param notes			The list of notes for this Taskseries.
	 * @param tasks			The list of tasks in this Taskseries.
	 */
	public Taskseries(String id, String created, String modified, String name, String source, String url, String locationID, Vector<String> tags,
			Vector<User> participants, Rrule rrule, Vector<Note> notes, Vector<Task> tasks) {
		_id = id;
		_created = created;
		_modified = modified;
		_name = name;
		_source = source;
		_url = url;
		_locationID = locationID;
		_tags = tags;
		_participants = participants;
		_rrule = rrule;
		_notes = notes;
		_tasks = tasks;
	}

	/**
	 * Returns the ID associated with this Taskseries.
	 * 
	 * @return 				The ID associated with this Taskseries.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Returns a timestamp indicating when this Taskseries was created.
	 * 
	 * @return 				A timestamp indicating when this Taskseries was created.
	 */
	public String getCreated() {
		return _created;
	}

	/**
	 * Returns a timestamp indicating the last time this Taskseries was modified.
	 * 
	 * @return 				A timestamp indicating the last time this Taskseries was modified.
	 */
	public String getModified() {
		return _modified;
	}

	/**
	 * Returns the name of this Taskseries
	 * 
	 * @return 				The name of this Taskseries.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the source of this Taskseries (API, website, etc).
	 *
	 * @return				The source of this Taskseries.
	 */
	public String getSource() {
		return _source;
	}

	/**
	 * Returns the URL associated with this Taskseries.
	 * 
	 * @return 				The URL associated with this Taskseries.
	 */
	public String getUrl() {
		return _url;
	}

	/**
	 * Returns the ID of the location associated with this Taskseries.
	 * 
	 * @return 				The ID of the location associated with this Taskseres.
	 */
	public String getLocationID() {
		return _locationID;
	}

	/**
	 * Returns the tags for this Taskseries.
	 * 
	 * @return 				A comma delimited list of tags for this Taskseries.
	 */
	public Vector<String> getTags() {
		return _tags;
	}

	/**
	 * Returns the list of participants for this Taskseries.
	 * 
	 * @return 				The list of participants for this Taskseries.
	 */
	public Vector<User> getParticipants() {
		return _participants;
	}

	/**
	 * Returns the recurrence rule for this Taskseries.
	 * 
	 * @return 				The recurrence rule for this Taskseries.
	 */
	public Rrule getRrule() {
		return _rrule;
	}

	/**
	 * Sets the recurrence rule for this Taskseries.
	 * 
	 * @param rrule 		The recurrence rule to set.
	 */
	void setRrule(Rrule rrule) {
		_rrule = rrule;
	}

	/**
	 * Returns the notes contained in this Taskseries.
	 * 
	 * @return 				The notes contained in this Taskseries.
	 */
	public Vector<Note> getNotes() {
		return _notes;
	}

	/**
	 * Returns the Tasks contained in this Taskseries.
	 * 
	 * @return 				The tasks contained in this Taskseries.
	 */
	public Vector<Task> getTasks() {
		return _tasks;
	}

	/**
	 * Adds a tag to this Taskseries.
	 *
	 * @param tag			The tag to add to this Taskeries.
	 */
	public void addTag(String tag) {
		if(_tags == null){
			_tags = new Vector<String>();
		}
		
		_tags.add(tag);
	}
	
	/**
	 * Adds a note to this Taskseries.
	 *
	 * @param note			The note to add to this Taskseries.
	 */
	public void addNote(Note note){
		if(_notes == null){
			_notes = new Vector<Note>();
		}
		
		_notes.add(note);
	}
	
	/**
	 * Adds a task to this Taskseries.
	 *
	 * @param task			The task to add to this Taskseries.
	 */
	void addTask(Task task){
		if(_tasks == null){
			_tasks = new Vector<Task>();
		}
		
		_tasks.add(task);
	}
	
	/**
	 * Adds a user to this Taskseries.
	 *
	 * @param user			The user to add to this Taskseries.
	 */
	void addParticipant(User user){
		if(_participants == null){
			_participants = new Vector<User>();
		}
		
		_participants.add(user);
	}
}
