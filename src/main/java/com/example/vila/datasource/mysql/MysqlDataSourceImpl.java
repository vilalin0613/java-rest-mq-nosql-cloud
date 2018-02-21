/**
 * 
 */
package com.example.vila.datasource.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.datasource.DataSourceException;
import com.example.vila.repository.MysqlRepository;

/**
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class MysqlDataSourceImpl extends MysqlRepository implements MySqlDataSource {

    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MysqlDataSourceImpl.class);

    @Override
	public void initialize() {
		
	}
    
    @Override
	public boolean authBasic(String username, String password) throws DataSourceException {
		
    	logger.trace("METHOD authBasic IN");
        
        
        boolean result = false;
        
        
        Connection con = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        
        try {
            con = this.getConnection();
            pstat = con.prepareStatement("SELECT displayname FROM demo.account WHERE username = ? and userpassword = ? and active = 1");
            pstat.setString(1, username);
            pstat.setString(2, password);
            rs = pstat.executeQuery();

            if (rs.next()) {
                
                logger.debug("displayname: {}", rs.getString("displayname"));
                
                result = true;
            }
            
        } catch (SQLException sqle) {
            throw new DataSourceException(sqle);
        } finally {
            if (rs != null) { try { rs.close(); } catch (SQLException ignore) {}}
            if (pstat != null) { try { pstat.close(); } catch (SQLException ignore) {}}
            if (con != null) { try { con.close(); } catch (SQLException ignore) {}}
        }
        
        
        logger.trace("METHOD authBasic OUT");
        
        
        return result;
	}
    
    @Override
    public String getUserID(String phoneNumber) throws DataSourceException {

        logger.trace("METHOD getUserID IN");
        logger.debug("phoneNumber: {}", phoneNumber);
        
        String result = null;
        
        
        Connection con = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();
            pstat = con.prepareStatement("SELECT u.UID "
            		+ "FROM demodb.User AS u "
            		+ "INNER JOIN demodb.UserStatus AS us "
            		+ "ON u.userID = us.userID "
            		+ "WHERE u.phoneNumber = ? AND u.active = 1");
            pstat.setString(1, phoneNumber);
            rs = pstat.executeQuery();

            if (rs.next()) {
                
                logger.debug("userID: {}", rs.getString("UID"));
                
                result = rs.getString("UID");
            }
            
        } catch (SQLException sqle) {
            throw new DataSourceException(sqle);
        } finally {
            if (rs != null) { try { rs.close(); } catch (SQLException ignore) {}}
            if (pstat != null) { try { pstat.close(); } catch (SQLException ignore) {}}
            if (con != null) { try { con.close(); } catch (SQLException ignore) {}}
        }

        logger.trace("METHOD getUserID OUT");
        
        
        return result;
    }

}
