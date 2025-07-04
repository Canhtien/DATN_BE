package com.alibou.security.api.admin;

import com.alibou.security.api.user.InfoAPI;
import com.alibou.security.model.response.UserResponse;
import com.alibou.security.service.JPA.UserServiceJPA;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AccountAPI {
    private static final Logger logger = LoggerFactory.getLogger(InfoAPI.class);
    private final UserServiceJPA service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        try {
            List<UserResponse> users = service.getAllUsers();
            logger.info("Retrieved all theaters successfully");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Failed to retrieve notifications: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/pages")
    public ResponseEntity<Page<UserResponse>> findAllUser(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pagesize) {
        return ResponseEntity.ok(service.getAllUsersPaged(name, page, pagesize));
    }

    @PutMapping("/{id}/role/{roleId}")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @PathVariable Long roleId) {
        try {
            service.changeRole(id, roleId);
            logger.info("Updated user role successfully: {}", id);
//            return ResponseEntity.status(200).body("User role updated successfully");
            return ResponseEntity.ok(Map.of("message", "Cập nhật vai trò thành công"));
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update user role with ID: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to update user role with ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<?> updateStatusUser(@PathVariable Long id){
        try {
            UserResponse userResponse = service.blockUser(id);
            logger.info("Block user: {}", userResponse);
            return ResponseEntity.ok().body(userResponse);
        }catch (Exception e) {
            logger.error("Error blocking user: {}", e);
            return ResponseEntity.badRequest().body("Error blocking user");
        }
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<?> unblockUser(@PathVariable Long id){
        try {
            UserResponse userResponse = service.unblockUser(id);
            logger.info("Unblock user: {}", userResponse);
            return ResponseEntity.ok().body(userResponse);
        }catch (Exception e) {
            logger.error("Error unblocking user: {}", e);
            return ResponseEntity.status(500).body("Error unblocking user");
        }
    }
}
