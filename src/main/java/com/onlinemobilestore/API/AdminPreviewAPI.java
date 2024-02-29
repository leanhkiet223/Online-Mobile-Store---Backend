package com.onlinemobilestore.API;

import com.onlinemobilestore.dto.DTOPreview;
import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.PreviewRepository;
import com.onlinemobilestore.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPreviewAPI {
    @Autowired
    private PreviewRepository previewRepository;

    // Get all preview
    @GetMapping("/previews")
    public ResponseEntity<List<DTOPreview>> getAllUsers() {
        List<Preview> previews = previewRepository.findAll();
        List<DTOPreview> previewList = new ArrayList<>();
        previews.stream().forEach(preview -> {
            previewList.add(new DTOPreview(preview.getContent(), preview.getRate(), preview.getUser().getFullName(),
                    preview.getCreateDate(), preview.getProduct().getName()));
        });
        return new ResponseEntity<>(previewList, HttpStatus.OK);
    }

}

