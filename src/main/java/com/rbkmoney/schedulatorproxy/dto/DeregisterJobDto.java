package com.rbkmoney.schedulatorproxy.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DeregisterJobDto {

    @NotEmpty
    private String jobId;

}
