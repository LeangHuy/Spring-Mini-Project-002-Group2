package com.config.service.serviceImpl;

import com.config.exception.ConflictException;
import com.config.exception.NotFoundException;
import com.config.model.request.GroupRequest;
import com.config.response.GroupUserResponse;
import com.config.response.UserGroupResponse;
import com.config.response.UserResponse;
import com.config.response.GroupResponse;
import com.config.service.GroupService;
import com.config.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;
    private final String realm;
    private final UserService userService;

    public GroupServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm, UserService userService) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.userService = userService;
    }

    @Override
    public UserGroupResponse AddUserToGroup(UUID groupId, UUID userId) {
        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(userId.toString());
        userResource.joinGroup(groupId.toString());

        UserResponse userResponse = userService.getUserById(userId.toString());
        GroupResponse groupResponse = getGroupByID(groupId);
        UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.setGroup(groupResponse);
        userGroupResponse.setUser(userResponse);
        return userGroupResponse;
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
        GroupRepresentation groupResource;
        try {
            groupResource = keycloak.realm(realm).groups().group(groupId.toString()).toRepresentation();
        }catch (Exception e) {
            throw new NotFoundException("Group not found");
        }
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(groupResource.getId());
        groupResponse.setGroupName(groupResource.getName());
        return groupResponse;
    }

    @Override
    public List<GroupResponse> getAllGroup() {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupResponse> groupResponseList = new ArrayList<>();
        for (GroupRepresentation groupRepresentation : groupsResource.groups()) {
            GroupResponse groupResponse = new GroupResponse();
            groupResponse.setGroupId(groupRepresentation.getId());
            groupResponse.setGroupName(groupRepresentation.getName());
            groupResponseList.add(groupResponse);
        }
        return groupResponseList;
    }

    @Override
    public GroupResponse updateGroupByGroupID(UUID groupId, GroupRequest groupRequest) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(groupId.toString());
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        groupRepresentation.setName(groupRequest.getGroupName());
        groupResource.update(groupRepresentation);
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(groupRepresentation.getId());
        groupResponse.setGroupName(groupRepresentation.getName());
        return groupResponse;
    }

    @Override
    public void DeletedGroupByGroupID(UUID groupId) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(groupId.toString());
        groupResource.remove();
    }

    @Override
    public GroupUserResponse getAllGroupUser(String groupId) {
        List<UserRepresentation> userRepresentations;
        try{
            GroupResource groupResource = keycloak.realm(realm).groups().group(groupId);
            userRepresentations = groupResource.members();
        }catch (Exception e) {
            throw new NotFoundException("Group not found");
        }
        GroupUserResponse groupUserResponse = new GroupUserResponse();
        List<UserResponse> usersList = new ArrayList<>();
        for (UserRepresentation userRepresentation : userRepresentations) {
            UserResponse userResponse = userService.getUserById(userRepresentation.getId());
            usersList.add(userResponse);
        }
        groupUserResponse.setUsers(usersList);
        groupUserResponse.setGroup(getGroupByID(UUID.fromString(groupId)));

        return groupUserResponse;
    }


}
