package com.rbkmoney.schedulatorproxy.controller;

import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import com.rbkmoney.damsel.schedule.DominantBasedSchedule;
import com.rbkmoney.damsel.schedule.RegisterJobRequest;
import com.rbkmoney.damsel.schedule.SchedulatorSrv;
import com.rbkmoney.damsel.schedule.Schedule;
import com.rbkmoney.schedulatorproxy.JobStateSerializer;
import com.rbkmoney.schedulatorproxy.dto.DeregisterJobDto;
import com.rbkmoney.schedulatorproxy.dto.RegisterJobDto;
import com.rbkmoney.schedulatorproxy.exception.ScheduleJobException;
import com.rbkmoney.schedulatorproxy.model.JobContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/schedulator/proxy/")
@RequiredArgsConstructor
@Slf4j
public class SchedulatorProxyController {

    private final SchedulatorSrv.Iface schedulatorClient;

    private final JobStateSerializer jobStateSerializer;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void registerJob(@Valid @RequestBody RegisterJobDto registerJobDto) {
        log.info("Register job: {}", registerJobDto);
        RegisterJobRequest registerJobRequest = new RegisterJobRequest();
        registerJobRequest.setExecutorServicePath(registerJobDto.getServicePath());

        // State
        JobContext jobContext = JobContext.builder()
                .jobId(registerJobDto.getJobId())
                .schedulerId(registerJobDto.getSchedulerId())
                .servicePath(registerJobDto.getServicePath())
                .build();
        registerJobRequest.setContext(jobStateSerializer.writeByte(jobContext));
        registerJobRequest.setSchedule(buildsSchedule(registerJobDto.getSchedulerId()));

        try {
            schedulatorClient.registerJob(registerJobDto.getJobId(), registerJobRequest);
        } catch (TException e) {
            String errMsg = "Register job '" + registerJobDto.getJobId() + "' failed";
            throw new ScheduleJobException(registerJobDto.getJobId(), errMsg, e);
        }
    }

    @PostMapping(value = "/deregister", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deregisterJob(@Valid @RequestBody DeregisterJobDto deregisterJobDto) {
        log.info("Deregister job: {}", deregisterJobDto);
        try {
            schedulatorClient.deregisterJob(deregisterJobDto.getJobId());
        } catch (TException e) {
            String errMsg = "Deregister job '" + deregisterJobDto.getJobId() + "' failed";
            throw new ScheduleJobException(deregisterJobDto.getJobId(), errMsg, e);
        }
    }

    private Schedule buildsSchedule(int scheduleRefId) {
        Schedule schedule = new Schedule();
        DominantBasedSchedule dominantBasedSchedule = new DominantBasedSchedule()
                .setBusinessScheduleRef(new BusinessScheduleRef().setId(scheduleRefId));
        schedule.setDominantSchedule(dominantBasedSchedule);

        return schedule;
    }

}
