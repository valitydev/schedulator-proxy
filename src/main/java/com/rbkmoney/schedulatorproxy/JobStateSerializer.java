package com.rbkmoney.schedulatorproxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.schedulatorproxy.model.JobContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JobStateSerializer {

    private final ObjectMapper mapper;

    public byte[] writeByte(JobContext obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JobContext read(byte[] data) {
        try {
            return mapper.readValue(data, JobContext.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

}
