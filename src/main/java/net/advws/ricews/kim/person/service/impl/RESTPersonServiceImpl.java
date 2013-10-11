package net.advws.ricews.kim.person.service.impl;
 
import java.util.Collection;

import net.advws.ricews.kim.person.service.RESTPersonService;
import net.advws.ricews.util.ConvertToXML;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;


public class RESTPersonServiceImpl implements RESTPersonService {

    /*KRADServiceLocator.getBusinessObjectSerializerService()
                    .serializeBusinessObjectToXml(me)
    */
    
	public String getMe() {	    
	    return getPersonByPrincipalName(GlobalVariables.getUserSession().getPrincipalName());
	}

	public String getPersonByPrincipalName(String prncpl_nm) {
		Person me =  KimApiServiceLocator.getPersonService().getPersonByPrincipalName(prncpl_nm);
		return ConvertToXML.convert(me);
	}

	public String getPersonByEmployeeId(String emplid) {
		Person me =  KimApiServiceLocator.getPersonService().getPersonByEmployeeId(emplid);
		return ConvertToXML.convert(me);
	}

	public String getPersonByPrincipalID(String prncpl_id) {
		Person me =  KimApiServiceLocator.getPersonService().getPerson(prncpl_id);
		return ConvertToXML.convert(me);
	}

	public String getPersonByExternalIdentifier(String type, String id) {
		Collection<Person> peeps = KimApiServiceLocator.getPersonService().getPersonByExternalIdentifier(type, id);
		return ConvertToXML.convertCollection(peeps);
	}

}
