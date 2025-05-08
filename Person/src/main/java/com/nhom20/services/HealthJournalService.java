package com.nhom20.services;

import com.nhom20.pojo.HealthJournal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HealthJournalService {
    
    // Lưu nhật ký mới
    public HealthJournal saveJournal(HealthJournal journal) {
        // TODO: Implement database save
        journal.setId(generateId()); // Tạm thời tạo ID ngẫu nhiên
        return journal;
    }
    
    // Lấy danh sách nhật ký của người dùng
    public List<HealthJournal> getUserJournals(int userId) {
        // TODO: Implement database query
        return new ArrayList<>(); // Tạm thời trả về list rỗng
    }
    
    // Lấy nhật ký theo ID
    public HealthJournal getJournalById(int id) {
        // TODO: Implement database query
        return null;
    }
    
    // Cập nhật nhật ký
    public HealthJournal updateJournal(HealthJournal journal) {
        // TODO: Implement database update
        return journal;
    }
    
    // Xóa nhật ký
    public boolean deleteJournal(int id) {
        // TODO: Implement database delete
        return true;
    }
    
    // Lấy nhật ký theo khoảng thời gian
    public List<HealthJournal> getJournalsByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        // TODO: Implement database query with date range
        return new ArrayList<>();
    }
    
    // Lấy thống kê về tâm trạng và năng lượng
    public List<Object> getMoodAndEnergyStats(int userId) {
        List<HealthJournal> journals = getUserJournals(userId);
        
        // Tính trung bình mức năng lượng
        double avgEnergyLevel = journals.stream()
                .mapToInt(HealthJournal::getEnergyLevel)
                .average()
                .orElse(0.0);
        
        // Thống kê tâm trạng
        List<String> moods = journals.stream()
                .map(HealthJournal::getMood)
                .collect(Collectors.toList());
        
        // TODO: Implement more detailed statistics
        
        return List.of(avgEnergyLevel, moods);
    }
    
    // Tạo ID tạm thời
    private int generateId() {
        return (int) (Math.random() * 1000);
    }
} 