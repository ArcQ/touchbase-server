package com.kf.touchbase.service.app;

import com.kf.touchbase.domain.Base;
import com.kf.touchbase.domain.enumeration.Role;
import com.kf.touchbase.domain.utils.BaseUtils;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.repository.app.BaseRepositoryApp;
import com.kf.touchbase.service.BaseService;
import com.kf.touchbase.service.dto.BaseDTO;
import com.kf.touchbase.service.mapper.BaseMapper;
import io.micronaut.security.authentication.AuthenticationException;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@Transactional
public class BaseServiceApp {

    private final BaseMapper baseMapper;
    private final BaseRepositoryApp baseRepositoryApp;
    private final UserRepository userRepository;
    private final BaseService baseService;

    public BaseServiceApp(BaseMapper baseMapper, BaseRepositoryApp baseRepositoryApp,
                          UserRepository userRepository, BaseService baseService) {
        this.baseMapper = baseMapper;
        this.baseRepositoryApp = baseRepositoryApp;
        this.userRepository = userRepository;
        this.baseService = baseService;
    }

    public Base findIfMemberAdmin(String adminAuthKey, Long baseId) {
        return baseRepositoryApp.findIfMemberAdmin(baseId, adminAuthKey)
            .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthKey)));
    }

    public List<BaseDTO> getOwnBases(String adminAuthKey) {
        var base = baseRepositoryApp.findAllByMembersUserAuthKey(adminAuthKey);
        return baseMapper.toDto(base);
    }

    public BaseDTO getOwnBase(String adminAuthKey, Long baseId) {
        var base = baseRepositoryApp.findIfMember(baseId, adminAuthKey).orElse(null);
        return baseMapper.toDto(base);
    }

    @Transactional
    public BaseDTO createBase(String adminAuthKey, BaseDTO baseDTO) {
        var newBase = baseMapper.toEntity(baseDTO);
        var creator = userRepository.findByAuthKey(adminAuthKey).orElseThrow(AuthenticationException::new);
        newBase.setCreator(creator);
        BaseUtils.addMember(newBase, creator, Role.ADMIN);
        baseRepositoryApp.mergeAndSave(newBase);
        return baseMapper.toDto(newBase);
    }

    public BaseDTO updateBase(String adminAuthKey, BaseDTO updateBaseDto) throws SecurityException {
        findIfMemberAdmin(adminAuthKey, updateBaseDto.getId());
        return baseService.save(updateBaseDto);
    }

    public void makeBaseInactive(String adminAuthKey, Long baseId) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.setIsActive(false);
        baseRepositoryApp.save(existingBase);
    }
}
