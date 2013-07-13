/*
 * Note.java
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
 * An wrapper containing the information about an RTM Note.
 * 
 * @author Dustin Stroup
 */
public class Note {
	private String _id, _created, _modified, _title, _content;

	/**
	 * @param id		The ID associated with this note.
	 * @param created	The timestamp from this notes creation.
	 * @param modified	The timestamp when this note was last modified.
	 * @param title		The title of this note.
	 */
	public Note(String id, String created, String modified, String title) {
		_id = id;
		_created = created;
		_modified = modified;
		_title = title;
	}

	/**
	 * @param id		The ID associated with this note.
	 * @param created	The timestamp from this notes creation.
	 * @param modified	The timestamp when this note was last modified.
	 * @param title		The title of this note.
	 * @param content	The body of this note.
	 */
	public Note(String id, String created, String modified, String title,
			String content) {
		_id = id;
		_created = created;
		_modified = modified;
		_title = title;
		_content = content;
	}

	/**
	 * Returns the ID associated with this Note.
	 * 
	 * @return 			The ID of this note.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Returns the timestamp from this notes creation.
	 * 
	 * @return 			The timestamp from this notes creation.
	 */
	public String getCreated() {
		return _created;
	}

	/**
	 * Returns the timestamp this note was last modified.
	 * 
	 * @return 				The timestamp from this notes last modification.
	 */
	public String getModified() {
		return _modified;
	}

	/**
	 * Returns the title of this note.
	 * 
	 * @return 				The title of this note.
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Returns the body of this note.
	 * 
	 * @return 				The body of this note.
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Sets the body of the note.
	 * 
	 * @param 				The text to set as the body of the note.
	 */
	void setContent(String content) {
		_content = content;
	}
}
