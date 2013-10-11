package net.advws.ricews.kim.permission.service.impl;

import java.util.HashMap;
import java.util.List;

import net.advws.ricews.kim.permission.service.RestPermissionService;

import org.kuali.rice.kim.api.common.assignee.Assignee;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class RestPermissionServiceImpl implements RestPermissionService {

    public List<Assignee> getPrincipalsByPermission(String permission,
            String namespace) {
        List<Assignee> assignees = KimApiServiceLocator.getPermissionService()
                .getPermissionAssignees(namespace, permission, new HashMap<String,String>());
        return assignees;
    }

}
