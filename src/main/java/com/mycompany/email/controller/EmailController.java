package com.mycompany.email.controller;

import com.mycompany.email.dto.Email;
import com.mycompany.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shambhu.mehta on 3/10/2022
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("api/v1/notify")
public class EmailController {

    public EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendemail")
    @ResponseBody
    public String sendEmail(@RequestBody Email email){
        try {
            emailService.sendMail(email,false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while sending email {}",e.getStackTrace());
        }
        return "Email sent to "+ email.getTo() +" successfully";
    }

    @PostMapping("/sendemailwithpdf")
    @ResponseBody
    public String sendEmailWithPdf(@RequestBody Email email){
        try {
            emailService.sendMail(email,true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while sending email {}",e.getStackTrace());
        }
        return "Email sent to "+ email.getTo() +" successfully";
    }
}
