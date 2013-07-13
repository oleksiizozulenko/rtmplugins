/*
 * MilkException.java
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
 * A general API error.
 * 
 * @author Dustin Stroup
 */
public class MilkException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4536538743313930521L;

	public MilkException(String message){
		super(message);
	}
	
	public MilkException(String message, Throwable cause){
		super(message, cause);
	}
}
