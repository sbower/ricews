package advws.net.ricews.simmpledocumentaction;

import java.util.Collection;

import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionlist.service.ActionListService;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kew.webservice.UserInRouteLogResponse;
import org.kuali.rice.ksb.messaging.service.KSBXMLService;

import advws.net.ricews.util.ConvertToXML;

public class WebserviceImpl implements Webservice {
	
	private final static String ACTION_LIST_SERVICE = "enActionListService";
	private final static String SIMPLE_DOCUMENT_ACTION_SERVICE = "simpleDocumentActionsService";
	private final static String NOTIFICATION_SERVICE = "sendNotificationKewXmlService";
	
	private SimpleDocumentActionsWebService simpleDocumentActionsWebService;
	private ActionListService actionListService;
	private KSBXMLService notificationService;
	
	protected void checkAuthorization() {
		
	}
	
	protected synchronized SimpleDocumentActionsWebService getSimpleDocumentActionsWebService() {
		if (simpleDocumentActionsWebService == null) {
	        simpleDocumentActionsWebService = (SimpleDocumentActionsWebService) GlobalResourceLoader.getService(SIMPLE_DOCUMENT_ACTION_SERVICE);
	    }
	    return simpleDocumentActionsWebService;
	}
	
	protected synchronized ActionListService getActionListService() {
	    if (actionListService == null) {
	        actionListService = (ActionListService) GlobalResourceLoader.getService(ACTION_LIST_SERVICE);
        }
        return actionListService;
    }

	protected synchronized KSBXMLService getNotificationService() {
        if (notificationService == null) {
            notificationService = (KSBXMLService)  GlobalResourceLoader.getService(NOTIFICATION_SERVICE);
        }
        return notificationService;
    }
	
	public String ping() {
	    checkAuthorization();
		String ping = ConfigContext.getCurrentContextConfig().getProperty("server.environment");
		return ping;
	}
	
	public String create(String initiatorId, String appDocId, String docType, String docTitle) {
	    checkAuthorization();
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().create(initiatorId, appDocId, docType, docTitle));
	}

	public StandardResponse save(String docId, String userId, String docTitle,
			String annotation) {
	    checkAuthorization();
	    // NOTE: Null was added for compatibility. It may nuke content though.
		return getSimpleDocumentActionsWebService().save(docId, userId, docTitle, null, annotation);
	}
	
	public DocumentResponse get(String docId, String userId) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().getDocument(docId, userId);
	}	
	
	public StandardResponse acknowledge(String docId, String userId,
			String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().acknowledge(docId, userId, annotation);
	}
	
	public StandardResponse approve(String docId, String userId,
			String docTitle, String docContent, String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().approve(docId, userId, docTitle, docContent, annotation);
	}
	
	public StandardResponse blanketApprove(String docId, String userId,
			String docTitle, String docContent, String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().blanketApprove(docId, userId, docTitle, docContent, annotation);
	}
	
	public StandardResponse cancel(String docId, String userId,
			String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().cancel(docId, userId, annotation);
	}
	
	public StandardResponse disapprove(String docId, String userId,
			String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().disapprove(docId, userId, annotation);
	}
	
	public StandardResponse fyi(String docId, String userId) {
		return getSimpleDocumentActionsWebService().fyi(docId, userId);
	}
	
	public StandardResponse route(String docId, String userId, String docTitle,
			String docContent, String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().route(docId, userId, docTitle, docContent, annotation);
	}
	
	public StandardResponse requestAdHocAckToGroup(String docId, String userId,
			String recipientGroupId, String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().requestAdHocAckToGroup(docId, userId, recipientGroupId, annotation);
	}
	
	public StandardResponse requestAdHocAckToUser(String docId, String userId,
			String recipientuserId, String annotation) {
        checkAuthorization();	    
		return getSimpleDocumentActionsWebService().requestAdHocAckToPrincipal(docId, userId, recipientuserId, annotation);
	}
	
	public StandardResponse requestAdHocApproveToGroup(String docId,
			String userId, String recipientGroupId, String annotation) {
        checkAuthorization();
		return getSimpleDocumentActionsWebService().requestAdHocApproveToGroup(docId, userId, recipientGroupId, annotation);
	}
	
	public StandardResponse requestAdHocApproveToUser(String docId,
			String userId, String recipientuserId, String annotation) {
        checkAuthorization();
		return getSimpleDocumentActionsWebService().requestAdHocApproveToPrincipal(docId, userId, recipientuserId, annotation);
	}
	
	public StandardResponse requestAdHocFyiToGroup(String docId, String userId,
			String recipientGroupId, String annotation) {
        checkAuthorization();
		return getSimpleDocumentActionsWebService().requestAdHocFyiToGroup(docId, userId, recipientGroupId, annotation);
	}

	public StandardResponse requestAdHocFyiToUser(String docId, String userId,
			String recipientuserId, String annotation) {
	    checkAuthorization();
		return getSimpleDocumentActionsWebService().requestAdHocFyiToPrincipal(docId, userId, recipientuserId, annotation);
	}	
	
	public UserInRouteLogResponse isUserInRouteLog(String docId, String userId) {
		checkAuthorization();
		return getSimpleDocumentActionsWebService().isUserInRouteLog(docId, userId);
	}
	
	public Collection<ActionItem> getUserInbox(String userId) {
	    checkAuthorization();
		Collection<ActionItem> actionItems = null;
		actionItems = getActionListService().getActionList(userId, null);
		return actionItems;
	}
	
	public int countUserInboxItems(String userId) {
	    checkAuthorization();
		int count = 0;
		count = getActionListService().getCount(userId);
		return count;
	}
	
	public Collection<ActionItem> findByRouteHeaderId(String routeHeaderIdStr) {
	    
		checkAuthorization();
		Collection<ActionItem> actionItem = null;
		try {
            Long routeHeaderId = Long.parseLong(routeHeaderIdStr);
            actionItem = getActionListService().findByRouteHeaderId(routeHeaderId);
	    }
	    catch ( NumberFormatException e ) {
	    	//LOG.error( "Expected Long type but got: " + routeHeaderIdStr );
	    }
	    return actionItem;

	}
	
	public void sendNotification(String message) {
	    checkAuthorization();
		try {
			getNotificationService().invoke(message);
		} catch (Exception e) {
			//LOG.error("CynergyService.sendNotification error: " + e.getMessage());
		}
	}
}
