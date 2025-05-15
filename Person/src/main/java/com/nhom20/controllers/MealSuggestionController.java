package com.nhom20.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhom20.pojo.MealRequest;
import com.nhom20.services.GPTService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nguyenho
 */
@WebServlet("/api/meal-suggestion")
public class MealSuggestionController extends HttpServlet {
    private final GPTService gptService = new GPTService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MealRequest request = mapper.readValue(req.getInputStream(), MealRequest.class);

        String suggestion = gptService.generateMealPlan(request);
        resp.setContentType("text/plain");
        resp.getWriter().write(suggestion);
    }
}

