package com.billhub.controller;

import com.billhub.model.Notice;
import com.billhub.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{providerName}")
    public List<Notice> getNoticesByProvider(@PathVariable String providerName) {
        return noticeService.getNoticesByProvider(providerName);
    }

    @PostMapping
    public Notice createNotice(@RequestBody Notice notice) {
        return noticeService.createNotice(notice);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
    }
}