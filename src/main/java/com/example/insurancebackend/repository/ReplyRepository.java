package com.example.insurancebackend.repository;



import com.example.insurancebackend.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByTicketId(Long ticketId);
}
