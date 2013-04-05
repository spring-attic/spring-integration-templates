package stsorg.stsspringframework.stsintegration;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stsorg.stsspringframework.stsintegration.model.TwitterMessage;
import stsorg.stsspringframework.stsintegration.model.TwitterMessages;
import stsorg.stsspringframework.stsintegration.support.JaxbJacksonObjectMapper;

public class JacksonTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonTest.class);

	@Test
	public void twitterMessageSerializationTest() throws Exception {

		final JaxbJacksonObjectMapper mapper = new JaxbJacksonObjectMapper();

		final TwitterMessage message = new TwitterMessage();

		message.setCreatedAt(new Date());
		message.setFromUser("user");
		message.setProfileImageUrl("profileImageUrl");
		message.setText("some text");
		message.setId(123456789L);

		final String json = mapper.writeValueAsString(message);

		LOGGER.info(json);

		final TwitterMessage message2 = new TwitterMessage();
		message2.setCreatedAt(new Date());
		message2.setFromUser("user");
		message2.setProfileImageUrl("profileImageUrl");
		message2.setText("some text");
		message2.setId(77777L);

		final TwitterMessages list = new TwitterMessages();
		list.getTwitterMessages().add(message);
		list.getTwitterMessages().add(message2);

		String json2 = mapper.writeValueAsString(list);

		LOGGER.info(json2);

	}

}
