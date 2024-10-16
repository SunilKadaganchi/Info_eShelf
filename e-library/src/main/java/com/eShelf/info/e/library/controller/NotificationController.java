package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    // this API will be called daily @4:00 pm , to remind about the reserved books to users
    // else the reserved books will be released
    @Scheduled(cron = "0 0 16 * * *")
    @GetMapping("/collect")
    public ResponseEntity<Void> notifyToCollectBooks(){
        notificationService.notifyCollectAndRelease("Collect");
        return ResponseEntity.ok().build();
    }

    // this API will be called daily @6:00 pm , to Notify about  reserved books are released
    @Scheduled(cron = "0 5 18 * * *")
    @GetMapping("/released")
    public ResponseEntity<Void> releasedNotification(){
        notificationService.notifyCollectAndRelease("Release");
        return ResponseEntity.ok().build();
    }
}
