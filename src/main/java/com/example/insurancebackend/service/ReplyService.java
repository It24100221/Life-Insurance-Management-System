package com.example.insurancebackend.service;



import com.example.insurancebackend.entity.Reply;
import com.example.insurancebackend.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public Reply addReply(Reply reply) {
        reply.setReplyDate(LocalDateTime.now());
        return replyRepository.save(reply);
    }

    public List<Reply> getRepliesByTicketId(Long ticketId) {
        return replyRepository.findByTicketId(ticketId);
    }
}
