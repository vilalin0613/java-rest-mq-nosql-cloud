/**
 * 
 */
package com.example.vila.rest.resource.entity;

/**
 * All classes extend this class MUST override hashCode(), equals(Object obj), toString() methods.
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class ResponseBody {
    
    /**
     * 
     */
    protected int returnCode;
    
    /**
     * Constructor. Each field MUST be initialized a default value here.
     * 
     */
    public ResponseBody() {
        this.returnCode = -1;
    }

    /**
     * 
     * 
     * @param returnCode
     */
    public ResponseBody(int returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * @return the returnCode
     */
    public int getReturnCode() {
        return this.returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + returnCode;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponseBody other = (ResponseBody) obj;
        if (returnCode != other.returnCode)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseBody [returnCode=").append(returnCode)
                .append("]");
        return builder.toString();
    }

    
    
}
