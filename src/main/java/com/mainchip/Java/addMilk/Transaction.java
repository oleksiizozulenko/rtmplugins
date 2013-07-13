/*
 * Transaction.java
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

public class Transaction {
	private String _id;
	private boolean _undoable;
	
	/**
	 * @param id			The ID associated with this Transaction.
	 * @param undoable		Whether or not the action associated with this Transaction can be undone.
	 */
	public Transaction(String id, String undoable){
		_id = id;
		
		if(undoable == null){
			_undoable = false;
		}else {
			_undoable = (undoable.equals("1"));
		}
	}

	/**
	 * Returns the ID associated with this Transaction.
	 *
	 * @return				The ID associated with this Transaction.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Indicates whether or not the API call associated with this Transaction can be undone.
	 *
	 * @return				True if the action associated with this Transaction can be undone, false otherwise.
	 */
	public boolean isUndoable() {
		return _undoable;
	}
}
