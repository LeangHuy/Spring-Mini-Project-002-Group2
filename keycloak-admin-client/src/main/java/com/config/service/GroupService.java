package com.config.service;

import com.config.model.request.GroupRequest;
import com.config.response.GroupUserResponse;
import com.config.response.UserGroupResponse;
import com.config.response.GroupResponse;
import io.swagger.v3.oas.annotations.servers.Server;

import java.util.List;
import java.util.UUID;

@Server
public interface GroupService {

    UserGroupResponse AddUserToGroup(UUID groupId, UUID userId);

    GroupResponse createGroup(GroupRequest groupRequest);


    GroupResponse getGroupByID(UUID groupId);

    List<GroupResponse> getAllGroup();

    GroupResponse updateGroupByGroupID(UUID groupId, GroupRequest groupRequest);

    void DeletedGroupByGroupID(UUID groupId);

    GroupUserResponse getAllGroupUser(String groupId);
}
