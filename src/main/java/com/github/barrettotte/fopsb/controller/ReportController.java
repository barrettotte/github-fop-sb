package com.github.barrettotte.fopsb.controller;

import com.github.barrettotte.fopsb.service.FopService;
import com.github.barrettotte.fopsb.service.GithubGqlService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.barrettotte.fopsb.model.User;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/report")
@Api(value="report", description="Generate report of a GitHub user's repos")
public class ReportController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private FopService fopService;

    @Autowired
    private GithubGqlService githubService;

    @GetMapping("/")
    @ApiOperation(value="Generate report for specific user")
    public ResponseEntity<Object> generate(@RequestParam("username") final String username, HttpServletResponse response) {
        try{
            final User user = githubService.getUser(username);
            LOGGER.info(String.format("Requested %s", username));
            
            try(final ByteArrayInputStream pdf = fopService.generateUserReport(user)) {
                response.setContentType("application/pdf");
                IOUtils.copy(pdf, response.getOutputStream());
                response.flushBuffer();
            }
            return ResponseEntity.ok().build();
        } catch(final Exception e){
            LOGGER.error("Error occurred in request", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/test")
    @ApiOperation(value="Generate report with local test data")
    public ResponseEntity<Object> generate(HttpServletResponse response) {
        try{
            LOGGER.info(String.format("Requested test data"));
            
            try(final ByteArrayInputStream pdf = fopService.generateTestReport()) {
                response.setContentType("application/pdf");
                IOUtils.copy(pdf, response.getOutputStream());
                response.flushBuffer();
            }
            return ResponseEntity.ok().build();
        } catch(final Exception e){
            LOGGER.error("Error occurred in request", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
