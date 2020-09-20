package com.kf.touchbase.service.app;

import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.service.BaseService;
import com.kf.touchbase.service.mapper.BaseMapper;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
public class BaseServiceApp extends BaseService {
    public BaseServiceApp(BaseRepository baseRepository, BaseMapper baseMapper) {
        super(baseRepository, baseMapper);
    }
}
