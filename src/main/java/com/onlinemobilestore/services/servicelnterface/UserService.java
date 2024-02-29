package com.onlinemobilestore.services.servicelnterface;


import com.onlinemobilestore.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    AccountDTO getAccountById(Integer id);
}
