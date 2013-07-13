/*
 * ListHandler.java
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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Vector;

/**
 * A SAX Handler for parsing responses to Lists methods of the Remember the Milk API.
 *
 * @author Dustin Stroup
 */
public class ListHandler extends DefaultHandler {
	private StringBuilder _builder;
	private Transaction _trans;
	private Vector<User> _users;
	private Vector<Taskseries> _series;
	private Vector<TaskList> _taskLists;
	private Note _note;
	private Rrule _rrule;
	private MilkError _error;

	/**
	 * Returns a single TaskList.
	 *
	 * @return A single TaskList, null if no lists exist or an error occurred.
	 */
	TaskList getTaskList() {
		if (_trans != null) {
			_taskLists.firstElement().setTransaction(_trans);
		}

		return _taskLists.firstElement();
	}

	/**
	 * Returns a vector of TaskLists.
	 *
	 * @return A vector of TaskLists, null if no lists exist or an error occurred.
	 */
	Vector<TaskList> getTaskLists() {
		if (_trans != null) {
			_taskLists.firstElement().setTransaction(_trans);
		}

		return _taskLists;
	}

	/**
	 * Returns the error code and message provided by RTM, if any.
	 *
	 * @return If an error was reported by RTM, this returns a MilkError object with the details.  Otherwise, returns null.
	 */
	MilkError getError() {
		return _error;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		_builder.append(ch, start, length);
	}


	@Override
	public void startDocument() {
		_builder = new StringBuilder();
	}


	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (name.equals("err")) {
			_error = new MilkError(attributes.getValue("code"), attributes.getValue("msg"));
		} else if (name.equals("transaction")) {
			_trans = new Transaction(attributes.getValue("id"), attributes.getValue("undoable"));
		} else if (name.equals("contact")) {
			if (_users == null) {
				_users = new Vector<User>();
			}

			_users.add(new User(attributes.getValue("id"), attributes.getValue("username"), attributes.getValue("fullname")));
		} else if (name.equals("list")) {
			if (_taskLists == null) {
				_taskLists = new Vector<TaskList>();
			}

			if (attributes.getLength() < 7) {
				_taskLists.add(new TaskList(attributes.getValue("id")));
			} else {
				_taskLists.add(new TaskList(attributes.getValue("id"), attributes.getValue("name"), attributes.getValue("position"), attributes.getValue("smart"),
						attributes.getValue("deleted"), attributes.getValue("locked"), attributes.getValue("archived")));
			}
		} else if (name.equals("rrule")) {
			_rrule = new Rrule(attributes.getValue("every"));
		} else if (name.equals("taskseries")) {
			if (_series == null) {
				_series = new Vector<Taskseries>();
			}

			_series.add(new Taskseries(attributes.getValue("id"), attributes.getValue("created"), attributes.getValue("modified"),
					attributes.getValue("name"), attributes.getValue("source"), attributes.getValue("url"), attributes.getValue("location_id")));
		} else if (name.equals("note")) {
			_note = new Note(attributes.getValue("id"), attributes.getValue("created"), attributes.getValue("modified"), attributes.getValue("title"));
		} else if (name.equals("task")) {
			_series.lastElement().addTask(new Task(attributes.getValue("id"), attributes.getValue("due"), attributes.getValue("has_due_time"),
					attributes.getValue("added"), attributes.getValue("completed"), attributes.getValue("deleted"), attributes.getValue("priority"),
					attributes.getValue("postponed"), attributes.getValue("estimate")));
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (name.equals("participants")) {
			// add _users to current tasklist
		} else if (name.equals("list")) {
			_taskLists.lastElement().setSeries(_series);
		} else if (name.equals("rrule")) {
			Properties p = new Properties();
			try {
				p.load(new StringReader(_builder.toString().replace(";", "\n")));
			} catch (IOException e) {
				throw new SAXException(e);
			}

			_rrule.setInterval(Integer.valueOf(p.getProperty("INTERVAL")));
			String freqProp = p.getProperty("FREQ");
			if (freqProp.equalsIgnoreCase("daily")) {
				_rrule.setRecFreq(RTM.Freq.DAILY);
			} else if (freqProp.equalsIgnoreCase("montly")) {
				_rrule.setRecFreq(RTM.Freq.MONTHLY);
			} else if (freqProp.equalsIgnoreCase("weekly")) {
				_rrule.setRecFreq(RTM.Freq.WEEKLY);
			} else if (freqProp.equalsIgnoreCase("yearly")) {
				_rrule.setRecFreq(RTM.Freq.YEARLY);
			}

			_series.lastElement().setRrule(_rrule);
		} else if (name.equals("note")) {
			_note.setContent(_builder.toString());

			if (_series != null) {
				_series.lastElement().addNote(_note);
			} else {
				throw new SAXException("Fatal error: malformed response");
			}
		} else if (name.equals("tag")) {
			_series.lastElement().addTag(_builder.toString());
		} else if (name.equals("filter")) {
			_taskLists.lastElement().setFilter(_builder.toString());
		}

		_builder.setLength(0);
	}
}
