package advws.net.ricews.util;

import java.util.Collection;

import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionitem.ActionItemActionListExtension;

import org.kuali.rice.kim.impl.identity.PersonImpl;
import com.thoughtworks.xstream.XStream;

public class ConvertToXML {
	public static String convert(Object o) {
		XStream xstream = new XStream();
		xstream.alias("person", PersonImpl.class);
		
		return xstream.toXML(o);
	}
	
	public static String convertCollection(Collection<?> c) {
		XStream xstream = new XStream();
		xstream.alias("ActionItem", ActionItemActionListExtension.class);
		xstream.alias("ActionItem", ActionItem.class);

		String items = "<items>";
		
		for (Object t : c) {
			items += xstream.toXML(t);
		}
		
		items += "\n</items>";
		return items;
	}
}
