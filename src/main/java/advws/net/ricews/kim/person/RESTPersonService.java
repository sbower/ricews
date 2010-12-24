package advws.net.ricews.kim.person;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.kuali.rice.kim.bo.Person;

@Path("/")
public interface RESTPersonService {
	
    @GET
    @Produces("text/xml")
    public String getMe();
    
    @GET
    @Path("/Person/byprincipalname/{prncpl_nm}")
    @Produces("text/xml")
    public String getPersonByPrincipalName(@PathParam("prncpl_nm") String prncpl_nm);
    
    @GET
    @Path("/Person/byemployeeid/{emplid}")
    @Produces("text/xml")
    public String getPersonByEmployeeId(@PathParam("emplid") String emplid); 
    
    @GET
    @Path("/Person/byprincipalid/{prncpl_id}")
    @Produces("text/xml")
    public String getPersonByPrincipalID(@PathParam("prncpl_id") String prncpl_id); 
    
    @GET
    @Path("/Person/byexternalid/type/{type}/id/{id}")
    @Produces("text/xml")
    public String getPersonByExternalIdentifier(@PathParam("type") String type, @PathParam("id") String id); 
}
