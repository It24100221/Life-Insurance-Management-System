package com.example.insurancebackend.dto;

import com.example.insurancebackend.entity.Policy;
import lombok.Data;

import java.util.List;

@Data
public class PolicyPageResponse {
    private List<Policy> policies;
    private int currentPage;
    private int totalPages;
    private int totalCount;
}