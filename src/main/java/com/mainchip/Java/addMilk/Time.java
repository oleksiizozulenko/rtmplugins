/*
 * Time.java
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
 * A wrapper containing the information used in RTM Time.
 * @author Dustin Stroup
 */
public class Time {
	private String _precision, _time, _timezone;
	
	public Time(){
	}
	
	/**
	 * @param precision		The precision of this timestamp.
	 * @param timezone		The timezone for this timestamp.
	 */
	public Time(String precision, String timezone){
		_precision = precision;
		_timezone = timezone;
	}

	/**
	 * Indicates the precision of this timestamp.
	 *
	 * @return				The precision of this timestamp ("time" or "date").
	 */
	public String getPrecision() {
		return _precision;
	}

	/**
	 * Returns the timestamp.
	 *
	 * @return				The timestamp.
	 */
	public String getTime() {
		return _time;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param time			The timestamp to set.
	 */
	void setTime(String time) {
		_time = time;
	}

	/**
	 * Returns the timezone for this timestamp.
	 *
	 * @return				The timezone for this timestamp.
	 */
	public String getTimezone() {
		return _timezone;
	}
}
