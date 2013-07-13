/*
 * ResponseHandler.java
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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX Handler for parsing responses to various methods of the Remember the Milk API.
 * 
 * @author Dustin Stroup
 */
public class ResponseHandler extends DefaultHandler{
	private String _timeline, _frob;
	private Vector<Location> _locations;
	private Settings _settings;
	private Time _time;
	private Vector<Timezone> _timezones;
	private MilkError _error = null;
	private StringBuilder _builder;
	
	/**
	 * Returns a string containing a timeline.
	 * 
	 * @return			A string containing a timeline, null if none was recieved from RTM.
	 */
	String getTimeline() {
		return _timeline;
	}
	
	
	/**
	 * Returns a string containing an authentication frob.
	 * 
	 * @return			A string containing an authentication frob, null if none was recieved from RTM.
	 */
	String getFrob(){
		return _frob;
	}
	
	/**
	 * Returns a Vector of Locations if the RTM response contained any.
	 * 
	 * @return			A Vector of Locations if the RTM response contained any, otherwise null.
	 */
	Vector<Location> getLocations(){
		return _locations;
	}

	/**
	 * Returns a Settings object if the RTM response contained one.
	 * 
	 * @return			A Settings object if the RTM response contained one, otherwise null.
	 */
	Settings getSettings(){
		return _settings;
	}
	
	/**
	 * Returns a Vector of Timezones if the RTM response contained any.
	 * 
	 * @return			A Vector of Timezones if the RTM response contained any, otherwise null.
	 */
	Vector<Timezone> getTimezones(){
		return _timezones;
	}

	/**
	 * Returns a Time object if the RTM response contained one.
	 * 
	 * @return			A Time object if the RTM response contained one, otherwise null.
	 */
	Time getTime(){
		return _time;
	}
	
	/**
	 * Returns the error code and message provided by RTM, if any.
	 * 
	 * @return		If an error was reported by RTM, this returns a MilkError object with the details.  Otherwise, returns null.
	 */
	MilkError getError(){
		return _error;
	}
	
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        _builder.append(ch, start, length);
    }

	
	@Override
	public void startDocument(){
		_builder = new StringBuilder();
	}
	
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        
        if (name.equals("err")){
        	_error = new MilkError(attributes.getValue("code"), attributes.getValue("msg"));
        }else if(name.equals("location")){
        	_locations.add(new Location(attributes.getValue("id"), attributes.getValue("name"), attributes.getValue("longitude"), attributes.getValue("latitude"),
        			attributes.getValue("zoom"), attributes.getValue("address"), attributes.getValue("viewable")));
        }else if(name.equals("locations")){
        	_locations = new Vector<Location>();
        }else if(name.equals("settings")){
        	_settings = new Settings();
        }else if(name.equals("timezone") && _settings == null){
        	_timezones.add(new Timezone(attributes.getValue("id"), attributes.getValue("name"), attributes.getValue("dst"), attributes.getValue("offset"),
        			attributes.getValue("current_offset")));
        }else if(name.equals("timezones")){
        	_timezones = new Vector<Timezone>();
        }
    }
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException{
		super.endElement(uri, localName, name);
		
		if(name.equals("timeline")){
			_timeline = _builder.toString();
		}else if(name.equals("frob")){
			_frob = _builder.toString();
		}else if(name.equals("timezone") && _settings != null){
			_settings.setTimezone(_builder.toString());
		}else if(name.equals("dateformat")){
			_settings.setDateFormat(_builder.toString());
		}else if(name.equals("timeformat")){
			_settings.setTimeFormat(_builder.toString());
		}else if(name.equals("defaultlist")){
			_settings.setDefaultList(_builder.toString());
		}
		
		_builder.setLength(0);
	}
}
