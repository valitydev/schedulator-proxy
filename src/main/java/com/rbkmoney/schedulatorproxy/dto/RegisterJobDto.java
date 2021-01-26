package com.rbkmoney.schedulatorproxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class RegisterJobDto {

    @NotEmpty
    @JsonProperty("job_id")
    private String jobId;

    @NotNull
    @JsonProperty("scheduler_id")
    private Integer schedulerId;

    @NotNull
    @JsonProperty("calendar_id")
    private Integer calendarId;

    @JsonProperty("revision_id")
    private Long revisionId;

    @NotEmpty
    @JsonProperty("service_path")
    private String servicePath;

}
