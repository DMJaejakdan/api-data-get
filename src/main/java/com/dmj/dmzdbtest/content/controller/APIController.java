package com.dmj.dmzdbtest.content.controller;

import com.dmj.dmzdbtest.content.dto.response.DramaListResponse;
import com.dmj.dmzdbtest.util.APIConfig;
import com.dmj.dmzdbtest.util.DataGenerationTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class APIController {
    private final APIConfig apiConfig;
    private final DataGenerationTask dataGenerationTask;

    public APIController(APIConfig apiConfig, DataGenerationTask dataGenerationTask) {
        this.apiConfig = apiConfig;
        this.dataGenerationTask = dataGenerationTask;
    }

    @GetMapping("/api-config-test")
    public ResponseEntity<String> getAPIConfig() {
        log.info("[APIConfig] :" + apiConfig);
        return ResponseEntity.ok("apiConfig");
    }

    @GetMapping("/test")
    public ResponseEntity<Void> testRestTemplate() {
        dataGenerationTask.getData();
        return ResponseEntity.ok().build();
    }
}
