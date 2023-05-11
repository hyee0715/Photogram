package com.hy.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDto {

    private BigInteger id;
    private String username;
    private String profile_image_url;
    private BigInteger subscribe_state;
    private BigInteger equal_user_state;
}
