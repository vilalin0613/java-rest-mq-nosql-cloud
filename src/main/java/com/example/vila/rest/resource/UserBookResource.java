/**
 * 
 */
package com.example.vila.rest.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.example.vila.cloud.CloudService;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.example.vila.datasource.cassandra.entity.UserBookEntity;
import com.example.vila.rest.resource.cloud.Syncer;
import com.example.vila.rest.resource.entity.ErrorResponseBody;
import com.example.vila.rest.resource.entity.ResponseBody;
import com.example.vila.rest.resource.entity.UserBookQueryRequestBody;
import com.example.vila.rest.resource.entity.UserBookQueryRequestData;
import com.example.vila.rest.resource.entity.UserBookQueryResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The ContactServerResource class handles contact related REST web services.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@Path("/user/book")
public class UserBookResource {
	
	/**
	 * slf4j logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserBookResource.class);
	
	/**
     * Some initialized data sources (SQL, NoSQL)
     */
	@Context
	private ServletContext context;

    /**
     * Gets some phone status
     * @param authentication
     * @param requestBody
     * @return
     */
    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public ResponseBody query(@HeaderParam("authorization") String authentication,
    		@HeaderParam("Brand-Id") String brandID,
    		UserBookQueryRequestBody requestBody) {
		
    	try {
    		List<UserBookQueryRequestData> data = (List<UserBookQueryRequestData>) requestBody.getData();
        	
        	CloudService cloudService = (CloudService) this.context.getAttribute("cloudService");
        	CassandraDataSource cds = (CassandraDataSource) this.context.getAttribute("cassandraDataSource");
        	
        	ExecutorService executor = (ExecutorService) this.context.getAttribute("executor");
        	Collection<Syncer> collection = new ArrayList<Syncer>();
    		collection.add(new Syncer(cloudService, cds, data));
    		
    		executor.invokeAll(collection);
    		
    		List<HashMap<String, String>> books = new ArrayList<HashMap<String, String>>();
    		for (UserBookQueryRequestData subData : data) {
    			List<UserBookEntity> entityList = cds.getUserBooks(subData.getUserID(), subData.getLanguage());
    			for (UserBookEntity entity : entityList){
    				HashMap<String, String> bookInformation = new HashMap<String, String>();
    				bookInformation.put("userID", entity.getUserID());
    				bookInformation.put("language", entity.getLanguage());
    				bookInformation.put("bookID", entity.getBookID());
    				books.add(bookInformation);
    			}
    		}
    		
    		return new UserBookQueryResponseBody(books);
        			
        } catch (WebApplicationException wae) {
        	logger.error("WebApplicationException: {}", wae);
            MDC.put("statusCode", "3");
            return new ErrorResponseBody(3, "aaServerContacts error");
            
        } catch (Exception e) {
        	logger.error("Exception: {}", e);
        	MDC.put("statusCode", "3");
        	return new ErrorResponseBody(3, "");
            
        }
    }
}
