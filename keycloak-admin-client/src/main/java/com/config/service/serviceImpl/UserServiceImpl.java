package com.config.service.serviceImpl;

import com.config.exception.ConflictException;
import com.config.model.request.UserRequest;
import com.config.response.UserResponse;
import com.config.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    private final ModelMapper modelMapper;

    public UserServiceImpl(Keycloak keycloak, ModelMapper modelMapper) {
        this.keycloak = keycloak;
        this.modelMapper = modelMapper;
    }

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        UserRepresentation representation = prepareUserRepresentation(userRequest, preparePasswordRepresentation(userRequest.getPassword()));
        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(representation);
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            throw new ConflictException("This email is already registered");
        }
        UserRepresentation userRepresentation = usersResource.get(CreatedResponseUtil.getCreatedId(response)).toRepresentation();
        UserResponse userResponse = modelMapper.map(userRepresentation, UserResponse.class);
        userResponse.setCreatedAt(userRepresentation.getAttributes().get("createdAt").getFirst());
        userResponse.setLastModifiedAt(userRepresentation.getAttributes().get("lastModifiedAt").getFirst());
        return userResponse;
    }

    private UserRepresentation prepareUserRepresentation(UserRequest userRequest, CredentialRepresentation credentialRepresentation) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.singleAttribute("createdAt", String.valueOf(LocalDateTime.now()));
        userRepresentation.singleAttribute("lastModifiedAt", String.valueOf(LocalDateTime.now()));
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }
}
