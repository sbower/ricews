package advws.net.ricews.util;

import org.kuali.rice.kim.bo.impl.PersonImpl;
import com.thoughtworks.xstream.XStream;

public class ConvertToXML {
	public static String convert(Object o) {
		XStream xstream = new XStream();
		xstream.alias("person", PersonImpl.class);
		
		return xstream.toXML(o);
	}
}
