package advws.net.ricews.kim.person;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import advws.net.ricews.util.ConvertToXML;


public class RESTPersonServiceImpl implements RESTPersonService {

	public String getMe() {
		Person me =  KIMServiceLocator.getPersonService().getPersonByPrincipalName("user1");
		return ConvertToXML.convert(me);
	}

	public String getPersonByPrincipalName(String prncpl_nm) {
		Person me =  KIMServiceLocator.getPersonService().getPersonByPrincipalName(prncpl_nm);
		return ConvertToXML.convert(me);
	}

	public String getPersonByEmployeeId(String emplid) {
		Person me =  KIMServiceLocator.getPersonService().getPersonByEmployeeId(emplid);
		return ConvertToXML.convert(me);
	}

	public String getPersonByPrincipalID(String prncpl_id) {
		Person me =  KIMServiceLocator.getPersonService().getPerson(prncpl_id);
		return ConvertToXML.convert(me);
	}

	public String getPersonByExternalIdentifier(String type, String id) {
		Person me = (Person) KIMServiceLocator.getPersonService().getPersonByExternalIdentifier(type, id).get(0);
		return ConvertToXML.convert(me);
	}

}
