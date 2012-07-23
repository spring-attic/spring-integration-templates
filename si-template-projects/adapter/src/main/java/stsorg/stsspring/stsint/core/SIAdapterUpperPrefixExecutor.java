/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stsorg.stsspring.stsint.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

/**
 * Bundles common core logic for the SIAdapterUpperPrefix components.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since SI-TEMPLATE-VERSION
 *
 */
public class SIAdapterUpperPrefixExecutor implements InitializingBean {

	private static final Log logger = LogFactory.getLog(SIAdapterUpperPrefixExecutor.class);

	private volatile String  exampleProperty;

	/**
	 * Constructor.
	 */
	public SIAdapterUpperPrefixExecutor() {}

	/**
	 * Verifies and sets the parameters. E.g. initializes the to be used
	 */
	public void afterPropertiesSet() {
		Assert.hasText(this.exampleProperty, "exampleProperty must not be empty.");
	}

	/**
	 * Executes the outbound SIAdapterUpperPrefix Operation.
	 *
	 */
	public Object executeOutboundOperation(final Message<?> message) {

		if (logger.isWarnEnabled()) {
			logger.warn("Logic not implemented, yet.");
		}

		return message.getPayload();

	}

	/**
	 * Execute the SIAdapterUpperPrefix operation. Delegates to
	 * {@link SIAdapterUpperPrefixExecutor#poll(Message)}.
	 */
	public Object poll() {
		return poll(null);
	}

	/**
	 * Execute a retrieving (polling) SIAdapterUpperPrefix operation.
	 *
	 * @param requestMessage May be null.
	 * @return The payload object, which may be null.
	 */
	public Object poll(final Message<?> requestMessage) {

		if (logger.isWarnEnabled()) {
			logger.warn("Logic not implemented, yet.");
		}

		return MessageBuilder.fromMessage(requestMessage).build();
	}

	/**
	 * Example property to illustrate usage of properties in Spring Integration
	 * components. Replace with your own logic.
	 *
	 * @param exampleProperty Must not be null
	 */
	public void setExampleProperty(String exampleProperty) {
		Assert.hasText(exampleProperty, "exampleProperty must be neither null nor empty");
		this.exampleProperty = exampleProperty;
	}

}
