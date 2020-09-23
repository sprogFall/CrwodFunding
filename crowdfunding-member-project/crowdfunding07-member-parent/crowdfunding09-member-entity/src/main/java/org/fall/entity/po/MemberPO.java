package org.fall.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberPO {
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private Integer authstaus;

    private Integer userType;

    private String realName;

    private String cardNum;

    private Integer acctType;



}