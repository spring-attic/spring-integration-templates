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
package sipackage.mvc.controller;

import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sipackage.model.TwitterAdapterStatus;
import sipackage.model.TwitterMessage;
import sipackage.model.TwitterMessages;
import sipackage.service.TwitterService;
import sipackage.support.SortOrder;

/**
 * Handles requests for the application home page.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since  SI-TEMPLATE-VERSION
 *
 */
@Controller
@RequestMapping
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private TwitterService twitterService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value={"/", "/tweets"})
	public String home(Model model, @RequestParam(required=false) Long latestTweetId,
			@RequestParam(defaultValue="DESCENDING", required=false) SortOrder sortOrder) {

		if (latestTweetId == null) {
			latestTweetId = 0L;
		}

		final SortedSet<TwitterMessage> twitterMessages = twitterService.getTwitterMessages(latestTweetId, sortOrder);

		TwitterMessages twitterMessagesWrapper = new TwitterMessages();

		if (twitterMessages == null || twitterMessages.isEmpty()) {
			twitterMessagesWrapper.setLatestTweetId(latestTweetId);
		} else {
			twitterMessagesWrapper.setTwitterMessages(twitterMessages);
		}

		twitterMessagesWrapper.setAdapterRunning(twitterService.isTwitterAdapterRunning());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Latest Tweet ID: '%s'; Adapter running: %s",
					twitterMessagesWrapper.getLatestTweetId(),
					twitterMessagesWrapper.isAdapterRunning()));
		}

		model.addAttribute("tweets", twitterMessagesWrapper);

		return "home";
	}

	@ResponseBody
	@RequestMapping(value={"/adapter/{state}"})
	public void state(@PathVariable String state) {

		if ("start".equalsIgnoreCase(state)) {
			twitterService.startTwitterAdapter();
		}
		else if ("stop".equalsIgnoreCase(state)) {
			twitterService.stopTwitterAdapter();
		}

	}

	@ResponseBody
	@RequestMapping(value={"/adapter-running"})
	public TwitterAdapterStatus isRunning() {
		TwitterAdapterStatus status = new TwitterAdapterStatus(twitterService.isTwitterAdapterRunning());
		return status;
	}
}

