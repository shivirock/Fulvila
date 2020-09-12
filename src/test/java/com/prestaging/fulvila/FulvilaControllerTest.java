package com.prestaging.fulvila;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class FulvilaControllerTest {

    @Test
    void welcome() {
        FulvilaController fulvilaController = new FulvilaController();
        ResponseEntity<String> response = fulvilaController.welcome();

    }
}