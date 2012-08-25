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
package sipackage.inbound;

import org.springframework.integration.Message;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

import sipackage.core.SIAdapterUpperPrefixExecutor;

/**
 * @author SI-TEMPLATE-AUTHOR
 * @since SI-TEMPLATE-VERSION
 *
 */
public class SIAdapterUpperPrefixPollingChannelAdapter extends IntegrationObjectSupport implements MessageSource<Object>{

	private final SIAdapterUpperPrefixExecutor siAdapterLowerPrefixExecutor;

	/**
	 * Constructor taking a {@link SIAdapterUpperPrefixExecutor} that provide all required SIAdapterUpperPrefix
	 * functionality.
	 *
	 * @param siAdapterLowerPrefixExecutor Must not be null.
	 */
	public SIAdapterUpperPrefixPollingChannelAdapter(SIAdapterUpperPrefixExecutor siAdapterLowerPrefixExecutor) {
		super();
		Assert.notNull(siAdapterLowerPrefixExecutor, "siAdapterLowerPrefixExecutor must not be null.");
		this.siAdapterLowerPrefixExecutor = siAdapterLowerPrefixExecutor;
	}

	/**
	 * Check for mandatory attributes
	 */
	@Override
	protected void onInit() throws Exception {
		 super.onInit();
	}

	/**
	 * Uses {@link SIAdapterUpperPrefixExecutor#poll()} to executes the SIAdapterUpperPrefix operation.
	 *
	 * If {@link SIAdapterUpperPrefixExecutor#poll()} returns null, this method will return
	 * <code>null</code>. Otherwise, a new {@link Message} is constructed and returned.
	 */
	public Message<Object> receive() {

		final Object payload = siAdapterLowerPrefixExecutor.poll();

		if (payload == null) {
			return null;
		}

		return MessageBuilder.withPayload(payload).build();
	}

	@Override
	public String getComponentType(){
		return "siAdapterLowerPrefix:inbound-channel-adapter";
	}

}
