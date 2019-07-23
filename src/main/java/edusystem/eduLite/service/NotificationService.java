package edusystem.eduLite.service;

import java.security.Principal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edusystem.eduLite.dto.NotificationDto;
import edusystem.eduLite.model.NotificationModel;
import edusystem.eduLite.util.FailureResponse;
import edusystem.eduLite.util.ResponseBuilder;

@Stateless
public class NotificationService {
	
	@Inject 
	NotificationModel notificModel;
	
	@Inject 
	ResponseBuilder responseBuilder;
	
	public Response writeNotification(Principal p, NotificationDto notificationDto) {
		NotificationDto notfcation = notificModel.writeNotification(p, notificationDto);
		if(notfcation == null) {
			return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to add a notification"));
		}
		return responseBuilder.buildResponse(Status.OK, notfcation);
	}
}
