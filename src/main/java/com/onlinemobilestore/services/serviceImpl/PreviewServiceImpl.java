package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.repository.PreviewRepository;
import com.onlinemobilestore.services.servicelnterface.PreviewService;
import org.springframework.stereotype.Service;

@Service
public class PreviewServiceImpl implements PreviewService {
    private PreviewRepository previewRepository;
    public PreviewServiceImpl(PreviewRepository previewRepository){
        this.previewRepository = previewRepository;
    }
}
