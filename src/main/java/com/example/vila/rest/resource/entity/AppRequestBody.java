/**
 * 
 */
package com.example.vila.rest.resource.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppRequestBody extends RequestBody {

	/**
	 * 
	 */
	protected String checksum;
	
    /**
     * 
     */
    protected String phoneNumber;
    
    /**
     * 
     */
    protected String uuid;

    /**
     * Constructor. Each field MUST be initialized a default value here. 
     * 
     */
    public AppRequestBody() {
        super();
        this.checksum = null;
        this.phoneNumber = null;
        this.uuid = null;
    }

    /**
     * 
     * @param checksum the checksum to set
     * @param phoneNumber the phoneNumber to set
     * @param uuid the uuid to set
     */
    public AppRequestBody(String checksum, String phoneNumber, String uuid) {
        super();
        this.checksum = checksum;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
    }

    /**
     * 
     * 
     * @param phoneNumber the phoneNumber to set
     * @param uuid the uuid to set
     */
    public AppRequestBody(String phoneNumber, String uuid) {
        super();
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
    }
    
    /**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	/**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		AppRequestBody other = (AppRequestBody) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppRequestBody [checksum=");
		builder.append(checksum);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", uuid=");
		builder.append(uuid);
		builder.append("]");
		return builder.toString();
	}
    
}
