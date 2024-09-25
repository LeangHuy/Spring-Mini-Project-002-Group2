package com.config.service.serviceImpl;

import com.config.exception.ConflictException;
import com.config.model.request.GroupRequest;
import com.config.response.UserGroupResponse;
import com.config.response.UserResponse;
import com.config.response.GroupResponse;
import com.config.service.GroupService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;
    private final String realm;

    public GroupServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    @Override
    public UserGroupResponse AddUserToGroup(UUID groupId, UUID userId) {
        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(userId.toString());
        userResource.joinGroup(groupId.toString());

        UserResponse addUserResponse = new UserResponse();

        return null;
    }


    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.singleAttribute("group_id",UUID.randomUUID().toString());
        groupRepresentation.setName(groupRequest.getGroupName());
        Response response = groupsResource.add(groupRepresentation);
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            throw new ConflictException("This group name is already in existed");
        }
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(groupRepresentation.getAttributes().get("group_id").getFirst());
        groupResponse.setGroupName(groupRepresentation.getName());
        return groupResponse;
    }

    @Override
    public GroupResponse getGroupByID(UUID groupId) {
        return null;
    }
}
