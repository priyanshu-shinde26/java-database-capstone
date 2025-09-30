package com.project.back_end.controller;

import com.project.back_end.model.Admin;
import com.project.back_end.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("${api.path}admin")
public class AdminController {

    @Autowired
    private Service service;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@Valid @RequestBody Admin admin) {
        return service.validateAdmin(admin);
    }
}
