/**
 *
 */
package com.example.vila.rest.resource;

import java.util.Base64;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.datasource.DataSourceException;
import com.example.vila.datasource.mysql.MySqlDataSource;


/**
 * The base resource. All the resources MUST inherent this class. All the
 * business logics MUST be written in the resources. All methods in this class
 * MUST be "protected".
 *
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class BaseResource {

    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseResource.class);

    /**
     * Some initialized data sources (SQL, NoSQL)
     */
    @Context
    private ServletContext context;

    /**
     * @param authentication
     * @return
     * @throws DataSourceException
     */
    protected boolean authBasic(String authentication) throws DataSourceException {

        logger.trace("METHOD authUser IN");

        boolean result = false;

        if (authentication == null || authentication.isEmpty()) {
            result = false;
        } else if (!authentication.toLowerCase().startsWith("basic ")) {
            result = false;
        } else {

            String[] authInformation = (new String(Base64.getDecoder().decode(authentication.substring(6)))).split(":", 2);

            String username = authInformation[0];
            String password = authInformation[1];

            MySqlDataSource uds = (MySqlDataSource) this.context.getAttribute("mysqlRepository");

            result = uds.authBasic(username, password);

        }

        logger.trace("METHOD authUser OUT");

        return result;
    }
    
}
