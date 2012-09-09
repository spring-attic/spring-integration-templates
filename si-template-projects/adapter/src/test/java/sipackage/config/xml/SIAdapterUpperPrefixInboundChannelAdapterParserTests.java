/*
uytea * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package sipackage.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.test.util.TestUtils;

import sipackage.core.SIAdapterUpperPrefixExecutor;

/**
 * @author SI-TEMPLATE-AUTHOR
 * @since SI-TEMPLATE-VERSION
 *
 */
public class SIAdapterUpperPrefixInboundChannelAdapterParserTests {

	private ConfigurableApplicationContext context;

	private SourcePollingChannelAdapter consumer;

	@Test
	public void testJpaInboundChannelAdapterParser() throws Exception {

		setUp("SIAdapterUpperPrefixInboundChannelAdapterParserTests.xml", getClass(), "siAdapterLowerPrefixInboundChannelAdapter");

		final AbstractMessageChannel outputChannel = TestUtils.getPropertyValue(this.consumer, "outputChannel", AbstractMessageChannel.class);

		assertEquals("out", outputChannel.getComponentName());

		final SIAdapterUpperPrefixExecutor siAdapterLowerPrefixExecutor = TestUtils.getPropertyValue(this.consumer, "source.siAdapterLowerPrefixExecutor", SIAdapterUpperPrefixExecutor.class);

		assertNotNull(siAdapterLowerPrefixExecutor);

		final String exsampleProperty = TestUtils.getPropertyValue(siAdapterLowerPrefixExecutor, "exampleProperty", String.class);

		assertEquals("I am a sample property", exsampleProperty);

	}

	@Test
	public void testJpaExecutorBeanIdNaming() throws Exception {

		this.context = new ClassPathXmlApplicationContext("SIAdapterUpperPrefixInboundChannelAdapterParserTests.xml", getClass());
		assertNotNull(context.getBean("siAdapterLowerPrefixInboundChannelAdapter.siAdapterLowerPrefixExecutor", SIAdapterUpperPrefixExecutor.class));

	}

	@After
	public void tearDown(){
		if(context != null){
			context.close();
		}
	}

	public void setUp(String name, Class<?> cls, String consumerId){
		context    = new ClassPathXmlApplicationContext(name, cls);
		consumer   = this.context.getBean(consumerId, SourcePollingChannelAdapter.class);
	}

}
