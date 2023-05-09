package com.hy.photogram.web.dto.user;

import com.hy.photogram.domain.user.User;
import lombok.Data;

@Data
public class UserProfileDto {

    private User user;
    private boolean pageOwnerState;
    private boolean subscribeState;
    private int imageCount;
    private int subscribeCount;
}
