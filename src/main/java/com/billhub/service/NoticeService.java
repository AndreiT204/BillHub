package com.billhub.service;

import com.billhub.model.Notice;
import com.billhub.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public List<Notice> getNoticesByProvider(String providerName) {
        return noticeRepository.findAll().stream()
                .filter(n -> n.getProvider() != null && n.getProvider().toString().equalsIgnoreCase(providerName))
                .collect(Collectors.toList());
    }
}