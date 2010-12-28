package advws.net.ricews.simmpledocumentaction;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("/")
public interface Webservice {

	@GET
	@Path("/ping")
	public String ping();

	@POST
	@Path("/doc/create/initiatorID/{initiatorID}/appid/{appid}/type/{type}/title/{title}")
	public String create(@Context HttpHeaders hh,
			@PathParam("initiatorID") String initiatorId,
			@PathParam("appid") String appDocId,
			@PathParam("type") String docType,
			@PathParam("title") String docTitle);

	@POST
	@Path("/doc/save/userID/{userID}/docid/{docid}/title/{title}/annotation/{annotation}")
	public String save(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("title") String docTitle,
			@PathParam("annotation") String annotation);

	@GET
	@Path("/doc/getdocument/userID/{userID}/docid/{docid}")
	public String get(@Context HttpHeaders hh,
			@PathParam("docid") String docID, @PathParam("userID") String userID);

	@POST
	@Path("/doc/acknowledge/userID/{userID}/docid/{docid}/annotation/{annotation}")
	public String acknowledge(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/approve/userID/{userID}/docid/{docid}/title/{title}/annotation/{annotation}")
	public String approve(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("title") String docTitle,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/blanketApprove/userID/{userID}/docid/{docid}/title/{title}/annotation/{annotation}")
	public String blanketApprove(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("title") String docTitle,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/cancel/userID/{userID}/docid/{docid}/annotation/{annotation}")
	public String cancel(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/disapprove/userID/{userID}/docid/{docid}/annotation/{annotation}")
	public String disapprove(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/fyi/userID/{userID}/docid/{docid}")
	public String fyi(@Context HttpHeaders hh,
			@PathParam("docid") String docID, @PathParam("userID") String userID);

	@POST
	@Path("/doc/route/userID/{userID}/docid/{docid}/title/{title}/annotation/{annotation}")
	public String route(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("title") String docTitle,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocAckToGroup/userID/{userID}/docid/{docid}/recipientgroupid/{recipientgroupid}/annotation/{annotation}")
	public String requestAdHocAckToGroup(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientgroupid") String recipientGroupId,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocAckToUser/userID/{userID}/docid/{docid}/recipientid/{recipientid}/annotation/{annotation}")
	public String requestAdHocAckToUser(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientid") String recipientId,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocApproveToGroup/userID/{userID}/docid/{docid}/recipientgroupid/{recipientgroupid}/annotation/{annotation}")
	public String requestAdHocApproveToGroup(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientgroupid") String recipientGroupId,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocApproveToUser/userID/{userID}/docid/{docid}/recipientid/{recipientid}/annotation/{annotation}")
	public String requestAdHocApproveToUser(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientid") String recipientId,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocFyiToGroup/userID/{userID}/docid/{docid}/recipientgroupid/{recipientgroupid}/annotation/{annotation}")
	public String requestAdHocFyiToGroup(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientgroupid") String recipientGroupId,
			@PathParam("annotation") String annotation);

	@POST
	@Path("/doc/requestAdHocFyiToUser/userID/{userID}/docid/{docid}/recipientid/{recipientid}/annotation/{annotation}")
	public String requestAdHocFyiToUser(@Context HttpHeaders hh,
			@PathParam("docid") String docid,
			@PathParam("userID") String userID,
			@PathParam("recipientid") String recipientId,
			@PathParam("annotation") String annotation);

	@GET
	@Path("/doc/isUserInRouteLog/userID/{userID}/docid/{docid}")
	public String isUserInRouteLog(@Context HttpHeaders hh,
			@PathParam("docid") String docid, @PathParam("userID") String userID);

	@GET
	@Path("/actionitems/getinbox/{userID}")
	public String getUserInbox(@Context HttpHeaders hh, @PathParam("userID") String userId);

	@GET
	@Path("/actionitems/countinbox/{userID}")
	public int countUserInboxItems(@Context HttpHeaders hh, @PathParam("userID") String userId);

	@GET
	@Path("/actionitems/findbyrouteheader/{routeHeaderIdStr}")
	public String findByRouteHeaderId(@Context HttpHeaders hh, @PathParam("routeHeaderIdStr") String routeHeaderIdStr);

	@POST
	@Path("/notification/send")
	public void sendNotification(@Context HttpHeaders hh, String message);
}
