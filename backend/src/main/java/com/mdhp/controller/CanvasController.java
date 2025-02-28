package com.mdhp.controller;

import com.mdhp.exceptions.AlreadyBought;
import com.mdhp.exceptions.BadRequest;
import com.mdhp.model.Canvas;
import com.mdhp.pojo.CanvasPojo;
import com.mdhp.service.CanvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/canvas")
public class CanvasController {
    @Autowired
    private CanvasService canvasService;

    @PostMapping("/buy")
    @CacheEvict(value = "activeAds", allEntries = true)
    public ResponseEntity<String> buyPixels(@RequestBody CanvasPojo buyRequest) throws IOException, AlreadyBought, BadRequest {
        System.out.println(buyRequest);
        return canvasService.buyPixels(buyRequest);
    }

    @GetMapping("/active")
    @Cacheable(value = "activeAds")
    public List<Canvas> getActiveAds() {
        return canvasService.getActiveAds();
    }
}
