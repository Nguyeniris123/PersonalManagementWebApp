package com.nhom20.controllers;

import com.google.gson.Gson;
import com.nhom20.pojo.NotificationSettings;
import com.nhom20.services.NotificationService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/api/notifications/*")
public class ApiNotificationController extends HttpServlet {
    private final NotificationService notificationService;
    private final Gson gson;

    public ApiNotificationController() {
        this.notificationService = NotificationService.getInstance();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        NotificationSettings settings = notificationService.getUserSettings(userId);
        sendJsonResponse(response, settings);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        NotificationSettings settings = gson.fromJson(request.getReader(), NotificationSettings.class);
        settings.setUserId(userId);
        notificationService.updateUserSettings(userId, settings);
        
        sendJsonResponse(response, settings);
    }

    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(data));
        }
    }
} 