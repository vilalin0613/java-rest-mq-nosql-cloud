/**
 * 
 */
package com.example.vila.rest.resource.entity;

/**
 * 
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class ErrorResponseBody extends ResponseBody {
    
    /**
     * 
     */
    public static final String RETURNCODE_001 = "Wrong User or Password";
    
    /**
     * 
     */
    public static final String RETURNCODE_002 = "Wrong Function";
    
    /**
     * 
     */
    public static final String RETURNCODE_006 = "Data Format Error";

    /**
     * 
     */
    public static final String RETURNCODE_031 = "Prohibit Updating Same Contacts in a Short Time";

    /**
     * 
     */
    public static final String RETURNCODE_032 = "Prohibit Deleting Same Contacts in a Short Time";
    
    /**
     * 
     */
    public static final String RETURNCODE_131 = "Error when Sending SMS";
    
    
    
    
    
    /**
     * 
     */
    private String returnMsg;
    
    /**
     * Constructor. Each field MUST be initialized a default value here. 
     * 
     */
    public ErrorResponseBody() {
        super();
        this.returnMsg = null;
    }

    /**
     * 
     * 
     * @param returnMeg
     */
    public ErrorResponseBody(int returnCode, String returnMeg) {
        super(returnCode);
        this.returnMsg = returnMeg;
    }

	/**
	 * @return the returnMsg
	 */
	public String getReturnMsg() {
		return returnMsg;
	}

	/**
	 * @param returnMsg the returnMsg to set
	 */
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((returnMsg == null) ? 0 : returnMsg.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponseBody other = (ErrorResponseBody) obj;
		if (returnMsg == null) {
			if (other.returnMsg != null)
				return false;
		} else if (!returnMsg.equals(other.returnMsg))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorResponseBody [returnMsg=");
		builder.append(returnMsg);
		builder.append("]");
		return builder.toString();
	}

    
}
