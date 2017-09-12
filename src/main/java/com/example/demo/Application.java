package com.example.demo;

import com.example.demo.model.Campaign;
import com.example.demo.model.EntityA;
import com.example.demo.model.EntityB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@SpringBootApplication
@EnableBinding(MultiOutputSource.class)
@EnableScheduling
public class Application {

    private Random r = new Random();

    @Autowired
    private MultiOutputSource source;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void doWork(){
            if(Math.floorMod(r.nextInt(10) + 1, 2) == 0)
                source.dsp1out().send(generateMessage(1));
            else
                source.dsp2out().send(generateMessage(2));
    }

    public GenericMessage<Campaign> generateMessage(int n) {
            Campaign c = Campaign.builder()
                    .date(LocalDateTime.now())
                    .entityA(EntityA.builder().fieldInt(new Random().nextInt(10 * n) + 1).build())
                    .entityB(EntityB.builder().fieldString(String.valueOf(new Random().nextInt())).build())
                    .build();
            log.info("[x]DSP{} -> Sending campaign: {}",n,c);
            return new GenericMessage<>(c);
    }
}
