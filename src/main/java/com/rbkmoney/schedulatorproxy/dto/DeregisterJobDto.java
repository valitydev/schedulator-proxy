package com.rbkmoney.schedulatorproxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DeregisterJobDto {

    @NotEmpty
    @JsonProperty("job_id")
    private String jobId;

}
