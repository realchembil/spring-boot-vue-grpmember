package com.lcm.lcmvuespring.api.model.request;

import lombok.Data;

@Data
public class SecurityGroupCreationRequest {
    private String displayName;
    private String samAccountName;
    private Long owner_id;
}