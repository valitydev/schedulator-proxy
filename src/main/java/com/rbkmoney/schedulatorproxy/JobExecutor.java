package com.rbkmoney.schedulatorproxy;

import com.rbkmoney.damsel.schedule.ContextValidationResponse;
import com.rbkmoney.damsel.schedule.ExecuteJobRequest;
import com.rbkmoney.damsel.schedule.ScheduledJobContext;
import com.rbkmoney.damsel.schedule.ScheduledJobExecutorSrv;
import com.rbkmoney.schedulatorproxy.model.JobContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobExecutor implements ScheduledJobExecutorSrv.Iface {

    private final JobStateSerializer jobStateSerializer;

    @Override
    public ContextValidationResponse validateExecutionContext(ByteBuffer byteBuffer) throws TException {
        JobContext jobState = jobStateSerializer.read(byteBuffer.array());
        log.info("Validate job state: {}", jobState);

        ContextValidationResponse contextValidationResponse = new ContextValidationResponse();
        contextValidationResponse.setErrors(Collections.emptyList());
        return contextValidationResponse;
    }

    @Override
    public ByteBuffer executeJob(ExecuteJobRequest executeJobRequest) throws TException {
        ScheduledJobContext scheduledJobContext = executeJobRequest.getScheduledJobContext();

        JobContext jobContext = jobStateSerializer.read(executeJobRequest.getServiceExecutionContext());

        log.info("[Execute job] job context: {}", jobContext);

        jobContext.setPrevFireTime(scheduledJobContext.getPrevFireTime());
        jobContext.setNextFireTime(scheduledJobContext.getNextFireTime());
        jobContext.setNextCronTime(scheduledJobContext.getNextCronTime());

        log.info("[Execute job] job context after: {}", jobContext);

        return ByteBuffer.wrap(jobStateSerializer.writeByte(jobContext));
    }

}
