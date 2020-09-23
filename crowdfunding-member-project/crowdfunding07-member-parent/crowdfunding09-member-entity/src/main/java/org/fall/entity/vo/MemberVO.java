package org.fall.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberVO {
    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String phoneNum;

    private String code;

}
