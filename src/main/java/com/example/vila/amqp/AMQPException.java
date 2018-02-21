/**
 * 
 */
package com.example.vila.amqp;

/**
 * The AMQP exception
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public class AMQPException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 102884159710026834L;

	/**
     * 
     * 
     * @param cause
     */
    public AMQPException(Throwable cause) {
        super(cause);
    }
}
