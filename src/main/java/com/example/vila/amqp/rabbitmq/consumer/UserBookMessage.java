/**
 * 
 */
package com.example.vila.amqp.rabbitmq.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBookMessage extends ConsumerMessage {

	/**
	 * 
	 */
	private String userID;
	
	/**
	 * 
	 */
	private String userName;
	
	/**
	 * 
	 */
	private String mqSource;
	
	/**
     * Constructor. Each field MUST be initialized a default value here.
     * 
     */
	public UserBookMessage() {
		this.userID = null;
		this.userName = null;
		this.mqSource = null;
	}

	/**
	 * @param e164PhoneNumber
	 * @param phoneType
         * @param mvpnGroupID
	 */
	public UserBookMessage(String userID, String userName, String mqSource) {
		this.userID = userID;
		this.userName = userName;
		this.mqSource = mqSource;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMqSource() {
		return mqSource;
	}

	public void setMqSource(String mqSource) {
		this.mqSource = mqSource;
	}

	
	
}
