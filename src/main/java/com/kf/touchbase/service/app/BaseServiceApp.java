package com.kf.touchbase.service.app;

import com.kf.touchbase.domain.Base;
import com.kf.touchbase.domain.User;
import com.kf.touchbase.domain.enumeration.Role;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.service.BaseService;
import com.kf.touchbase.service.mapper.BaseMapper;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Singleton
@Transactional
public class BaseServiceApp extends BaseService {

    UserRepository userRepository;

    public BaseServiceApp(BaseRepository baseRepository, BaseMapper baseMapper, UserRepository userRepository) {
        super(baseRepository, baseMapper);
    }

    public Base findIfMemberAdmin(String adminAuthKey, Long baseId) {
        return baseRepository.findIfMemberAdmin(baseId, adminAuthKey)
            .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthKey)));
    }

    public List<Base> getOwnBases(String adminAuthKey) {
        var bases = baseRepository.findAllByMembersUserAuthKey(adminAuthKey);
        return bases;
    }

    public Base getBase(String adminAuthKey, Long baseId) {
        return baseRepository.findIfMember(baseId, adminAuthKey).orElse(null);
    }

    @Transactional
    public Base createBase(String adminAuthKey, Base newBase) {
        var creator = userRepository.findByAuthKey(adminAuthKey).orElseThrow(AuthenticationException::new);
        newBase.setCreator(creator);
        newBase.setAdmins(Set.of(creator));
        newBase.addMember(creator, Role.ADMIN);
        return baseRepository.save(newBase);
    }

    public Base patchBase(String adminAuthKey, Long baseId, Base updateBase) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.mergeInNotNull(updateBase);
        return baseRepository.save(existingBase);
    }

    public Base addMember(String adminAuthKey, MemberRef memberRef, Long baseId, Role role) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        User user = userRepository.findById(memberRef.getUserId()).orElseThrow();
        existingBase.addMember(user, role);
        baseRepository.save(existingBase);
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
