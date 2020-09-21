package com.prestaging.fulvila;

import com.prestaging.fulvila.controller.FulvilaController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class FulvilaControllerTest {

    @Test
    void welcome() {
        FulvilaController fulvilaController = new FulvilaController();
        ResponseEntity<String> response = fulvilaController.welcome();

    }
}