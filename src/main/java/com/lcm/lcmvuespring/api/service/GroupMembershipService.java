package com.lcm.lcmvuespring.api.service;

import com.lcm.lcmvuespring.api.model.*;
import com.lcm.lcmvuespring.api.model.request.AssignGroupRequest;
import com.lcm.lcmvuespring.api.model.request.OwnerCreationRequest;
import com.lcm.lcmvuespring.api.model.request.SecurityGroupCreationRequest;
import com.lcm.lcmvuespring.api.model.request.UserCreationRequest;
import com.lcm.lcmvuespring.api.repository.AssignRepository;
import com.lcm.lcmvuespring.api.repository.OwnerRepository;
import com.lcm.lcmvuespring.api.repository.SecurityGroupRepository;
import com.lcm.lcmvuespring.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final AssignRepository assignRepository;
    private final SecurityGroupRepository securityGroupRepository;

    public SecurityGroup readGroup(Long id){
        Optional<SecurityGroup> securityGroup = securityGroupRepository.findById(id);
        if(securityGroup.isPresent()){
            return securityGroup.get();
        }
        throw new EntityNotFoundException("Can't find any group under given id");
    }

    public List<SecurityGroup> readGroups(){
        return securityGroupRepository.findAll();
    }

    public SecurityGroup readGroup(String samaccountname){
        Optional<SecurityGroup> securityGroup = securityGroupRepository.findBySamAccountName(samaccountname);
        if(securityGroup.isPresent()){
            return securityGroup.get();
        }
        throw new EntityNotFoundException("Can't find any group under given samaccountname");
    }

    public SecurityGroup createSecurityGroup(SecurityGroupCreationRequest group){
        Optional<Owner> owner = ownerRepository.findById(group.getOwner_id());
        if(!owner.isPresent()){
            throw new EntityNotFoundException("Owner not found");
        }
        SecurityGroup groupToCreate = new SecurityGroup();
        BeanUtils.copyProperties(group,groupToCreate);

        groupToCreate.setOwner(owner.get());
        return securityGroupRepository.save(groupToCreate);
    }

    public void deleteGroup(Long id){
        securityGroupRepository.deleteById(id);
    }

    public User createUser(UserCreationRequest userRequest){
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);

    }

    public User updateUser(Long id,UserCreationRequest userCreationRequest){
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new EntityNotFoundException("User not found in database");
        }
        User user = optionalUser.get();
        user.setLastName(userCreationRequest.getLastName());
        user.setFirstName(userCreationRequest.getFirstName());
        return userRepository.save(user);
    }

    public Owner createOwner(OwnerCreationRequest ownerCreationRequest){
        Owner owner = new Owner();
        BeanUtils.copyProperties(ownerCreationRequest,owner);
        return ownerRepository.save(owner);
    }

    public List<String> assignGroup(AssignGroupRequest request){

        Optional<User> userForId = userRepository.findById(request.getUser_id());
        if(!userForId.isPresent()){
            throw new EntityNotFoundException("Can't find a user with the given id");
        }
        User user = userForId.get();
        if(user.getStatus()!= UserStatus.ACTIVE){
            throw new RuntimeException("User not Active to proceed with Assignment");
        }

        List<String> groupsApprovedToAsssign = new ArrayList<>();

        request.getGroup_id().forEach(groupId -> {
            Optional<SecurityGroup> groupForId = securityGroupRepository.findById(groupId);
            if(!groupForId.isPresent()){
                throw new EntityNotFoundException("Can't find a group with the given id");
            }


            Optional<Assign> assignedGroup = assignRepository.findByGroupAndAssignstatus(groupForId.get(),AssignStatus.ASSIGNED);
            System.out.println("assignedGroup"+assignedGroup.toString());
            if(!assignedGroup.isPresent()){
                groupsApprovedToAsssign.add(groupForId.get().getSamAccountName());
                Assign assign = new Assign();
                assign.setUser(userForId.get());
               // assign.setSecurityGroup(groupForId.get());
                assign.setGroup(groupForId.get());
                assign.setAssignstatus(AssignStatus.ASSIGNED);
                assign.setStartOn(Instant.now());
                assign.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                assignRepository.save(assign);

            }

        });
        return groupsApprovedToAsssign;
    }



}
