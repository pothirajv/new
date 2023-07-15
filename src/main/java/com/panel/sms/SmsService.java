package com.panel.sms;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author vijay
 */
@Component
public class SmsService {
    
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

	@Value("${enable.otp.sending}")
	boolean enableOtp;
     
    @Async
    public void sendSms(String msg,String phoneNumber){
		if(enableOtp){
            System.out.println("SMS SENT");
        }
    }
}
