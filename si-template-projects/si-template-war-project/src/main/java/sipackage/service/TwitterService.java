/*
 * Copyright 2002-2013 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package sipackage.service;

import java.util.SortedSet;

import sipackage.model.TwitterMessage;
import sipackage.support.SortOrder;

/**
 * Provides some basic methods for controlling the flow of Twitter messages.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since  SI-TEMPLATE-VERSION
 *
 */
public interface TwitterService {

	/**
	 * Retrieve the already polled Twitter messages. Keep in mind this
	 * method does not perform the actual Twitter search. It merely returns all
	 * the Tweets that were previously polled through Spring Integration and
	 * which have been cached for returning those to the web-frontend. */
	SortedSet<TwitterMessage> getTwitterMessages(Long tweetId, SortOrder sortOrder);
	/**
	 * By default - After application startup, the Spring Integration Twitter
	 * search-inbound-channel-adapter is stopped. Use this method to start
	 * the adapter.
	 */
	void startTwitterAdapter();

	/**
	 * Allows for stopping the Spring Integration Twitter
	 * search-inbound-channel-adapter.
	 */
	void stopTwitterAdapter();

	/**
	 *  Returns <code>true</code> if the Twitter Adapter is running,
	 *  <code>false</code> otherwise.
	 */
	boolean isTwitterAdapterRunning();

}
