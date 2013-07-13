/*
 * UserHandler.java
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
 * A SAX Handler for parsing responses to User/Contact methods of the Remember the Milk API.
 * 
 * @author Dustin Stroup
 */
public class UserHandler extends DefaultHandler{
	private StringBuilder _builder;
	private Vector<User> _users;
	private Transaction _trans;
	private MilkError _error;

	/**
	 * Returns a list of users.
	 *
	 * @return		A list of users.
	 */
	Vector<User> getUsers(){
		if(_users != null && !_users.isEmpty()){
			_users.firstElement().setTransaction(_trans);
		}
		
		return _users;
	}
	
	/**
	 * Returns a single user.
	 *
	 * @return		A single user.
	 */
	User getUser(){
		if(_users != null && !_users.isEmpty()){
			_users.firstElement().setTransaction(_trans);
		}
		
		return _users.firstElement();
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
		_users = new Vector<User>();
	}


	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (name.equals("err")){
			_error = new MilkError(attributes.getValue("code"), attributes.getValue("msg"));
		}else if(name.equals("contact")){
        	_users.add(new User(attributes.getValue("id"), attributes.getValue("username"), attributes.getValue("fullname")));
        }else if(name.equals("transaction")){
        	_trans = new Transaction(attributes.getValue("id"), attributes.getValue("undoable"));
        }else if(name.equals("user")){
        	_users.add(new User(attributes.getValue("id")));
        }
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException{
		super.endElement(uri, localName, name);

		if(name.equals("username")){
			_users.lastElement().setUsername(_builder.toString());
		}
		
		_builder.setLength(0);
	}
}