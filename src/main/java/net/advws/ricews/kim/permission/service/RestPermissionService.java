package net.advws.ricews.kim.permission.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.kuali.rice.kim.api.common.assignee.Assignee;


@Path("/")
public interface RestPermissionService {
    
    @GET
    @Produces("text/xml")
    @Path("/prinicpalsbypermission/{permission}/namespace/{namespace}")
    public List<Assignee> getPrincipalsByPermission(@PathParam("permission") String permission,
                                              @PathParam("namespace") String namespace);
}
