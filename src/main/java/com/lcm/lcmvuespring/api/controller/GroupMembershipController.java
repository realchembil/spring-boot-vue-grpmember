package com.lcm.lcmvuespring.api.controller;

import com.lcm.lcmvuespring.api.model.Owner;
import com.lcm.lcmvuespring.api.model.SecurityGroup;
import com.lcm.lcmvuespring.api.model.User;
import com.lcm.lcmvuespring.api.model.request.AssignGroupRequest;
import com.lcm.lcmvuespring.api.model.request.OwnerCreationRequest;
import com.lcm.lcmvuespring.api.model.request.SecurityGroupCreationRequest;
import com.lcm.lcmvuespring.api.model.request.UserCreationRequest;
import com.lcm.lcmvuespring.api.service.GroupMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/groupmembership")
@RequiredArgsConstructor
public class GroupMembershipController {
    private final GroupMembershipService groupMembershipService;


    @GetMapping("/secgroups")
    public ResponseEntity readGroups(@RequestParam(required = false) String samaccountName) {
        if (samaccountName == null) {
            return ResponseEntity.ok(groupMembershipService.readGroups());
        }
        return ResponseEntity.ok(groupMembershipService.readGroup(samaccountName));
    }

    @GetMapping("/secgroups/{groupId}")
    public ResponseEntity<SecurityGroup> readGroup (@PathVariable Long groupId) {
        return ResponseEntity.ok(groupMembershipService.readGroup(groupId));
    }

    @PostMapping("/secgroup")
    public ResponseEntity<SecurityGroup> createBook (@RequestBody SecurityGroupCreationRequest request) {
        return ResponseEntity.ok(groupMembershipService.createSecurityGroup(request));
    }

    @DeleteMapping("/secgroups/{secgroupId}")
    public ResponseEntity<Void> deleteGroup (@PathVariable Long secgroupId) {
      groupMembershipService.deleteGroup(secgroupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser (@RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(groupMembershipService.createUser(request));
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<User> updateUser (@RequestBody UserCreationRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok(groupMembershipService.updateUser(userId, request));
    }

    @PostMapping("/secgroup/assign")
    public ResponseEntity<List<String>> assignAGroup(@RequestBody AssignGroupRequest assignGroupRequest) {
        return ResponseEntity.ok(groupMembershipService.assignGroup(assignGroupRequest));
    }

    @PostMapping("/owner")
    public ResponseEntity<Owner> createOwner (@RequestBody OwnerCreationRequest ownerCreationRequest) {
        return ResponseEntity.ok(groupMembershipService.createOwner(ownerCreationRequest));
    }

}

