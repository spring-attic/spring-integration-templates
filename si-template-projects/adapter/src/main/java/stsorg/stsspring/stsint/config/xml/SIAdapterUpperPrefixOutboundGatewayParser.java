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
package stsorg.stsspring.stsint.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import stsorg.stsspring.stsint.outbound.SIAdapterUpperPrefixOutboundGateway;

/**
 * The Parser for SIAdapterUpperPrefix Outbound Gateway.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since SI-TEMPLATE-VERSION
 *
 */
public class SIAdapterUpperPrefixOutboundGatewayParser extends AbstractConsumerEndpointParser  {

	@Override
	protected BeanDefinitionBuilder parseHandler(Element gatewayElement, ParserContext parserContext) {

		final BeanDefinitionBuilder siAdapterLowerPrefixOutboundGatewayBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(SIAdapterUpperPrefixOutboundGateway.class);

		IntegrationNamespaceUtils.setValueIfAttributeDefined(siAdapterLowerPrefixOutboundGatewayBuilder, gatewayElement, "reply-timeout", "sendTimeout");

		final String replyChannel = gatewayElement.getAttribute("reply-channel");

		if (StringUtils.hasText(replyChannel)) {
			siAdapterLowerPrefixOutboundGatewayBuilder.addPropertyReference("outputChannel", replyChannel);
		}

		final BeanDefinitionBuilder siAdapterLowerPrefixExecutorBuilder = SIAdapterUpperPrefixParserUtils.getSIAdapterUpperPrefixExecutorBuilder(gatewayElement, parserContext);

		IntegrationNamespaceUtils.setValueIfAttributeDefined(siAdapterLowerPrefixExecutorBuilder, gatewayElement, "example-property");

		final BeanDefinition siAdapterLowerPrefixExecutorBuilderBeanDefinition = siAdapterLowerPrefixExecutorBuilder.getBeanDefinition();
		final String gatewayId = this.resolveId(gatewayElement, siAdapterLowerPrefixOutboundGatewayBuilder.getRawBeanDefinition(), parserContext);
		final String siAdapterLowerPrefixExecutorBeanName = gatewayId + ".siAdapterLowerPrefixExecutor";

		parserContext.registerBeanComponent(new BeanComponentDefinition(siAdapterLowerPrefixExecutorBuilderBeanDefinition, siAdapterLowerPrefixExecutorBeanName));

		siAdapterLowerPrefixOutboundGatewayBuilder.addConstructorArgReference(siAdapterLowerPrefixExecutorBeanName);

		return siAdapterLowerPrefixOutboundGatewayBuilder;

	}

	@Override
	protected String getInputChannelAttributeName() {
		return "request-channel";
	}

}
