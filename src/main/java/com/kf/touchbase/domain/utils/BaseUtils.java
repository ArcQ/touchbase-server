package com.kf.touchbase.domain.utils;

import com.kf.touchbase.domain.Base;
import com.kf.touchbase.domain.BaseMember;
import com.kf.touchbase.domain.User;
import com.kf.touchbase.domain.enumeration.Role;

public class BaseUtils {
    public static void addMember(Base base, User user, Role role) {
        var member =  new BaseMember().role(Role.ADMIN).member(user).base(base);
        base.getMembers().add(member);
    }

    public static void mergeInNotNull(Base base, Base newBase) {
        base.name((newBase.getName() == null) ? newBase.getName() : base.getName())
            .imageUrl((newBase.getImageUrl() == null) ? newBase.getImageUrl() : base.getImageUrl());
    }
}
