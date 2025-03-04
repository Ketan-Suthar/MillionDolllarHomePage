package com.mdhp.service;

import com.mdhp.exceptions.AlreadyBought;
import com.mdhp.exceptions.BadRequest;
import com.mdhp.exceptions.TooManyRequests;
import com.mdhp.model.Canvas;
import com.mdhp.pojo.CanvasPojo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ICanvasService {
    ResponseEntity<String> buyPixels(final CanvasPojo buyRequest) throws IOException, AlreadyBought, BadRequest;

    List<Canvas> getActiveAds(HttpServletRequest request) throws TooManyRequests;
}
