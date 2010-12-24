package advws.net.ricews.simmpledocumentaction;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kew.webservice.UserInRouteLogResponse;

@Path("/")
public interface Webservice {
	
	@GET
	@Path("/ping")
	public String ping();
	
	@POST
	@Path("/create/initiatorID/{initiatorID}/appid/{appid}/type/{type}/title/{title}")
	public String create(@PathParam("initiatorID") String initiatorId, 
						 @PathParam("appid") String appDocId, 
						 @PathParam("type") String docType, 
						 @PathParam("title") String docTitle);

	public StandardResponse save(String docId, String userId, String docTitle,
			String annotation);
	
	public DocumentResponse get(String docId, String userId);
	
	public StandardResponse acknowledge(String docId, String userId,
			String annotation);
	
	public StandardResponse approve(String docId, String userId,
			String docTitle, String docContent, String annotation);
	
	public StandardResponse blanketApprove(String docId, String userId,
			String docTitle, String docContent, String annotation);
	
	public StandardResponse cancel(String docId, String userId,
			String annotation);
	
	public StandardResponse disapprove(String docId, String userId,
			String annotation);
	
	public StandardResponse fyi(String docId, String userId);
	
	public StandardResponse route(String docId, String userId, String docTitle,
			String docContent, String annotation);
	
	public StandardResponse requestAdHocAckToGroup(String docId, String userId,
			String recipientGroupId, String annotation);
	
	public StandardResponse requestAdHocAckToUser(String docId, String userId,
			String recipientuserId, String annotation);
	
	public StandardResponse requestAdHocApproveToGroup(String docId,
			String userId, String recipientGroupId, String annotation);
	
	public StandardResponse requestAdHocApproveToUser(String docId,
			String userId, String recipientuserId, String annotation);
	
	public StandardResponse requestAdHocFyiToGroup(String docId, String userId,
			String recipientGroupId, String annotation);

	public StandardResponse requestAdHocFyiToUser(String docId, String userId,
			String recipientuserId, String annotation);	
	
	public UserInRouteLogResponse isUserInRouteLog(String docId, String userId);
	
	public Collection<ActionItem> getUserInbox(String userId);
	
	public int countUserInboxItems(String userId);
	
	public Collection<ActionItem> findByRouteHeaderId(String routeHeaderIdStr);
	
	public void sendNotification(String message);
}
