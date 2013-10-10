package advws.net.ricews.kim.person.service.impl;
 
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import advws.net.ricews.kim.person.service.RESTPersonService;
import advws.net.ricews.util.ConvertToXML;


public class RESTPersonServiceImpl implements RESTPersonService {

	public String getMe() {
		Person me =  KimApiServiceLocator.getPersonService().getPersonByPrincipalName("user1");
		return ConvertToXML.convert(me);
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
		Person me = (Person) KimApiServiceLocator.getPersonService().getPersonByExternalIdentifier(type, id).get(0);
		return ConvertToXML.convert(me);
	}

}
