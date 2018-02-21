/**
 * 
 */
package com.example.vila.amqp;

/**
 * AMQP consumer interface.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public interface Consumer {
	
	/**
	 * @param message
	 */
	public void process(String message);
}
