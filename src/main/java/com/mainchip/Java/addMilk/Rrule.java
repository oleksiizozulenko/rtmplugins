/*
 * Rrule.java
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

import com.mainchip.Java.addMilk.RTM.Freq;

/**
 * An wrapper containing the information about an RTM Recurrence Rule.
 * 
 * @author Dustin Stroup
 */
public class Rrule {
	private boolean _recType;
	private Freq _recFreq;
	private int _interval;
	
	/**
	 * @param recType	The recurrence type of this rule.	
	 * @param recFreq	The recurrence frequency of this rule.
	 * @param interval	The recurrence interval of this rule.
	 */
	public Rrule(boolean recType, Freq recFreq, int interval) {
		_recType = recType;
		_recFreq = recFreq;
		_interval = interval;
	}
	
	/**
	 * @param recType	The recurrence type of this rule.
	 */
	public Rrule(String recType) {
		if(recType.equals("0")){
			_recType = false;
		}else {
			_recType = true;
		}
	}
	
	/**
	 * Returns the recurrence type.
	 * 
	 * @return 			The recurrence type of this rule.  False if the rule 
	 * 					repeats always, True if the rule 'every'.
	 */
	public boolean getRecType() {
		return _recType;
	}
	
	/**
	 * Returns the recurrence frequency.
	 * 
	 * @return 			The recurrence frequency of this rule.
	 */
	public Freq getRecFreq() {
		return _recFreq;
	}
	
	/**
	 * Sets the recurrence frequency.
	 * 
	 * @param recFreq 	The recurrence frequency to set.
	 */
	public void setRecFreq(Freq recFreq) {
		_recFreq = recFreq;
	}
	
	/**
	 * Returns the interal of recurrence.
	 * 
	 * @return 			The interval of recurrence.
	 */
	public int getInterval() {
		return _interval;
	}
	
	/**
	 * Sets the interval of recurrence.
	 * 
	 * @param interval	The interval to set.
	 */
	public void setInterval(int interval) {
		_interval = interval;
	}
}
