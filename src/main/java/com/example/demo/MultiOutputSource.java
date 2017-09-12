package com.example.demo;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface MultiOutputSource {
    String DSP1OUT = "dsp1out";
    String DSP2OUT = "dsp2out";

    @Output(DSP1OUT)
    MessageChannel dsp1out();

    @Output(DSP2OUT)
    MessageChannel dsp2out();
}