/*
 * Task.java
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
 * A wrapper containing information about an RTM task.
 * 
 * @author Dustin Stroup
 */
public class Task {
	private String _id, _due, _hasDueTime, _added, _completed, _deleted, _priority, _postponed, _estimate;

	/**
	 * @param id			The ID associated with this task.
	 * @param due			A timestamp indicating the due date of this task.
	 * @param hasDueTime	A value (1 or 0) indicating whether this task's due date includes a time of day.
	 * @param added			A timestamp indicating when this task was created.
	 * @param completed		A timestamp indicating when this task was completed.
	 * @param deleted		A timestamp indicating when this task was deleted.
	 * @param priority		A value (1, 2, 3, or "") indicating this tasks priority.
	 * @param postponed		A value indicating how many times this task has been postponed.
	 * @param estimate		A time estimate for this task.
	 */
	public Task(String id, String due, String hasDueTime, String added, String completed, String deleted, String priority,
			String postponed, String estimate) {
		_id = id;
		_due = due;
		_hasDueTime = hasDueTime;
		_added = added;
		_completed = completed;
		_deleted = deleted;
		_priority = priority;
		_postponed = postponed;
		_estimate = estimate;
	}

	/**
	 * Returns the ID associated with this task.
	 * 
	 * @return 				The ID associated with this task.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Returns a timestamp indicating this tasks due date.
	 * 
	 * @return 				A timestamp indicating this tasks due date.  An emtpy String is returned if no due date is set.
	 */
	public String getDue() {
		return _due;
	}

	/**
	 * Indicates whether or not the due date includes a time of day.
	 * 
	 * @return 				Returns "1" if the due date includes a time of day, otherwise returns 0.
	 */
	public String getHasDueTime() {
		return _hasDueTime;
	}

	/**
	 * Returns the timestamp from this tasks creation.
	 * 
	 * @return 				A timestamp indicating when this task was created.
	 */
	public String getAdded() {
		return _added;
	}

	/**
	 * Returns the timestamp from this tasks completion.
	 * 
	 * @return 				A timestamp indicating when this task was completed.  An empty String is returned for incomplete tasks.
	 */
	public String getCompleted() {
		return _completed;
	}
	
	/**
	 * Returns the timestamp of this tasks deletion.
	 * 
	 * @return 				A timestamp indicating when this task was deleted. An empty String is returned for non-deleted tasks.
	 */
	public String getDeleted() {
		return _deleted;
	}

	/**
	 * Returns the priority of this task.
	 * 
	 * @return 				The priority of this task.  Returns an empty String if the task has no priority.
	 */
	public String getPriority() {
		return _priority;
	}

	/**
	 * Returns the number of times this task has been postponed.
	 * 
	 * @return 				The number of times this task has been postponed.  Returns an empty String if the task has never been postponed.
	 */
	public String getPostponed() {
		return _postponed;
	}

	/**
	 * Returns the time estimate for this task.
	 * 
	 * @return 				The estimated time for this task.
	 */
	public String getEstimate() {
		return _estimate;
	}
}
