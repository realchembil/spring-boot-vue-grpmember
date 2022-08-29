package com.lcm.lcmvuespring.api.model.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignGroupRequest {
    private List<Long> group_id;
    private Long user_id;
}