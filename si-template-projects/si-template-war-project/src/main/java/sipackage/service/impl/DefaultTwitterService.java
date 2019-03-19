/*
 * Copyright 2002-2013 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package sipackage.service.impl;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import sipackage.model.TwitterMessage;
import sipackage.service.TwitterService;
import sipackage.support.SortOrder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Implementation of the {@link TwitterService} interface.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since  SI-TEMPLATE-VERSION
 */
@Service
public class DefaultTwitterService implements TwitterService {

	/** Holds a collection of polled Twitter messages */
	private final Cache<Long, TwitterMessage> twitterMessages;

	@Autowired
	@Qualifier("controlBusChannel")
	private DirectChannel channel;

	/**
	 * Constructor that initializes the 'twitterMessages' Map as a simple LRU
	 * cache.
	 */
	public DefaultTwitterService() {

		twitterMessages = CacheBuilder.newBuilder()
			.maximumSize(10)
			.build();

	}

	/** {@inheritDoc} */
	@Override
	public SortedSet<TwitterMessage> getTwitterMessages(Long tweetId, SortOrder sortOrder) {

		final TreeSet<TwitterMessage> tweets = new TreeSet<TwitterMessage>();

		if (tweetId != null) {

			final Map<Long, TwitterMessage> twitterMessagesAsMap = twitterMessages.asMap();

			for (Long id : twitterMessagesAsMap.keySet()) {
				if (id.compareTo(tweetId) > 0) {
					tweets.add(twitterMessages.getIfPresent(id));
				}
			}

		} else {
			tweets.addAll(this.twitterMessages.asMap().values());
		}

		if (SortOrder.DESCENDING.equals(sortOrder)) {
			return tweets.descendingSet();
		}

		return tweets;
	}

	/** {@inheritDoc} */
	@Override
	public void startTwitterAdapter() {

		final MessagingTemplate m = new MessagingTemplate();
		final Message<String> operation = MessageBuilder.withPayload("@twitter.start()").build();

		m.send(channel, operation);

	}

	/** {@inheritDoc} */
	@Override
	public void stopTwitterAdapter() {

		final MessagingTemplate m = new MessagingTemplate();
		final Message<String> operation = MessageBuilder.withPayload("@twitter.stop()").build();

		m.send(channel, operation);

	}

	/** {@inheritDoc} */
	@Override
	public boolean isTwitterAdapterRunning() {

		final MessagingTemplate m = new MessagingTemplate();
		final Message<String> operation = MessageBuilder.withPayload("@twitter.isRunning()").build();

		@SuppressWarnings("unchecked")
		Message<Boolean> reply = (Message<Boolean>) m.sendAndReceive(channel, operation);

		return reply.getPayload();

	}

	/**
	 * Called by Spring Integration to populate a simple LRU cache.
	 *
	 * @param tweet - The Spring Integration tweet object.
	 */
	public void addTwitterMessages(Tweet tweet) {

			this.twitterMessages.put(tweet.getId(), new TwitterMessage(
					tweet.getId(),
					tweet.getCreatedAt(),
					tweet.getText(),
					tweet.getFromUser(),
					tweet.getProfileImageUrl()));

	}

}
