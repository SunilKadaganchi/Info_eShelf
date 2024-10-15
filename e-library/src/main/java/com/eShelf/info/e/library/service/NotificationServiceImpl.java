package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.model.UserBookRecord;
import com.eShelf.info.e.library.repo.UserBookRecordRepository;
import com.eShelf.info.e.library.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserBookRecordRepository userBookRecordRepository;
    @Autowired
    private UserRepository userRepository;



    @Override
    public void notifyCollectAndRelease(String notifyAbout) {

        List<UserBookRecord> userBookRecordList  = userBookRecordRepository.getReservedBooks();

        List<UUID> userIds = new ArrayList<>();
        for(UserBookRecord item : userBookRecordList){
            userIds.add(item.getUserId());
        }
        List<String> emailsList = userRepository.findEmailsIdByIds(userIds);

        for(String toEmail : emailsList) {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("kumarsunil250699@gmail.com");
            if (notifyAbout.equalsIgnoreCase("Collect")){
                mailMessage.setSubject("Reminder to Collect book");
                mailMessage.setText("Collect your Reserved book before 6:00 pm else it will be released");
             }
            else if(notifyAbout.equalsIgnoreCase("Release")) {
                mailMessage.setSubject("Book Release");
                mailMessage.setText("Sorry to inform,\n " +
                        "that your Reserved book has been released as you haven't collected it before 6:00 pm ");
                 }
            mailMessage.setTo(toEmail);

            javaMailSender.send(mailMessage);
        }

        System.out.println("Reminder Emails Sent Successfully......");

    }

}
