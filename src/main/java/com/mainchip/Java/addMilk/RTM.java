/*
 * RTM.java
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

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;


/**
 * An object used to pass calls to the Remember the Milk API
 *
 * @author Dustin Stroup
 */
public class RTM {
	public static final String AUTH_URL = "http://www.rememberthemilk.com/services/auth/";
	public static final String API_URL = "http://www.rememberthemilk.com/services/rest/";
	public static final String INVALID_TYPE = "Unrecoverable Java.addMilk Error";
	public static final String API_ERROR = "Unable to complete API call.";
	public static final String NO_KEYS = "No API Key or Secret set.";
	public static final String BAD_JSON = "Unexpected JSON recieved.";
	public static final String HASHING_ERROR = "Error parsing signature.";
	public static final String NO_TOKEN = "No token set";


	private static final String API_KEY = "api_key=";
	private static final String API_SIG = "&api_sig=";
	private static final String AUTH_TOKEN = "&auth_token=";
	private static final String TIMELINE = "&timeline=";
	private static final String FROB = "&frob=";


	private static final String CHECK_TOKEN = "&method=rtm.auth.checkToken";
	private static final String GET_FROB = "&method=rtm.auth.getFrob";
	private static final String GET_TOKEN = "&method=rtm.auth.getToken";

	private static final String CREATE_TIMELINE = "&method=rtm.timelines.create";

	private static final String ADD_CONTACT = "&method=rtm.contacts.add";
	private static final String DELETE_CONTACT = "&method=rtm.contacts.delete";
	private static final String GETLIST_CONTACTS = "&method=rtm.contacts.getList";

	private static final String ADD_GROUP = "&method=rtm.groups.add";
	private static final String ADD_CONTACT_GROUP = "&method=rtm.groups.addContact";
	private static final String DELETE_GROUP = "&method=rtm.groups.delete";
	private static final String GETLIST_GROUPS = "&method=rtm.groups.getList";
	private static final String REMOVE_CONTACT_GROUP = "&method=rtm.groups.removeContact";

	private static final String ADD_TASKLIST = "&method=rtm.lists.add";
	private static final String ARCHIVE_TASKLIST = "&method=rtm.lists.archive";
	private static final String DELETE_TASKLIST = "&method=rtm.lists.delete";
	private static final String GETLIST_TASKLIST = "&method=rtm.lists.getList";
	private static final String SETDEFAULT_TASKLIST = "&method=rtm.lists.setDefaultList";
	private static final String SETNAME_TASKLIST = "&method=rtm.lists.setName";
	private static final String UNARCHIVE_TASKLIST = "&method=rtm.lists.unarchive";

	private static final String GET_LOCATIONS = "&method=rtm.locations.getList";
	private static final String GET_SETTINGS = "&method=rtm.settings.getList";
	private static final String UNDO = "&method=rtm.transactions.undo";
	private static final String GETLIST_TIMEZONES = "&method=rtm.timezones.getList";

	private static final String CONVERT_TIME = "&method=rtm.time.convert";
	private static final String PARSE_TIME = "&method=rtm.time.parse";

	private static final String TEST_ECHO = "&method=rtm.test.echo";
	private static final String TEST_LOGIN = "&method=rtm.test.login";

	private static final String ADD_TASK = "&method=rtm.tasks.add";
	private static final String COMPLETE_TASK = "&method=rtm.tasks.complete";
	private static final String ADD_TAGS = "&method=rtm.tasks.addTags";
	private static final String DELETE_TASK = "&method=rtm.tasks.delete";
	private static final String GET_TASKLIST = "&method=rtm.tasks.getList";
	private static final String MOVE_PRIORITY = "&method=rtm.tasks.movePriority";
	private static final String MOVE_TASK_TO = "&method=rtm.tasks.moveTo";
	private static final String POSTPONE = "&method=rtm.tasks.postpone";
	private static final String SET_DUE_DATE = "&method=rtm.tasks.setDueDate";
	private static final String REMOVE_TAGS = "&method=rtm.tasks.removeTags";
	private static final String SET_ESTIMATE = "&method=rtm.tasks.setEstimate";
	private static final String SET_LOCATION = "&method=rtm.tasks.setLocation";
	private static final String SET_NAME = "&method=rtm.tasks.setName";
	private static final String SET_PRIORITY = "&method=rtm.tasks.setPriority";
	private static final String SET_RECURRENCE = "&method=rtm.tasks.setRecurrence";
	private static final String SET_TAGS = "&method=rtm.tasks.setTags";
	private static final String SET_URL = "&method=rtm.tasks.setURL";
	private static final String UNCOMPLETE = "&method=rtm.tasks.uncomplete";

	private static final String ADD_NOTE = "&method=rtm.tasks.notes.add";
	private static final String DELETE_NOTE = "&method=rtm.tasks.notes.delete";
	private static final String EDIT_NOTE = "&method=rtm.tasks.notes.edit";


	private String _apiKey = null;
	private String _secret = null;
	private String _token = "";
	private MilkError _error = null;


	public enum Freq {
		DAILY, WEEKLY, MONTHLY, YEARLY
	}

	/**
	 * The default constructor.
	 */
	public RTM() {
	}

	/**
	 * @param apiKey A valid RTM API key.
	 * @param secret A valid RTM API shared secret.
	 */
	public RTM(String apiKey, String secret) {
		_apiKey = apiKey;
		_secret = secret;
	}

	/**
	 * @param apiKey A valid RTM API key.
	 * @param secret A valid RTM API shared secret.
	 * @param token  The token associated with the current user.
	 */
	public RTM(String apiKey, String secret, String token) {
		_apiKey = apiKey;
		_secret = secret;
		_token = token;
	}

	/**
	 * Register your key and shared secret with this object.  Required only when
	 * using the default constructor.
	 *
	 * @param apiKey A valid RTM API key.
	 * @param secret A valid RTM API shared secret.
	 */
	public void register(String apiKey, String secret) {
		_apiKey = apiKey;
		_secret = secret;
	}

	/**
	 * Register your key, shared secret and token with this object.
	 *
	 * @param apiKey A valid RTM API key.
	 * @param secret A valid RTM API shared secret.
	 * @param token  The token associated with the current user.
	 */
	public void register(String apiKey, String secret, String token) {
		_apiKey = apiKey;
		_secret = secret;
		_token = token;
	}

	/**
	 * Set the token to be used for all method calls.
	 *
	 * @param token The token associated with the current user.
	 */
	public void setToken(String token) {
		_token = token;
	}


	/**
	 * Returns the token currently in use.
	 *
	 * @return The token associated with the current user.
	 */
	public String getToken() {
		return _token;
	}

	/**
	 * Returns the API key used in communicating with RTM.
	 *
	 * @return The API key used in communicating with RTM.
	 */
	public String getKey() {
		return _apiKey;
	}

	/**
	 * Returns the shared secret used in communicating with RTM.
	 *
	 * @return The shared secret used in communicating with RTM.
	 */
	public String getSecret() {
		return _secret;
	}

	/**
	 * Returns and clears the latest error, if any.
	 *
	 * @return The latest error, or null if no errors have occured
	 *         since the last time getError() was called.
	 * @throws MilkException
	 */
	public MilkError getError() {
		MilkError retVal = _error;
		_error = null;
		return retVal;
	}

	/**
	 * Checks if an error has been reported by the RTM API.
	 *
	 * @return True if the RTM object contains an error, false if
	 *         no error has been reported by the RTM API since
	 *         getError() was last called.
	 */
	public boolean errorSet() {
		return (_error != null);
	}

	/**
	 * Generates a URL that allows a user to permit access to your API key.
	 *
	 * @param frob       A string generated by getFrob().
	 * @param permission The permission level being requested.  Can be read, write, or delete.
	 *                   See http://www.rememberthemilk.com/services/api/ for more information.
	 * @return An authentication URL.
	 * @throws MilkException
	 */
	public String genAuthURL(String frob, String permission) throws MilkException {
		String apiCall;
		checkKeys();

		apiCall = API_KEY + _apiKey + "&frob=" + frob + "&perms=" + permission;

		return AUTH_URL + "?" + apiCall.replace(' ', '+') + parseSig(apiCall);
	}

	/**
	 * An internal method to determine if the API key and shared secret have
	 * been set by the user.
	 *
	 * @throws MilkException
	 */
	private void checkKeys() throws MilkException {
		if (_apiKey == null || _apiKey.isEmpty() || _secret == null ||
				_secret.isEmpty()) {
			throw new MilkException(NO_KEYS);
		}
	}

	/**
	 * An internal method to generate the final URL used to make API calls.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return The URL object used to make an API call.
	 * @throws MilkException
	 */
	private URL genURL(String methodCall) throws MilkException {
		if (!methodCall.contains("getFrob") && !methodCall.contains("getToken")) {
			if (_token == null || _token.isEmpty()) {
				throw new MilkException(NO_TOKEN);
			}
			methodCall = API_KEY + _apiKey + AUTH_TOKEN + _token + methodCall;
		} else
			methodCall = API_KEY + _apiKey + methodCall;

		try {
			return new URL(API_URL + "?" + methodCall.replace(' ', '+').replace("+", "%2B") +
					parseSig(methodCall));
		} catch (MalformedURLException e) {
			throw new MilkException(API_ERROR, e);
		}
	}

	/**
	 * An internal method to generate a SAXParser used to parse API responses.
	 *
	 * @return A SAXParser.
	 * @throws MilkException
	 * @throws SAXException
	 */
	private SAXParser genParser() throws MilkException, SAXException {
		try {
			return SAXParserFactory.newInstance().newSAXParser();
		} catch (ParserConfigurationException e) {
			throw new MilkException(API_ERROR, e);
		}
	}

	/**
	 * An internal method for making rtm.test.echo calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return An EchoHandler containing the API response.
	 * @throws MilkException
	 */
	private EchoHandler callEcho(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		EchoHandler handler = new EchoHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making rtm.list and rtm.task calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A ListHandler containing the API response.
	 * @throws MilkException
	 */
	private ListHandler callList(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		ListHandler handler = new ListHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making rtm.group calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A GroupHandler containing the API response.
	 * @throws MilkException
	 */
	private GroupHandler callGroup(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		GroupHandler handler = new GroupHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making Token calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A TokenHandler containing the API response.
	 * @throws MilkException
	 */
	private TokenHandler callToken(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		TokenHandler handler = new TokenHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making rtm.contact calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A UserHandler containing the API response.
	 * @throws MilkException
	 */
	private UserHandler callUser(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		UserHandler handler = new UserHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for parsing transaction responses from the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A TrasnactionHandler containing the API response.
	 * @throws MilkException
	 */
	private TransactionHandler callTrans(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		TransactionHandler handler = new TransactionHandler();

		checkKeys();


		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making rtm.task.note calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A NoteHandler containing the API response.
	 * @throws MilkException
	 */
	private NoteHandler callNote(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		NoteHandler handler = new NoteHandler();

		checkKeys();


		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making Time calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A TimeHandler containing the API response.
	 * @throws MilkException
	 */
	private TimeHandler callTime(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		TimeHandler handler = new TimeHandler();

		checkKeys();


		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	/**
	 * An internal method for making various calls to the API.
	 *
	 * @param methodCall The method to call, along with all necessary parameters.
	 * @return A ResponseHandler containing the API response.
	 * @throws MilkException
	 */
	private ResponseHandler callAPI(String methodCall) throws MilkException {
		URL url;
		SAXParser parser;
		ResponseHandler handler = new ResponseHandler();

		checkKeys();

		try {
			url = genURL(methodCall);
			parser = genParser();

			parser.parse(url.openStream(), handler);

			_error = handler.getError();
		} catch (IOException e) {
			throw new MilkException(API_ERROR, e);
		} catch (SAXException e) {
			throw new MilkException(API_ERROR, e);
		}

		return handler;
	}

	//----------------------Signature Parsing---------------------------------//

	/**
	 * A method for generating the appropiate hash code used to sign API calls.
	 *
	 * @param methodCall The full method call, including all parameters. (e.g. key=value&key2=value2)
	 * @return An md5 hash of the sorted parameters.
	 * @throws MilkException
	 */
	public String parseSig(String methodCall) throws MilkException {
		String retVal;
		String sig = _secret + parseMethod(methodCall);

		// Variables needed for MD5 hashing
		MessageDigest m;
		BigInteger bigInt;
		byte digest[];

		// Create the digest used to hash the api_sig
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new MilkException(HASHING_ERROR, e);
		}

		// Hash the api_sig and convert the resulting byte array to a string
		m.reset();
		m.update(sig.getBytes());
		digest = m.digest();
		bigInt = new BigInteger(1, digest);
		retVal = bigInt.toString(16);

		// Zero pad the hash to get the full 32 chars
		while (retVal.length() < 32) {
			retVal = "0" + retVal;
		}

		return (API_SIG + retVal);
	}

	/**
	 * A method to prepare the parameters of a method call for signing.
	 *
	 * @param methodCall The method call being made, along with all parameters.
	 * @return A string sorted and prepared for signing.
	 * @throws MilkException
	 */
	private String parseMethod(String methodCall) {
		String retVal = "";
		String sig[] = methodCall.split("&");
		Arrays.sort(sig);

		for (int i = 0; i < sig.length; i++) {
			if (!sig[i].isEmpty()) {
				retVal += sig[i].replace("=", "");
			}
		}

		return retVal;
	}

	//-----------------------------Auth---------------------------------------//

	/**
	 * Returns the credentials attached to an authentication token.  (rtm.auth.checkToken)
	 *
	 * @return The credentials attached to the given token.
	 * @throws MilkException
	 */
	public Token checkToken() throws MilkException {
		return callToken(CHECK_TOKEN).getToken();
	}

	/**
	 * Generates a frob used during authentication.  (rtm.auth.getFrob)
	 *
	 * @return A frob to be used during authentication.
	 * @throws MilkException
	 */
	public String getFrob() throws MilkException {
		return callAPI(GET_FROB).getFrob();
	}

	/**
	 * Returns the auth token associated with the given frob.  (rtm.auth.getToken)
	 *
	 * @param frob The frob to check.
	 * @return The token associated with the given frob.
	 * @throws MilkException
	 */
	public Token getToken(String frob) throws MilkException {
		return callToken(GET_TOKEN + FROB + frob).getToken();
	}

	//---------------------------Contacts-------------------------------------//

	/**
	 * Adds a new contact.  (rtm.contacts.add)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param contact  The username or email address of a Remember the Milk user.
	 * @return The details of the added user.
	 * @throws MilkException
	 */
	public User addContact(String timeline, String contact) throws MilkException {
		return callUser(ADD_CONTACT + TIMELINE + timeline + "&contact=" + contact).getUser();
	}

	/**
	 * Deletes a contact.  (rtm.contacts.delete)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param contactID The ID of the contact to delete.
	 * @return The transaction associated with this method call.
	 * @throws MilkException
	 */
	public Transaction deleteContact(String timeline,
	                                 String contactID) throws MilkException {
		return callTrans(DELETE_CONTACT + TIMELINE + timeline + "&contact_id=" + contactID).getTransaction();
	}

	/**
	 * Retrieves a list of contacts.  (rtm.contacts.getList)
	 *
	 * @return A vector containing all of the users contacts.
	 * @throws MilkException
	 */
	public Vector<User> getListOfContacts() throws MilkException {
		return callUser(GETLIST_CONTACTS).getUsers();
	}

	//----------------------------Groups--------------------------------------//

	/**
	 * Creates a new group.  (rtm.groups.add)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param group    The name of the new group.
	 * @return The new group.
	 * @throws MilkException
	 */
	public Group addGroup(String timeline, String group) throws MilkException {
		return callGroup(ADD_GROUP + TIMELINE + timeline + "&group=" + group).getGroup();
	}

	/**
	 * Adds a contact to a group.  (rtm.groups.addContact)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param groupID   The ID of the group to add the contact to.
	 * @param contactID The ID of the contact to add.
	 * @return The transaction associated with this method call.
	 * @throws MilkException
	 */
	public Transaction addContactGroup(String timeline, String groupID, String contactID) throws MilkException {
		return callTrans(ADD_CONTACT_GROUP + TIMELINE + timeline + "&group_id=" + groupID + "&contact_id=" + contactID).getTransaction();
	}

	/**
	 * Deletes a group.  (rtm.groups.delete)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param groupID  The ID of the group to delete.
	 * @return The transaction associated with this method call.
	 * @throws MilkException
	 */
	public Transaction deleteGroup(String timeline, String groupID) throws MilkException {
		return callTrans(DELETE_GROUP + TIMELINE + timeline + "&group_id=" + groupID).getTransaction();
	}

	/**
	 * Retrieves a list of groups.  (rtm.groups.getList)
	 *
	 * @return A vector containing all of a users' groups.
	 * @throws MilkException
	 */
	public Vector<Group> getListOfGroups() throws MilkException {
		return callGroup(GETLIST_GROUPS).getGroups();
	}

	/**
	 * Removes a contact from a group. (rtm.groups.removeContact)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param groupID   The ID of the group to remove the contact from.
	 * @param contactID The ID of the contact to remove.
	 * @return The transaction associated with this method call.
	 * @throws MilkException
	 */
	public Transaction removeContactGroup(String timeline, String groupID, String contactID) throws MilkException {
		return callTrans(REMOVE_CONTACT_GROUP + TIMELINE + timeline + "&group_id=" + groupID + "&contact_id=" + contactID).getTransaction();
	}

	//-----------------------------List---------------------------------------//

	/**
	 * Creates a new list. (rtm.lists.add)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param name     The name of the new list.  Cannot be 'Inbox' or 'Sent'.
	 * @param filter   If specified, a Smart List is created with the desired criteria.
	 * @return The new list.
	 * @throws MilkException
	 */
	public TaskList addTaskList(String timeline, String name, String filter) throws MilkException {
		String methodCall = ADD_TASKLIST + TIMELINE + timeline + "&name=" + name;

		if (!filter.isEmpty()) {
			methodCall += "&filter=" + filter;
		}

		ListHandler listHandler = callList(methodCall);
		if (listHandler != null) {
			Vector<TaskList> taskLists = listHandler.getTaskLists();
			if (taskLists != null) return taskLists.get(0);
		}
		return null;

	}

	/**
	 * Archives a task list. (rtm.lists.archive)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list to archive.
	 * @return The archived list.
	 * @throws MilkException
	 */
	public TaskList archiveTaskList(String timeline, String listID) throws MilkException {
		return callList(ARCHIVE_TASKLIST + TIMELINE + timeline + "&list_id=" + listID).getTaskList();
	}

	/**
	 * Deletes a task list. (rtm.lists.delete)
	 * NOTE: This does not remove the list from the server, it only marks the list as deleted.
	 * Any tasks in the deleted list are moved to the default list.
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list to delete.
	 * @return The deleted list.
	 * @throws MilkException
	 */
	public TaskList deleteTaskList(String timeline, String listID) throws MilkException {
		return callList(DELETE_TASKLIST + TIMELINE + timeline + "&list_id=" + listID).getTaskList();
	}

	/**
	 * Retrives a list of lists. (rtm.lists.getList)
	 *
	 * @return A vector containing a users' lists.
	 * @throws MilkException
	 */
	public Vector<TaskList> getListOfTaskLists() throws MilkException {
		return callList(GETLIST_TASKLIST).getTaskLists();
	}

	/**
	 * Sets the default list. (rtm.lists.setDefaultList)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list to set as default.
	 * @return The transaction associated with the method call.
	 * @throws MilkException
	 */
	public Transaction setDefaultTaskList(String timeline, String listID) throws MilkException {
		return callTrans(SETDEFAULT_TASKLIST + TIMELINE + timeline + "&list_id=" + listID).getTransaction();
	}

	/**
	 * Renames a list. (rtm.lists.setName)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list to rename.
	 * @param name     The new name of the list.  Cannot be 'Inbox' or 'Sent'.
	 * @return The renamed list.
	 * @throws MilkException
	 */
	public TaskList setNameOfTaskList(String timeline, String listID, String name) throws MilkException {
		return callList(SETNAME_TASKLIST + TIMELINE + timeline + "&list_id=" + listID + "&name=" + name).getTaskList();
	}

	/**
	 * Unarchives a list. (rtm.lists.unarchive)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list to unarchive.
	 * @return The TaskList that was unarchived.
	 * @throws MilkException
	 */
	public TaskList unarchiveTaskList(String timeline, String listID) throws MilkException {
		return callList(UNARCHIVE_TASKLIST + TIMELINE + timeline + "&list_id=" + listID).getTaskList();
	}

	//---------------------------Locations------------------------------------//

	/**
	 * Retrieves a list of locations. (rtm.locations.getList)
	 *
	 * @return A vector containing all of the current users locations.
	 * @throws MilkException
	 */
	public Vector<Location> getLocations() throws MilkException {
		return callAPI(GET_LOCATIONS).getLocations();
	}

	//---------------------------Settings-------------------------------------//

	/**
	 * Retrieve a list of user settings. (rtm.settings.getList)
	 *
	 * @return The settings of the current user.
	 * @throws MilkException
	 */
	public Settings getSettings() throws MilkException {
		return callAPI(GET_SETTINGS).getSettings();
	}

	//-----------------------------Task---------------------------------------//

	/**
	 * Adds a task to the specified list. (rtm.tasks.add)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param name     The name of the task.
	 * @param parse    1 if the task should be processed using RTM's Smart Add, 0 otherwise.  An empty value defaults to 0.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList addTask(String timeline, String listID, String name, String parse) throws MilkException {
		String methodCall = ADD_TASK + TIMELINE + timeline + "&list_id=" + listID + "&name=" + name;

		if (!(parse.isEmpty())) {
			methodCall += "&parse=" + parse;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Add tags to a task. (rtm.tasks.addTags)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param tags     A comma delimited list of tags to be added.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList addTags(String timeline, String listID, String seriesID, String taskID, String tags) throws MilkException {
		return callList(ADD_TAGS + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID + "&tags=" + tags).getTaskList();
	}

	/**
	 * Marks a task as completed. (rtm.tasks.complete)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList completeTask(String timeline, String listID, String seriesID, String taskID) throws MilkException {
		return callList(COMPLETE_TASK + TIMELINE + timeline + "&list_id=" + listID + "&task_id=" + taskID + "&taskseries_id=" + seriesID).getTaskList();
	}

	/**
	 * Marks a task as deleted. (rtm.tasks.delete)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList deleteTask(String timeline, String listID, String seriesID, String taskID) throws MilkException {
		return callList(DELETE_TASK + TIMELINE + timeline + "&list_id=" + listID + "&task_id=" + taskID + "&taskseries_id=" + seriesID).getTaskList();
	}

	/**
	 * Retrieve a list of tasks. (rtm.tasks.getList)
	 *
	 * @param listID   The ID of the list to retrieve.  An empty value returns all lists, unless filter is specified.
	 * @param filter   If specified, searches task for the specified criteria.
	 * @param lastSync An ISO 8601 formatted time value.  A non-empty value will return only tasks modified since last_sync.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public Vector<TaskList> getTaskList(String listID, String filter, String lastSync) throws MilkException {
		String methodCall = GET_TASKLIST;

		if (!(listID.isEmpty())) {
			methodCall += "&list_id=" + listID;
		}

		if (!(filter.isEmpty())) {
			methodCall += "&filter=" + filter;
		}

		if (!(lastSync.isEmpty())) {
			methodCall += "&last_sync=" + lastSync;
		}

		return callList(methodCall).getTaskLists();
	}

	/**
	 * Move the priority of a task up or down. (rtm.tasks.movePriority)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param listID    The ID of the list containing the task.
	 * @param seriesID  The ID of the series containing the task.
	 * @param taskID    The ID of the task.
	 * @param direction The direction to move a priority ('up' or 'down').
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList moveTaskPriority(String timeline, String listID, String seriesID, String taskID, String direction) throws MilkException {
		return callList(MOVE_PRIORITY + TIMELINE + timeline + "&list_id=" + listID +
				"&taskseries_id=" + seriesID + "&task_id=" + taskID + "&direction=" + direction).getTaskList();
	}

	/**
	 * Move a task between lists. (rtm.tasks.moveTo)
	 *
	 * @param timeline   The timeline to be associated with this method call.
	 * @param seriesID   The ID of the series containing the task.
	 * @param taskID     The ID of the task.
	 * @param fromListID The ID of the list the task is currently in.
	 * @param toListID   The ID of the list to move the task to.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList moveTaskTo(String timeline, String seriesID, String taskID, String fromListID, String toListID) throws MilkException {
		return callList(MOVE_TASK_TO + TIMELINE + timeline + "&task_id=" + taskID +
				"&taskseries_id=" + seriesID + "&from_list_id=" + fromListID + "&to_list_id=" + toListID).getTaskList();
	}

	/**
	 * Advances a tasks due date by one day.  If the task is overdue or has no due date, the due date is set to today. (rtm.tasks.removeTags)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList postpone(String timeline, String listID, String seriesID, String taskID) throws MilkException {
		return callList(POSTPONE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID).getTaskList();
	}

	/**
	 * Removes tags from a task. (rtm.tasks.removeTags)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param tags     A comma delimited list of tags to be removed.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList removeTags(String timeline, String listID, String seriesID, String taskID, String tags) throws MilkException {
		return callList(REMOVE_TAGS + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID +
				"&tags=" + tags).getTaskList();
	}

	/**
	 * Sets the due date for a task. (rtm.tasks.setDueDate)
	 *
	 * @param timeline   The timeline to be associated with this method call.
	 * @param listID     The ID of the list containing the task.
	 * @param seriesID   The ID of the series containing the task.
	 * @param taskID     The ID of the task.
	 * @param due        The due date for a task, in ISO 8601 format.  'due' is always parsed in the user's timezone (as found in rtm.settings.getList).
	 *                   An empty value will unset any due date.
	 * @param hasDueTime Specifies whether the due date has a due time (1 or 0).  An empty value defaults to 0 (no due time).
	 * @param parse      Specifies whether to parse 'due' as per rtm.time.parse (1 or 0).  An empty value defaults to 0 (do not parse).
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setDueDate(String timeline, String listID, String seriesID, String taskID, String due, String hasDueTime, String parse) throws MilkException {
		String methodCall = SET_DUE_DATE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(due.isEmpty())) {
			methodCall += "&due=" + due;
		}

		if (!(hasDueTime.isEmpty())) {
			methodCall += "&has_due_time=" + hasDueTime;
		}

		if (!(parse.isEmpty())) {
			methodCall += "&parse=" + parse;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Sets a time estimate for the completion of a task. (rtm.tasks.setEstimate)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param estimate The time estimate for a task.  An emtpy value unsets any current estimate.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setEstimate(String timeline, String listID, String seriesID, String taskID, String estimate) throws MilkException {
		String methodCall = SET_ESTIMATE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(estimate.isEmpty())) {
			methodCall += "&estimate=" + estimate;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Sets a location for a task. (rtm.tasks.setLocation)
	 *
	 * @param timeline   The timeline to be associated with this method call.
	 * @param listID     The ID of the list containing the task.
	 * @param seriesID   The ID of the series containing the task.
	 * @param taskID     The ID of the task.
	 * @param locationID The ID of a location.  An empty value unsets any location.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setLocation(String timeline, String listID, String seriesID, String taskID, String locationID) throws MilkException {
		String methodCall = SET_LOCATION + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(locationID.isEmpty())) {
			methodCall += "&location_id=" + locationID;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Renames a task. (rtm.tasks.setName)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param name     The new name for the task.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setName(String timeline, String listID, String seriesID, String taskID, String name) throws MilkException {
		return callList(SET_NAME + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID + "&name=" + name).getTaskList();
	}

	/**
	 * Sets the priority of the task (1, 2, or 3). (rtm.tasks.setPriority)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param priority The desired priority of the task.  An empty of invalid value leaves the task with no priority.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setPriority(String timeline, String listID, String seriesID, String taskID, String priority) throws MilkException {
		String methodCall = SET_PRIORITY + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(priority.isEmpty())) {
			methodCall += "&priority=" + priority;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Set the recurrence pattern for a task. (rtm.tasks.setRecurrence)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param repeat   The recurrence pattern for a task.  An empty value unsets any existing recurrence pattern.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setRecurrence(String timeline, String listID, String seriesID, String taskID, String repeat) throws MilkException {
		String methodCall = SET_RECURRENCE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(repeat.isEmpty())) {
			methodCall += "&repeat=" + repeat;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Sets the tags of a task.  (rtm.tasks.setTags)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param tags     The tags to set, as a comma delimited list.  Overwrites any current tags.  An empty value removes any existing tags.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setTags(String timeline, String listID, String seriesID, String taskID, String tags) throws MilkException {
		return callList(SET_TAGS + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID + "&tags=" + tags).getTaskList();
	}

	/**
	 * Sets the URL for a task. (rtm.tasks.setURL)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @param url      The URL to set.  If left empty, the URL will be unset.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList setURL(String timeline, String listID, String seriesID, String taskID, String url) throws MilkException {
		String methodCall = SET_URL + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID;

		if (!(url.isEmpty())) {
			methodCall += "&url=" + url;
		}

		return callList(methodCall).getTaskList();
	}

	/**
	 * Marks a task as incomplete. (rtm.tasks.uncomplete)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param listID   The ID of the list containing the task.
	 * @param seriesID The ID of the series containing the task.
	 * @param taskID   The ID of the task.
	 * @return A TaskList containing the Taskseries and task.
	 * @throws MilkException
	 */
	public TaskList uncomplete(String timeline, String listID, String seriesID, String taskID) throws MilkException {
		return callList(UNCOMPLETE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID).getTaskList();
	}

	//-----------------------------Note---------------------------------------//

	/**
	 * Adds a new note to a task. (rtm.tasks.notes.add)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param listID    The ID of the list containing the task.
	 * @param seriesID  The ID of the series containing the task.
	 * @param taskID    The ID of the task.
	 * @param noteTitle The title of the new note.
	 * @param noteText  The body of the new note.
	 * @return The new note.
	 * @throws MilkException
	 */
	public Note addNote(String timeline, String listID, String seriesID, String taskID, String noteTitle, String noteText) throws MilkException {
		return callNote(ADD_NOTE + TIMELINE + timeline + "&list_id=" + listID + "&taskseries_id=" + seriesID + "&task_id=" + taskID +
				"&note_title=" + noteTitle + "&note_text=" + noteText).getNote();
	}

	/**
	 * Deletes a note. (rtm.tasks.notes.delete)
	 *
	 * @param timeline The timeline to be associated with this method call.
	 * @param noteID   The ID of the note to delete.
	 * @return Returns true if the note was successfully deleted, otherwise returns false.
	 * @throws MilkException
	 */
	public boolean deleteNote(String timeline, String noteID) throws MilkException {
		return (callAPI(DELETE_NOTE + TIMELINE + timeline + "&note_id=" + noteID).getError() == null);
	}

	/**
	 * Modifies a note. (rtm.tasks.notes.edit)
	 *
	 * @param timeline  The timeline to be associated with this method call.
	 * @param noteID    The id of the note to be edited.
	 * @param noteTitle The new title of the note.
	 * @param noteText  The new body of the note.
	 * @return The modified note.
	 * @throws MilkException
	 */
	public Note editNote(String timeline, String noteID, String noteTitle, String noteText) throws MilkException {
		return callNote(EDIT_NOTE + TIMELINE + timeline + "&note_id=" + noteID + "&note_title=" + noteTitle + "&note_text=" + noteText).getNote();
	}

	//-----------------------------Test---------------------------------------//

	/**
	 * A testing method which echos all parameters back in the response. (rtm.test.echo)
	 *
	 * @param string The list of parameters and values.
	 * @return The list of parameters and values entered, null if an error occurred.
	 * @throws MilkException
	 */
	public HashMap<String, String> echo(HashMap<String, String> string) throws MilkException {
		Iterator<Entry<String, String>> iter = string.entrySet().iterator();
		String methodCall = "";

		while (iter.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) iter.next();
			methodCall += "&" + pairs.getKey() + "=" + pairs.getValue();
		}

		return callEcho(TEST_ECHO + methodCall).getParams();
	}


	/**
	 * A method to check if the user is logged into RTM. (rtm.test.login)
	 *
	 * @return The details of the current user.
	 * @throws MilkException
	 */
	public User testLogin() throws MilkException {
		return callUser(TEST_LOGIN).getUser();
	}

	//-----------------------------Time---------------------------------------//

	/**
	 * Converts the time from one timezone to another.  (rtm.time.convert)
	 *
	 * @param toTimezone   The timezone to convert to.
	 * @param fromTimezone The timezone to convert from. Defaults to UTC if left empty.
	 * @param time         The time to convert in ISO 8601 format.  Defaults to 'now' if left empty.
	 * @return The time in the specified timezone.
	 * @throws MilkException
	 */
	public Time convertTime(String toTimezone, String fromTimezone, String time) throws MilkException {
		String methodCall = CONVERT_TIME + "&to_timezone=" + toTimezone;

		if (!(fromTimezone.isEmpty())) {
			methodCall += "&from_timezone=" + fromTimezone;
		}

		if (!(time.isEmpty())) {
			methodCall += "&time=" + time;
		}

		return callTime(methodCall).getTime();
	}

	/**
	 * Parses a string into the RTM time format. (rtm.time.parse)
	 *
	 * @param text       The text to be parsed (e.g. 'today' or 'noon').
	 * @param timezone   The timezone in which the text should be parsed.  Defaults to UTC if left empty.
	 * @param dateFormat The date format to use.  0 indicates European format (14/10/2006) while 1
	 *                   indicates American format (10/14/2006). Defaults to 1 if left empty.
	 * @return The parsed time.
	 * @throws MilkException
	 */
	public Time parseTime(String text, String timezone, String dateFormat) throws MilkException {
		String methodCall = PARSE_TIME + "&text=" + text;

		if (!timezone.isEmpty()) {
			methodCall += "&timezone=" + timezone;
		}

		if (!dateFormat.isEmpty()) {
			methodCall += "&dateformat=" + dateFormat;
		}

		return callTime(methodCall).getTime();
	}

	//---------------------------Timeline-------------------------------------//

	/**
	 * Creates a timeline to be used in making method calls. (rtm.timeline.create)
	 *
	 * @return A string containing the generated timeline.
	 * @throws MilkException
	 */
	public String createTimeline() throws MilkException {
		return callAPI(CREATE_TIMELINE).getTimeline();
	}

	//---------------------------Timezone-------------------------------------//

	/**
	 * Gets the list of all timezones in the format accepted by RTM. (rtm.timezones.getList)
	 *
	 * @return A vector of Timezones as supplied by RTM.
	 * @throws MilkException
	 */
	public Vector<Timezone> getListOfTimezones() throws MilkException {
		return callAPI(GETLIST_TIMEZONES).getTimezones();
	}

	//-------------------------Transaction------------------------------------//

	/**
	 * Undo the actions of a previous method call.  (rtm.transactions.undo)
	 *
	 * @param timeline      The timeline used in the previous method call.
	 * @param transactionID The transaction ID of the call being undone.
	 * @return True if the previous method call was undone, otherwise returns false.
	 * @throws MilkException
	 */
	public boolean undo(String timeline,
	                    String transactionID) throws MilkException {
		return (callAPI(UNDO + TIMELINE + timeline + "&transaction_id=" + transactionID).getError() == null);
	}
}
