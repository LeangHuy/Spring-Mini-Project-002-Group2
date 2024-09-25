package com.config.service.serviceImpl;

import com.config.model.entity.Group;
import com.config.model.request.GroupRequest;
import com.config.response.GroupResponse;
import com.config.service.GroupService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;
    private final String realm;

    public GroupServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm, ModelMapper modelMapper) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    @Override
    public Group AddUserToGroup(UUID groupId, UUID userId) {
        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(userId.toString());
        userResource.joinGroup(groupId.toString());
        Group group = new Group();
        group.setGroupId(groupId);
        return group;
    }


    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {
        RealmResource groupsResource = keycloak.realm(realm);
        GroupRepresentation newGroup = new GroupRepresentation();
        newGroup.setName(groupRequest.getGroupName());
        groupsResource.groups().add(newGroup);

        List<GroupRepresentation> allGroups = groupsResource.groups().groups();
        String groupId = null;
        for (GroupRepresentation group : allGroups) {
            if (group.getName().equals(groupRequest.getGroupName())) {
                groupId = group.getId();
                break;
            }
        }

        if (groupId == null) {
            throw new IllegalStateException("Group creation failed, group ID not found.");
        }
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(groupId);
        groupResponse.setGroupName(groupRequest.getGroupName());
        return groupResponse;
    }


    @Override
    public Group getCurrentGroup(String userId) {
        GroupRepresentation groupRepresentation = keycloak.realm(realm)
                .users()
                .get(userId)
                .groups()
                .stream()
                .findFirst() // Get the first group the user belongs to
                .orElseThrow(() -> new RuntimeException("User does not belong to any groups"));

        // Map GroupRepresentation to Group
        Group group = new Group();
        group.setGroupId(UUID.fromString(groupRepresentation.getId()));
        group.setName(groupRepresentation.getName());
        return group;
    }
}
