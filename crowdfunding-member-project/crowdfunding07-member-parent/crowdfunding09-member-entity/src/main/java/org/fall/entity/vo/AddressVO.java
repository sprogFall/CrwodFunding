package org.fall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {
    private Integer id;

    private String receiveName;

    private String phoneNum;

    private String address;

    private Integer memberId;

}
