/*
 * GroupHandler.java
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
 * A SAX Handler for parsing responses to Group methods of the Remember the Milk API.
 * 
 * @author Dustin Stroup
 */
public class GroupHandler extends DefaultHandler{
	private StringBuilder _builder;
	private Vector<User> _users;
	private Vector<Group> _groups;
	private Transaction _trans;
	private MilkError _error;
	
	/**
	 * Returns a list of groups.
	 * @return		A list of groups, null if no groups exist or an error occurred.
	 */
	Vector<Group> getGroups(){
		if(_groups != null && !_groups.isEmpty()){
			_groups.firstElement().setTransaction(_trans);
		}
		return _groups;
	}
	
	/**
	 * Returns a single group.
	 * 
	 * @return		A single group, null if no groups exist or an error occurred.
	 */
	Group getGroup(){
		Group retVal = null;
		
		if(_groups != null && !_groups.isEmpty()){
			_groups.firstElement().setTransaction(_trans);	
			retVal = _groups.firstElement();
		}
			
		return retVal;
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
		_groups = new Vector<Group>();
	}
	
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        
        if(name.equals("contact")){
        	_users.add(new User(attributes.getValue("id"), attributes.getValue("username"), attributes.getValue("fullname")));
        }else if(name.equals("group")){
        	_users = new Vector<User>();
        	
        	_groups.add(new Group(attributes.getValue("id"), attributes.getValue("name")));
        }else if(name.equals("transaction")){
        	_trans = new Transaction(attributes.getValue("id"), attributes.getValue("undoable"));
        }else if (name.equals("err")){
			_error = new MilkError(attributes.getValue("code"), attributes.getValue("msg"));
		}
    }
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException{
		super.endElement(uri, localName, name);

		if(name.equals("group")){
			_groups.lastElement().setUsers(_users);
		}
		
		_builder.setLength(0);
	}
}
