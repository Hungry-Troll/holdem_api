package net.lodgames.main.controller;

import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
public class ErrorCodeExporterController {
    // test method
    @GetMapping("/api/test/errorCode")
    public ResponseEntity<?> errorCode() throws Exception{
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        List<String[]> codes = new ArrayList<>();
        for (ErrorCode code : ErrorCode.values()) {
            codes.add(new String[]{code.getCode() + "",code.getStatus()+"",code.getMessage()});
        }
        File csvOutputFile = new File("build/error.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            codes.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(csvOutputFile));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=codes_" +  currentDateTime + ".csv")
                .body(resource);
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }

}

