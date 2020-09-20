package com.kf.touchbase.service.app;

import com.kf.touchbase.domain.Base;
import com.kf.touchbase.domain.BaseMember;
import com.kf.touchbase.domain.User;
import com.kf.touchbase.domain.enumeration.Role;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.repository.app.BaseRepositoryApp;
import com.kf.touchbase.service.mapper.BaseMapper;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Singleton
@Transactional
public class BaseServiceApp {

    private final BaseMapper baseMapper;
    private final BaseRepositoryApp baseRepositoryApp;
    private final UserRepository userRepository;

    public BaseServiceApp(BaseMapper baseMapper, BaseRepositoryApp baseRepositoryApp, UserRepository userRepository) {
        this.baseMapper = baseMapper;
        this.baseRepositoryApp = baseRepositoryApp;
        this.userRepository = userRepository;
    }

    public Base findIfMemberAdmin(String adminAuthKey, Long baseId) {
        return baseRepositoryApp.findIfMemberAdmin(baseId, adminAuthKey)
            .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthKey)));
    }

    public List<Base> getOwnBases(String adminAuthKey) {
        var bases = baseRepositoryApp.findAllByMembersUserAuthKey(adminAuthKey);
        return bases;
    }

    public Base getBase(String adminAuthKey, Long baseId) {
        return baseRepositoryApp.findIfMember(baseId, adminAuthKey).orElse(null);
    }

    @Transactional
    public Base createBase(String adminAuthKey, Base newBase) {
        var creator = userRepository.findByAuthKey(adminAuthKey).orElseThrow(AuthenticationException::new);
        newBase.setCreator(creator);
        newBase.setAdmins(Set.of(creator));
        newBase.addMember(creator, Role.ADMIN);
        return baseRepositoryApp.save(newBase);
    }

    public Base patchBase(String adminAuthKey, Long baseId, Base updateBase) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.mergeInNotNull(updateBase);
        return baseRepositoryApp.save(existingBase);
    }

    public Base addMember(String adminAuthKey, MemberRef memberRef, Long baseId, Role role) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        User user = userRepository.findById(memberRef.getUserId()).orElseThrow();
        existingBase.addMember(user, role);
        baseRepositoryApp.save(existingBase);
        return existingBase;
    }

    public Base removeMembers(String adminAuthKey, List<MemberRef> memberRefs,
                              Long baseId) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        memberRefs.forEach((memberRef) -> {
            existingBase.removeMember(memberRef.getUserId());
        });
        baseRepository.save(existingBase);
        return existingBase;
    }

    public Success makeBaseInactive(String adminAuthKey, Long baseId) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return null;
    }
}
