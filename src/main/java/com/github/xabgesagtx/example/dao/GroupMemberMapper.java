package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.GroupMember;

public interface GroupMemberMapper {
    int insert(GroupMember record);

    int insertSelective(GroupMember record);

    int selectCountByGroupMember(GroupMember groupMember);

    int selectBlackKeywordByGroupMember(GroupMember groupMember);

    int updateBlackKeywordByGroupMember(GroupMember groupMember);
}