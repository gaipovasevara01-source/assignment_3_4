package com.example.demo.controller;

import com.example.demo.utils.InMemoryCache;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @DeleteMapping("/cache")
    public String clearCache() {
        InMemoryCache.getInstance().clearAll();
        return "Cache cleared";
    }
}
