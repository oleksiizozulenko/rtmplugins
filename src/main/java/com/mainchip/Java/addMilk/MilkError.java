/*
 * MilkError.java
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
 * A wrapper class for errors returned by the RTM API.
 * 
 * @author Dustin Stroup
 */
public class MilkError {
	private String _code, _msg;
	
	/**
	 * @param code		The error code provided by RTM.
	 * @param message	The error message provided by RTM.
	 */
	public MilkError(String code, String message){
		_code = code;
		_msg = message;
	}
	
	/**
	 * Returns the error code provided by RTM.
	 * 
	 * @return			The error code provided by RTM.
	 */
	public String getCode() {
		return _code;
	}
	
	/**
	 * Returns the error message provided by RTM.
	 * 
	 * @return			The error message provided by RTM.
	 */
	public String getMsg() {
		return _msg;
	}
}
