/**
 * 
 */
package com.example.vila.amqp;

import java.util.Map;

/**
 * AMQP producer interface.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public interface Producer {

	/**
	 * @param tags
	 */
	public void send(Map<String, Object> tags);
}
