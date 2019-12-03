package com.rbkmoney.schedulatorproxy.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobContext {

    private String jobId;

    private Integer schedulerId;

    private String servicePath;

    private String prevFireTime;

    private String nextFireTime;

    private String nextCronTime;

}
