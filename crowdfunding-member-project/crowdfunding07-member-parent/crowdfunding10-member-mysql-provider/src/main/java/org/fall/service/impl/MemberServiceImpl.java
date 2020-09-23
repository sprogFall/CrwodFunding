package org.fall.service.impl;

import org.fall.entity.po.MemberPO;
import org.fall.entity.po.MemberPOExample;
import org.fall.mapper.MemberPOMapper;
import org.fall.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberPOMapper memberPOMapper;

    // 通过loginAcct获取与数据库对应的MemberPO对象
    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {

        MemberPOExample example = new MemberPOExample();

        MemberPOExample.Criteria criteria = example.createCriteria();

        criteria.andLoginAcctEqualTo(loginAcct);

        List<MemberPO> memberPOS = memberPOMapper.selectByExample(example);

        // 判断得到的List是否为空，为空则返回null，防止后面调用的时候触发空指针异常
        if (memberPOS == null || memberPOS.size() == 0){
            return null;
        }

        // List非空，则返回第一个（因为LoginAcct是唯一的）
        MemberPO memberPO = memberPOS.get(0);
        return memberPO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveMember(MemberPO memberPO) {
            memberPOMapper.insertSelective(memberPO);
    }
}
