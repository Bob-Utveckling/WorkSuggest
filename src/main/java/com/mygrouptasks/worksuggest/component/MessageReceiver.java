package com.mygrouptasks.worksuggest.component;

import com.mygrouptasks.worksuggest.model.Log;
import com.mygrouptasks.worksuggest.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class MessageReceiver {

    @Autowired
    private LogRepository logRepository;

    Log log = new Log();

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    @JmsListener(destination = "myQueue1.queue")
    public void receiveQueue(String text) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("- Message Received: "+text);

        log = new Log();
        log.setMessage(text);
        date = new Date();
        log.setDatetime(sdf.format(date));

        logRepository.save(log);

        System.out.println("- Message saved: "+text);

    }
}
