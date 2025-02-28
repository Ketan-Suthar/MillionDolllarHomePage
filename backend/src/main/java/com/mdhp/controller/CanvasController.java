package com.mdhp.controller;

import com.mdhp.model.Canvas;
import com.mdhp.pojo.CanvasPojo;
import com.mdhp.service.CanvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/canvas")
public class CanvasController {
    @Autowired
    private CanvasService canvasService;

    @PostMapping("/buy")
    @CacheEvict(value = "activeAds", allEntries = true)
    public ResponseEntity<String> buyPixels(@RequestBody CanvasPojo buyRequest) {
        if (buyRequest.getDays() < 1 || buyRequest.getDays() > 30) {
            return ResponseEntity.badRequest().body("Days must be between 1 and 30.");
        }
        return canvasService.buyPixels(buyRequest);
    }

    @GetMapping("/active")
    @Cacheable(value = "activeAds")
    public List<Canvas> getActiveAds() {
        return canvasService.getActiveAds();
    }
}
