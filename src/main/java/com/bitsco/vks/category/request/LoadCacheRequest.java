package com.bitsco.vks.category.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadCacheRequest {
    private boolean param;
    private boolean user;
    private boolean area;
    private boolean evnPc;
}
