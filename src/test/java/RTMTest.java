/*
 * RTMTest.java - A JUnit test suite designed to test Java.addMilk.
 * Copyright (C) 2010  Dustin Stroup
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.mainchip.Java.addMilk.Group;
import com.mainchip.Java.addMilk.Location;
import com.mainchip.Java.addMilk.MilkException;
import com.mainchip.Java.addMilk.Note;
import com.mainchip.Java.addMilk.RTM;
import com.mainchip.Java.addMilk.Settings;
import com.mainchip.Java.addMilk.TaskList;
import com.mainchip.Java.addMilk.Time;
import com.mainchip.Java.addMilk.Timezone;
import com.mainchip.Java.addMilk.Transaction;
import com.mainchip.Java.addMilk.User;

public class RTMTest {
	private RTM rtm;
	private String timeline, contactID;
	private static String groupID, listID, seriesID, taskID, transID, noteID;

	@Before
	public void setUp() throws Exception {
		rtm = new RTM("API KEY", "SHARED SECRET", "TOKEN");
		timeline = "TIMELINE";
		contactID = "USER ID";
	}
	
	/**
	 * This test exists only to print out an authentication URL to allow further testing.
	 * Because it performs no actual tests, it has been commented.  
	 */
//	@Test
//	public void testGenAuthURL(){
//		try {
//			System.out.println(rtm.genAuthURL(rtm.getFrob(), "delete"));
//		} catch (MilkException e) {
//			fail("exception");
//			return;
//		}
//	}

	@Test
	public void testAddContact() {
		User user;
		try {
			user = rtm.addContact(timeline, "mainchip");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(user != null);
		assertTrue(user.getUsername().equals("mainchip"));
		assertTrue(user.getID().equals(contactID));
	}

	@Test
	public void testGetListOfContacts() {
		Vector<User> user;
		try {
			user = rtm.getListOfContacts();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(user.size() == 1);
		assertTrue(user.firstElement().getUsername().equals("mainchip"));
		assertTrue(user.firstElement().getID().equals(contactID));
	}

	@Test
	public void testAddGroup() {
		Group group;
		try {
			group = rtm.addGroup(timeline, "TestGroup");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(group != null);

		groupID = group.getID();

		assertTrue(group.getName().equals("TestGroup"));
	}

	@Test
	public void testAddContactGroup() {
		Transaction test;
		try {
			test = rtm.addContactGroup(timeline, groupID, contactID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test != null);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testGetListOfGroups() {
		Vector<Group> groups;
		try {
			groups = rtm.getListOfGroups();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(groups != null);
		assertFalse(rtm.errorSet());

		assertTrue(groups.firstElement().getName().equals("TestGroup"));
		assertTrue(groups.size() == 1);
	}

	@Test
	public void testRemoveContactGroup() {
		Transaction test;
		try {
			test = rtm.removeContactGroup(timeline, groupID, contactID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test != null);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testAddTaskList() {
		TaskList list;
		try {
			list = rtm.addTaskList(timeline, "TestList", "");
			// TODO : test filters
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(rtm.errorSet());

		listID = list.getID();
		assertTrue(list.getName().equals("TestList"));
		assertFalse(list.isArchived());
		assertFalse(list.isDeleted());
		assertFalse(list.isLocked());
		assertFalse(list.isSmart());
	}

	@Test
	public void testArchiveTaskList() {
		TaskList list;
		try {
			list = rtm.archiveTaskList(timeline, listID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(rtm.errorSet());

		assertTrue(list.getName().equals("TestList"));
		assertTrue(list.isArchived());
	}

	@Test
	public void testGetListOfTaskLists() {
		Vector<TaskList> lists;
		try {
			lists = rtm.getListOfTaskLists();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(lists != null);
		assertFalse(rtm.errorSet());
		assertTrue(lists.size() == 7);
	}

	@Test
	public void testSetDefaultTaskList() {
		Transaction test;
		try {
			test = rtm.setDefaultTaskList(timeline, "12160568");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test != null);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testSetNameOfTaskList() {
		TaskList list;
		try {
			list = rtm.setNameOfTaskList(timeline, listID, "RenamedList");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(rtm.errorSet());

		assertTrue(list.getName().equals("RenamedList"));
		assertTrue(list.getID().equals(listID));
	}

	@Test
	public void testUnarchiveTaskList() {
		TaskList list;
		try {
			list = rtm.unarchiveTaskList(timeline, listID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(rtm.errorSet());

		assertTrue(list.getName().equals("RenamedList"));
		assertFalse(list.isArchived());
	}

	@Test
	public void testGetLocations() {
		Vector<Location> locs;
		try {
			locs = rtm.getLocations();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(locs != null);
		assertFalse(rtm.errorSet());

		assertTrue(locs.size() == 1);
		assertTrue(locs.firstElement().getName().equals("Home"));

	}

	@Test
	public void testGetSettings() {
		Settings settings;
		try {
			settings = rtm.getSettings();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(settings != null);
		assertFalse(rtm.errorSet());

		assertTrue(settings.getDefaultList().equals("12160568"));
		assertTrue(settings.getTimezone().equals("America/New_York"));
		assertTrue(settings.getLanguage() == null);
		assertTrue(settings.getDateFormat().equals("1"));
		assertTrue(settings.getTimeFormat().equals("0"));
	}

	@Test
	public void testAddTask() {
		TaskList list;
		try {
			list = rtm.addTask(timeline, listID, "Task1", "");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries() != null);
		assertTrue(list.getSeries().firstElement() != null);

		assertTrue(list.getSeries().firstElement().getName().equals("Task1"));

		seriesID = list.getSeries().firstElement().getID();
		taskID = list.getSeries().firstElement().getTasks().firstElement().getID();
	}

	@Test
	public void testAddTags() {
		TaskList list;
		try {
			list = rtm.addTags(timeline, listID, seriesID, taskID, "testtag,tag2");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);

		assertTrue(list.getSeries().firstElement().getTags().size() == 2);
		assertTrue(list.getSeries().firstElement().getTags().get(1).equals("testtag"));
		assertTrue(list.getSeries().firstElement().getTags().get(0).equals("tag2"));
	}

	@Test
	public void testCompleteTask() {
		TaskList list;
		try {
			list = rtm.completeTask(timeline, listID, seriesID, taskID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(list.getSeries().firstElement().getTasks().firstElement().getCompleted().equals(""));
	}

	@Test
	public void testGetTaskList() {
		Vector<TaskList> lists;

		try {
			lists = rtm.getTaskList(listID, "", "");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(lists != null);
		assertTrue(lists.size() == 1);
		assertTrue(lists.firstElement().getID().equals(listID));
	}

	@Test
	public void testMoveTaskPriority() {
		TaskList list;
		try {
			list = rtm.moveTaskPriority(timeline, listID, seriesID, taskID, "up");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getPriority().equals("3"));
	}

	@Test
	public void testMoveTaskTo() {
		TaskList list;
		try {
			list = rtm.moveTaskTo(timeline, seriesID, taskID, listID, "12160568");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getID().equals("12160568"));
		assertTrue(list.getSeries().firstElement().getName().equals("Task1"));

		try {
			list = rtm.moveTaskTo(timeline, seriesID, taskID, "12160568", listID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getID().equals(listID));
		assertTrue(list.getSeries().firstElement().getName().equals("Task1"));
	}

	@Test
	public void testPostpone() {
		TaskList list;
		try {
			list = rtm.postpone(timeline, listID, seriesID, taskID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getPostponed().equals("1"));
	}

	@Test
	public void testRemoveTags() {
		TaskList list;
		try {
			list = rtm.removeTags(timeline, listID, seriesID, taskID, "testtag");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTags().size() == 1);
		assertTrue(list.getSeries().firstElement().getTags().firstElement().equals("tag2"));
	}

	@Test
	public void testSetDueDate() {
		TaskList list;
		try {
			list = rtm.setDueDate(timeline, listID, seriesID, taskID, "today", "", "1");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getDue().equals("2010-07-22T04:00:00Z"));
	}

	@Test
	public void testSetEstimate() {
		TaskList list;
		try {
			list = rtm.setEstimate(timeline, listID, seriesID, taskID, "1hour");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getEstimate().equals("1hour"));
	}

	@Test
	public void testSetLocation() {
		TaskList list;
		try {
			list = rtm.setLocation(timeline, listID, seriesID, taskID, "676088");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getLocationID().equals("676088"));
		
		list = null;
		
		try{
			list = rtm.setLocation(timeline, listID, seriesID, taskID, "");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getLocationID().equals(""));
	}

	@Test
	public void testSetName() {
		TaskList list;
		try {
			list = rtm.setName(timeline, listID, seriesID, taskID, "RenamedTask");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getName().equals("RenamedTask"));
	}

	@Test
	public void testSetPriority() {
		TaskList list;
		try {
			list = rtm.setPriority(timeline, listID, seriesID, taskID, "1");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getPriority().equals("1"));
	}

	@Test
	public void testSetRecurrence() {
		TaskList list;
		try {
			list = rtm.setRecurrence(timeline, listID, seriesID, taskID, "every day");
			//URLEncoder.encode("every day", "UTF-8"));
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getRrule().getInterval() == 1);
		assertTrue(list.getSeries().firstElement().getRrule().getRecType());
		assertTrue(list.getSeries().firstElement().getRrule().getRecFreq() == RTM.Freq.DAILY);
	}

	@Test
	public void testSetTags() {
		TaskList list;
		try {
			list = rtm.setTags(timeline, listID, seriesID, taskID, "tag3,tag4,tag5");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTags().size() == 3);
		assertTrue(list.getSeries().firstElement().getTags().get(2).equals("tag5"));
		assertTrue(list.getSeries().firstElement().getTags().get(1).equals("tag4"));
		assertTrue(list.getSeries().firstElement().getTags().get(0).equals("tag3"));
	}

	@Test
	public void testSetURL() {
		TaskList list;
		try {
			list = rtm.setURL(timeline, listID, seriesID, taskID, "http://www.google.com");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getUrl().equals("http://www.google.com"));
	}

	@Test
	public void testUncomplete() {
		TaskList list;
		try {
			list = rtm.uncomplete(timeline, listID, seriesID, taskID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertTrue(list.getSeries().firstElement().getTasks().firstElement().getCompleted().equals(""));

		assertTrue(list.getTransaction() != null);
		transID = list.getTransaction().getID();
	}

	@Test
	public void testAddNote() {
		Note note;

		try {
			note = rtm.addNote(timeline, listID, seriesID, taskID, "test note", "notebody");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(note != null);

		noteID = note.getID();

		assertTrue(note.getTitle().equals("test note"));
		assertTrue(note.getContent().equals("notebody"));
	}

	@Test
	public void testEditNote() {
		Note note;
		try {
			note = rtm.editNote(timeline, noteID, "testnote", "newbody");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(note != null);
		assertTrue(note.getTitle().equals("testnote"));
		assertFalse(note.getContent().equals("notebody"));
		assertTrue(note.getContent().equals("newbody"));
	}

	@Test
	public void testEcho() {
		HashMap<String, String> string = new HashMap<String, String>();
		HashMap<String, String> string2;
		string.put("test", "pair");

		try {
			string2 = rtm.echo(string);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(string2 != null);
		assertTrue(string2.containsKey("test"));
		assertTrue(string2.containsValue("pair"));
		assertTrue(string2.get("test").equals("pair"));
	}

	@Test
	public void testTestLogin() {
		User user;
		try {
			user = rtm.testLogin();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(user != null);
		assertTrue(user.getUsername().equalsIgnoreCase("talagor"));
	}

	@Test
	public void testConvertTime() {
		//TODO: More versatile test for convertTime()
		Time time;
		try {
			time = rtm.convertTime("Asia/Hong_Kong", "", "midnight");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(time != null);
		assertFalse(rtm.errorSet());

		assertTrue(time.getTime().equals("2010-07-22T00:00:00"));
		assertTrue(time.getTimezone().equals("Asia/Hong_Kong"));
	}

	@Test
	public void testParseTime() {
		//TODO: More versatile test for parseTime()
		Time time;
		try {
			time = rtm.parseTime("7/20/2010", "", "");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(time != null);
		assertFalse(rtm.errorSet());

		assertTrue(time.getPrecision().equals("date"));
		assertTrue(time.getTime().equals("2010-07-20T00:00:00Z"));
	}

	@Test
	public void testCreateTimeline() {
		String test;
		try {
			test = rtm.createTimeline();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertFalse(test.isEmpty());
	}

	@Test
	public void testGetListOfTimezones() {
		Vector<Timezone> tz;
		try {
			tz = rtm.getListOfTimezones();
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(tz != null);
		// TODO: add more assertions to timezones.getList
	}

	@Test
	public void testUndo() {
		boolean test;
		try {
			test = rtm.undo(timeline, transID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test);
	}


	@Test
	public void testDeleteContact() {
		Transaction test;
		try {
			test = rtm.deleteContact(timeline, "461789");
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test != null);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testDeleteGroup() {
		Transaction test;
		try {
			test = rtm.deleteGroup(timeline, groupID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test != null);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testDeleteNote() {
		boolean test;
		try {
			test = rtm.deleteNote(timeline, noteID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(test);
		assertFalse(rtm.errorSet());
	}

	@Test
	public void testDeleteTask() {
		TaskList list;
		try {
			list = rtm.deleteTask(timeline, listID, seriesID, taskID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(list.getSeries().firstElement().getTasks().firstElement().getDeleted().equals(""));

	}

	@Test
	public void testDeleteTaskList() {
		TaskList list;
		try {
			list = rtm.deleteTaskList(timeline, listID);
		} catch (MilkException e) {
			fail("exception");
			return;
		}

		assertTrue(list != null);
		assertFalse(rtm.errorSet());

		assertTrue(list.getName().equals("RenamedList"));
		assertTrue(list.isDeleted());
	}

}
