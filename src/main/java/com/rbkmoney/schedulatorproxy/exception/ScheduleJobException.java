package com.rbkmoney.schedulatorproxy.exception;

public class ScheduleJobException extends RuntimeException {

    private final String jobId;

    public ScheduleJobException(String jobId, Throwable cause) {
        super(cause);
        this.jobId = jobId;
    }

    public ScheduleJobException(String jobId, String message, Throwable cause) {
        super(message, cause);
        this.jobId = jobId;
    }

}
