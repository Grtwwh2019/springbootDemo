package com.grtwwh2019.vhr.utils;

import com.grtwwh2019.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

public class HrUtils {

    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
