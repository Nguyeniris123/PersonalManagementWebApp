package com.nhom20.services;

import com.nhom20.pojo.MealRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GPTService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // Thay thế bằng API Key hợp lệ của bạn
    private static final String API_KEY = "Bearer sk-proj-";

    public String generateMealPlan(MealRequest request) throws IOException {
//        // Tạo prompt mô tả người dùng
//        String prompt = String.format(
//            "I'm a %d-year-old %s, %.1fkg, %.1fcm, BMI %.1f, steps/day: %d. Target: %s. Suggest a meal plan (3 meals, 2 snacks) with calories.",
//            request.getAge(), request.getGender(), request.getWeight(), request.getHeight(),
//            request.getBmi(), request.getStepsPerDay(), request.getTarget()
//        );
//
//        // JSON body gửi lên OpenAI
//        String payload = """
//        {
//          "model": "gpt-3.5-turbo",
//          "messages": [
//            {"role": "system", "content": "You are a helpful dietitian."},
//            {"role": "user", "content": "%s"}
//          ]
//        }
//        """.formatted(prompt);
//
//        // Gửi request tới OpenAI
//        URL url = new URL(OPENAI_API_URL);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Authorization", API_KEY);
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setDoOutput(true);
//
//        try (OutputStream os = con.getOutputStream()) {
//            os.write(payload.getBytes("utf-8"));
//        }
//
//        // Nhận response (hoặc lỗi)
//        int status = con.getResponseCode();
//        InputStream inputStream = (status >= 200 && status < 300)
//                ? con.getInputStream()
//                : con.getErrorStream();
//
//        StringBuilder response = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                response.append(line.trim());
//            }
//        }
//
//        // Nếu bị rate limit/quota lỗi
//        if (status == 429) {
//            throw new IOException("GPT API error 429: Quá giới hạn truy cập. Vui lòng thử lại sau vài phút.\nChi tiết: " + response);
//        } else if (status != 200) {
//            throw new IOException("GPT API error: " + status + "\n" + response);
//        }
//
//        // Phân tích JSON response bằng org.json
//        try {
//            JSONObject obj = new JSONObject(response.toString());
//            JSONArray choices = obj.getJSONArray("choices");
//            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
//            String content = message.getString("content");
//            return content;
//        } catch (Exception e) {
//            return "Không thể phân tích phản hồi GPT.";
//        }
        return """
    Bữa sáng: Trứng luộc, bánh mì nguyên cám, rau củ hấp (350 kcal)
    Bữa phụ: Sữa chua Hy Lạp + 1 quả chuối (150 kcal)
    Bữa trưa: Cơm gạo lứt, ức gà, canh rau ngót (500 kcal)
    Bữa phụ: Táo + 1 nắm hạt điều (200 kcal)
    Bữa tối: Cá hồi áp chảo, khoai lang, salad rau trộn (500 kcal)
    """;
    }
}
