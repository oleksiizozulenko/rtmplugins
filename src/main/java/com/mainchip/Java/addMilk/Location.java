/*
 * Location.java
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
 *  An wrapper containing the information about an RTM Location.
 * 
 * @author Dustin Stroup
 */
public class Location {
	private String _id, _name, _longitude, _latitude, _zoom, _address, _viewable;
	
	/**
	 * 
	 * 
	 * @param id		The ID associated with this location.
	 * @param name		The name of this location.
	 * @param longitude	The longitude of this location.
	 * @param latitude	The latitude of this location.
	 * @param zoom		The zoom of this location (in Google Maps).
	 * @param address	The address of this location.
	 * @param viewable	A parameter supplied by RTM.  //TODO: What is 'viewable' used for in locations?
	 */
	public Location(String id, String name, String longitude, String latitude, String zoom, String address, String viewable){
		_id = id;
		_name = name;
		_longitude = longitude;
		_latitude = latitude;
		_zoom = zoom;
		_address = address;
		_viewable = viewable;
	}

	/**
	 * Returns the ID associated with this location
	 * 
	 * @return			The ID associated with this location.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Returns the name of this location.
	 * 
	 * @return			The name of this location.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the longitude of this location.
	 * 
	 * @return			The longitude of this location.
	 */
	public String getLongitude() {
		return _longitude;
	}

	/**
	 * Returns the latitude of this location.
	 * 
	 * @return			The latitude of this location.
	 */
	public String getLatitude() {
		return _latitude;
	}

	/**
	 * Returns the zoom (in Google Maps) of this location.
	 * 
	 * @return			The zoom (in Google Maps) of this location.
	 */
	public String getZoom() {
		return _zoom;
	}

	/**
	 * Returns the address of this location.
	 * 
	 * @return			The address of this location.
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * Returns the viewable parameter supplied by RTM.
	 * 
	 * @return			The viewable parameter supplied by RTM.
	 */
	public String getViewable() {
		return _viewable;
	}
}
