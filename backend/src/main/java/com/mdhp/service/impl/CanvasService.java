package com.mdhp.service.impl;

import com.mdhp.constants.Constants;
import com.mdhp.exceptions.AlreadyBought;
import com.mdhp.exceptions.BadRequest;
import com.mdhp.exceptions.TooManyRequests;
import com.mdhp.model.Canvas;
import com.mdhp.pojo.CanvasPojo;
import com.mdhp.repository.CanvasRepository;
import com.mdhp.service.IRateLimitingService;
import com.mdhp.utils.CanvasUtils;
import com.mdhp.utils.DateUtils;
import com.mdhp.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class CanvasService {

    @Autowired
    private CanvasRepository canvasRepository;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private IRateLimitingService slidingWindowRateLimiting;


    private static boolean pixelInRange(final CanvasPojo buyRequest) throws BadRequest {
        if (buyRequest.getP1().getX() < Constants.PIXEL_START) return false;
        if (buyRequest.getP1().getY() < Constants.PIXEL_START) return false;
        if (buyRequest.getP2().getX() < Constants.PIXEL_START) return false;
        if (buyRequest.getP2().getY() < Constants.PIXEL_START) return false;
        if (buyRequest.getP1().getX() > Constants.PIXEL_END) return false;
        if (buyRequest.getP1().getY() > Constants.PIXEL_END) return false;
        if (buyRequest.getP2().getX() > Constants.PIXEL_END) return false;
        if (buyRequest.getP2().getY() > Constants.PIXEL_END) return false;
        return true;
    }

    public void validations(final CanvasPojo buyRequest) throws AlreadyBought, BadRequest {
        final var area = buyRequest.getPointsForDb();
        if (buyRequest.getDays() < 1 || buyRequest.getDays() > 30) {
            throw new BadRequest("Bad Request", "Days must be between 1 and 30.");
        }

        if (!CanvasService.pixelInRange(buyRequest)) {
            throw new BadRequest("Bad Request", "Invalid selection pixel point must be between 0 and 99.");
        }

        if (canvasRepository.existsByAreaAndActive(area, true)) {
            throw new AlreadyBought("already taken.");
        }

        if(!ImageUtils.isValidImage(buyRequest.getImage())) {
            throw new RuntimeException("Please select JPG or PNG file only.");
        }

        final var activeAreas = canvasRepository.findAreaByActiveTrue();
        System.out.println("checking for intersections...");
        System.out.println(activeAreas);
        final var selectedPoints = Arrays.stream(area.split(",")).map(Integer::parseInt).toList();
        for(var activeArea: activeAreas) {
            final var existingPoints = Arrays.stream(activeArea.split(",")).map(Integer::parseInt).toList();
            System.out.println(existingPoints);
            if(CanvasUtils.doRectanglesIntersect(selectedPoints.get(0), selectedPoints.get(1), selectedPoints.get(2), selectedPoints.get(3),
                    existingPoints.get(0), existingPoints.get(1), existingPoints.get(2), existingPoints.get(3))) {
                System.out.println("Already taken...");
                throw new AlreadyBought("already taken.");
            }
        }
    }

    public ResponseEntity<String> buyPixels(final CanvasPojo buyRequest) throws IOException, AlreadyBought, BadRequest {
        this.validations(buyRequest);
        final var area = buyRequest.getPointsForDb();
        final var newUuid = UUID.randomUUID().toString();
        final var imagePath = Constants.IMAGES_PATH + newUuid;

        // todo save image with extension
//        ImageUtils.saveImage(buyRequest.getImage(), imagePath);

        Canvas canvas = new Canvas();
        canvas.setUuid(newUuid);
        canvas.setArea(area);
        canvas.setCreatedOn(DateUtils.getNow());
        canvas.setDays(buyRequest.getDays());
        canvas.setImageUrl(imagePath);
        canvas.setHoverText(buyRequest.getHoverText());
        canvas.setActive(true);
        canvas.setUrl(buyRequest.getUrl());
//        canvasRepository.save(canvas);
        cacheService.evictCache("activeAds");
        return ResponseEntity.ok("Purchase successful.");
    }

    public List<Canvas> getActiveAds(HttpServletRequest request) throws TooManyRequests {
        if (slidingWindowRateLimiting.isRateLimited(request)) {
            throw new TooManyRequests("Rate limit exceeded.", "You can make maximum %s requests in %s seconds.".
                    formatted(IRateLimitingService.MAX_REQUESTS, IRateLimitingService.TIME_WINDOW/1000));
        }

        List<Canvas> cachedAds = cacheService.getFromCache("activeAds");
        if (cachedAds != null) {
            System.out.println("Cache hit for active ads");
            return cachedAds;
        }
        System.out.println("Fetching active ads");
        List<Canvas> activeAds = canvasRepository.findByActiveTrue();
        cacheService.storeInCache("activeAds", activeAds, 1, TimeUnit.DAYS);
        return activeAds;
    }
}
