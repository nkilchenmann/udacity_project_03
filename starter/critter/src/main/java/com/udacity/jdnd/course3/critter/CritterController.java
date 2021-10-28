package com.udacity.jdnd.course3.critter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Dummy controller class to verify installation success. Do not use for
 * your project work.
 */
@RestController
@Transactional
public class CritterController {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/test")
    public String test() {
        return "Critter Starter installed successfully";
    }
}
