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
package sipackage.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents some common Twitter related fields.
 *
 * @author SI-TEMPLATE-AUTHOR
 * @since  SI-TEMPLATE-VERSION
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TwitterAdapterStatus {

	@XmlAttribute
	private boolean running;

	/** Default constructor. */
	public TwitterAdapterStatus() {
		super();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public TwitterAdapterStatus(boolean running) {
		this.running = running;
	}

}
