/*
 * EchoHandler.java
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

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX Handler for parsing responses to rtm.test.echo from the Remember the Milk web API.
 * 
 * @author Dustin Stroup
 */
public class EchoHandler extends DefaultHandler{
	private StringBuilder _builder;
	private HashMap<String, String> _echo;
	private MilkError _error;

	/**
	 * A testing method which echos all parameters back in the response.
	 * 
	 * @return		a HashMap containing the list of parameters and values supplied, including the API key and method being called.
	 */
	HashMap<String, String> getParams() {
		return _echo;
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
		_echo = new HashMap<String, String>();
	}


	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (name.equals("err")){
			_error = new MilkError(attributes.getValue("code"), attributes.getValue("msg"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException{
		super.endElement(uri, localName, name);

		if(!name.equals("rsp")){
			_echo.put(name, _builder.toString());
		}
		
		_builder.setLength(0);
	}
}
