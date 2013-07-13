/*
 * Timezone.java
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
 * A wrapper containing information about an RTM Timezone.
 * 
 * @author Dustin Stroup
 */
public class Timezone {
	private String _id, _name, _dst, _offset, _currentOffset;
	
	/**
	 * @param id			The ID associated with this timezone.
	 * @param name			The name of this timezone.
	 * @param dst			Whether or not DST is in effect in this timezone.
	 * @param offset		The offset from UTC of this timezone.
	 * @param currentOffset	The current offset from UTC of this timezone.
	 */
	public Timezone(String id, String name, String dst, String offset, String currentOffset){
		_id = id;
		_name = name;
		_dst = dst;
		_offset = offset;
		_currentOffset = currentOffset;
	}

	/**
	 * Returns the ID associated with this timezone.
	 *
	 * @return				The ID associated with this timezone.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Returns the name of this timezone.
	 *
	 * @return				The name of this Timezone.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Indicates whether Daylist Savings Time is in effect for this timezone.
	 *
	 * @return				1 if DST is in effect for this timezone, otherwise 0.
	 */
	public String getDst() {
		return _dst;
	}

	/**
	 * Returns the offset from UTC of this timezone.
	 *
	 * @return				The offset from UTC of this timezone.
	 */
	public String getOffset() {
		return _offset;
	}

	/**
	 * Returns the current offset from UTC of this timezone.
	 *
	 * @return				The current offset from UTC of this timezone.
	 */
	public String getCurrentOffset() {
		return _currentOffset;
	}
}
