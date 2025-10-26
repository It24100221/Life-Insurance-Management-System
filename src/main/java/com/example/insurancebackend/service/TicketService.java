package com.example.insurancebackend.service;



import com.example.insurancebackend.entity.Ticket;
import com.example.insurancebackend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        ticket.setCreatedDate(LocalDateTime.now());
        ticket.setUpdatedDate(LocalDateTime.now());
        ticket.setStatus("open");
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, Ticket updatedTicket) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setCustomerName(updatedTicket.getCustomerName());
        ticket.setEmail(updatedTicket.getEmail());
        ticket.setPolicyNumber(updatedTicket.getPolicyNumber());
        ticket.setInquiryType(updatedTicket.getInquiryType());
        ticket.setDepartment(updatedTicket.getDepartment());
        ticket.setContactNumber(updatedTicket.getContactNumber());
        ticket.setSubject(updatedTicket.getSubject());
        ticket.setDescription(updatedTicket.getDescription());
        ticket.setUpdatedDate(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByEmail(String email) {
        return ticketRepository.findByEmail(email);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket updateStatus(Long id, String status) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(status);
        ticket.setUpdatedDate(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
}

