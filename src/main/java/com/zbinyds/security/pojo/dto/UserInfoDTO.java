package com.zbinyds.security.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Package: com.zbinyds.security.pojo.dto
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/27 19:52
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = -8879573042676882665L;

    private String userId;

    private String token;
}
