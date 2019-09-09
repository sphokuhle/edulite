package edusystem.eduLite.util;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Stateless
public class ResponseBuilder {
	
	public Response buildResponse(Status status, Object obj) {
        return Response.status(status).entity(obj).header("Access-Control-Allow-Origin", "*").build();
    }
    
    public Response buildResponseError(Object msg) {
    	//Response.status(Status.OK).entity(msg).build();
    	return Response.status(422).entity(msg).build();
    }
}

