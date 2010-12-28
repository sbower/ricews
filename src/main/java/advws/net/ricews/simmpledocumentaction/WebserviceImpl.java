package advws.net.ricews.simmpledocumentaction;

import java.util.Collection;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionlist.service.ActionListService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.ksb.messaging.service.KSBXMLService;

import advws.net.ricews.util.ConvertToXML;

public class WebserviceImpl implements Webservice {
	
	protected final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(getClass());
	
	private final static String ACTION_LIST_SERVICE = "enActionListService";
	private final static String SIMPLE_DOCUMENT_ACTION_SERVICE = "simpleDocumentActionsService";
	private final static String NOTIFICATION_SERVICE = "sendNotificationKewXmlService";
		
	private SimpleDocumentActionsWebService simpleDocumentActionsWebService;
	private ActionListService actionListService;
	private KSBXMLService notificationService;
	
	private String authorizationGroup;
	private String authorizationGroupNS = "KUALI";
	private String userHeader = "REMOTE_USER";
	private String delegatorHeader = "REMOTE_USER";
	
	public void setAuthorizationGroup(String group) {
	    this.authorizationGroup = group;
	}
	
	public String getAuthorizationGroup() {
		return this.authorizationGroup;
	}
	
	public void setAuthorizationGroupNS(String groupNS) {
	    this.authorizationGroupNS = groupNS;
	}
	
	public String getAuthorizationGroupNS() {
	   return this.authorizationGroupNS;
	}
	
	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}

	public String getUserHeader() {
		return userHeader;
	}

	public void setDelegatorHeader(String delegatorHeader) {
		this.delegatorHeader = delegatorHeader;
	}

	public String getDelegatorHeader() {
		return delegatorHeader;
	}
	
	protected void checkAuthorization(HttpHeaders hh){
		checkAuthorization(hh, null); 
	}
	
	protected void checkAuthorization(HttpHeaders hh, String docID) {
        String user = null;
        //String delegator = null;
                        
        if (!StringUtils.isBlank(authorizationGroup)) {
            LOG.debug("Checking authenticated id against group: '" + authorizationGroup + "'");
            user = hh.getRequestHeader(this.userHeader).get(0);
            
            if (user == null) {
                throw new SecurityException("User not authenticated");
            }
            
        	// if either id is authorized then the call is authorized
        	boolean authorized = false;
        	
        	/*if (delegator != null) {
        	    KimPrincipalInfo p = KIMServiceLocator.getIdentityService().getPrincipalByPrincipalName(user);
        		authorized = KIMServiceLocator.getIdentityManagementService().isMemberOfGroup(p.getPrincipalId(), authorizationGroupNS, authorizationGroup);
        	}*/
        	if (!authorized) {
                KimPrincipalInfo p = KIMServiceLocator.getIdentityService().getPrincipalByPrincipalName(user);
        		authorized = KIMServiceLocator.getIdentityManagementService().isMemberOfGroup(p.getPrincipalId(), authorizationGroupNS, authorizationGroup);
        	}
        	
        	KimPrincipalInfo p = KIMServiceLocator.getIdentityService().getPrincipalByPrincipalName(user);
        	
        	if (authorized &&
        			docID != null) {
        		
        		WorkflowDocument workflowDocument = null;
        	
				try {
					workflowDocument = new WorkflowDocument(user, Long.decode(docID));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (WorkflowException e) {
					e.printStackTrace();
				}
                	
            	AttributeSet permissionDetails = new AttributeSet();
            	permissionDetails.put("documentTypeName", workflowDocument.getDocumentType());
            	
				if (KIMServiceLocator.getPermissionService().isPermissionDefined(
							"KR-SYS", "Document API", permissionDetails)) {
					authorized = KIMServiceLocator.getPermissionService().hasPermission(p.getPrincipalId(), "KR-SYS", "Document API", permissionDetails);
				}
        
        	}
        	
            if (!authorized) {
                throw new SecurityException("User " + user + " not authorized");
            }
        }
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
	    //checkAuthorization(hh);
		String ping = "PING: " + ConfigContext.getCurrentContextConfig().getProperty("app.context.name");
		return ping;
	}
	
	public String create(HttpHeaders hh, String initiatorId, String appDocId, String docType, String docTitle) {
	    checkAuthorization(hh);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().create(initiatorId, appDocId, docType, docTitle));
	}

	public String save(HttpHeaders hh, String docID, String userID, String docTitle, String annotation) {
	    checkAuthorization(hh, docID);
	    
	    // NOTE: Null was added for compatibility. It may nuke content though.
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().save(docID, userID, docTitle, null, annotation));
	}
	
	public String get(HttpHeaders hh, String docID, String userID) {
	    checkAuthorization(hh, docID);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().getDocument(docID, userID));
	}	
	
	public String acknowledge(HttpHeaders hh, String docId, String userId,
			String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().acknowledge(docId, userId, annotation));
	}
	
	public String approve(HttpHeaders hh, String docId, String userId,
			String docTitle, String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().approve(docId, userId, docTitle, null, annotation));
	}
	
	public String blanketApprove(HttpHeaders hh, String docId, String userId,
			String docTitle, String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().blanketApprove(docId, userId, docTitle, null, annotation));
	}
	
	public String cancel(HttpHeaders hh, String docId, String userId,
			String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().cancel(docId, userId, annotation));
	}
	
	public String disapprove(HttpHeaders hh, String docId, String userId,
			String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().disapprove(docId, userId, annotation));
	}
	
	public String fyi(HttpHeaders hh, String docId, String userId) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().fyi(docId, userId));
	}
	
	public String route(HttpHeaders hh, String docId, String userId, String docTitle, String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().route(docId, userId, docTitle, null, annotation));
	}
	
	public String requestAdHocAckToGroup(HttpHeaders hh, String docId, String userId,
			String recipientGroupId, String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocAckToGroup(docId, userId, recipientGroupId, annotation));
	}
	
	public String requestAdHocAckToUser(HttpHeaders hh, String docId, String userId,
			String recipientuserId, String annotation) {
        checkAuthorization(hh, docId);	    
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocAckToPrincipal(docId, userId, recipientuserId, annotation));
	}
	
	public String requestAdHocApproveToGroup(HttpHeaders hh, String docId,
			String userId, String recipientGroupId, String annotation) {
        checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocApproveToGroup(docId, userId, recipientGroupId, annotation));
	}
	
	public String requestAdHocApproveToUser(HttpHeaders hh, String docId,
			String userId, String recipientuserId, String annotation) {
        checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocApproveToPrincipal(docId, userId, recipientuserId, annotation));
	}
	
	public String requestAdHocFyiToGroup(HttpHeaders hh, String docId, String userId,
			String recipientGroupId, String annotation) {
        checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocFyiToGroup(docId, userId, recipientGroupId, annotation));
	}

	public String requestAdHocFyiToUser(HttpHeaders hh, String docId, String userId,
			String recipientuserId, String annotation) {
	    checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().requestAdHocFyiToPrincipal(docId, userId, recipientuserId, annotation));
	}	
	
	public String isUserInRouteLog(HttpHeaders hh, String docId, String userId) {
		checkAuthorization(hh, docId);
		return ConvertToXML.convert(getSimpleDocumentActionsWebService().isUserInRouteLog(docId, userId));
	}
	
	public String getUserInbox(HttpHeaders hh, String userId) {
	    checkAuthorization(hh);
		Collection<ActionItem> actionItems = null;
		actionItems = getActionListService().getActionList(userId, null);
		return ConvertToXML.convertCollection(actionItems);
	}
	
	public int countUserInboxItems(HttpHeaders hh, String userId) {
	    checkAuthorization(hh);
		int count = 0;
		count = getActionListService().getCount(userId);
		return count;
	}
	
	public String findByRouteHeaderId(HttpHeaders hh, String routeHeaderIdStr) {
	    
		checkAuthorization(hh);
		Collection<ActionItem> actionItem = null;
		try {
            Long routeHeaderId = Long.parseLong(routeHeaderIdStr);
            actionItem = getActionListService().findByRouteHeaderId(routeHeaderId);
	    }
	    catch ( NumberFormatException e ) {
	    	LOG.error( "Expected Long type but got: " + routeHeaderIdStr );
	    }
	    return ConvertToXML.convertCollection(actionItem);

	}
	
	public void sendNotification(HttpHeaders hh, String message) {
	    checkAuthorization(hh);
		try {
			getNotificationService().invoke(message);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
