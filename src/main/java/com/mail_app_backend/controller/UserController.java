//package com.mail_app_backend.controller;
//
//import com.mail_app_backend.model.dto.LoginRequest;
//import com.mail_app_backend.model.dto.UserDto;
//import com.mail_app_backend.service.UserService;
////import com.mail_app_backend.util.JwtUtil;
//import com.mail_app_backend.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private final UserService userService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//
//    @PostMapping("/register")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
//        UserDto savedUser = userService.createUser(userDto);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
//        String token = jwtUtil.generateToken(loginRequest.getEmail());  // Assuming email is used as username
//        if (token != null) {
//            return ResponseEntity.ok("Bearer " + token);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//    }
//
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser() {
//        // Clear any user session (if using sessions)
//        // For now, just return a success response
//        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
//    }
//
////    @GetMapping("/profile")
////    public ResponseEntity<?> getUserProfile(@RequestParam String email) {
////        UserDto userDto = userService.getUserProfile(email);
////        if (userDto == null) {
////            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
////        }
////        return new ResponseEntity<>(userDto, HttpStatus.OK);
////    }
//
//    @PutMapping("/profile")
//    public ResponseEntity<?> updateUserProfile(@RequestBody UserDto userDto) {
//        UserDto updatedUser = userService.updateUserProfile(userDto);
//        if (updatedUser == null) {
//            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }
//
//
//
//
//    @GetMapping("/profile")
//    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing");
//        }
//        String token = authorizationHeader.substring(7);
//        String email = jwtUtil.extractUsername(token);
//        UserDto userDto = userService.getUserProfile(email);
//        if (userDto != null) {
//            return ResponseEntity.ok(userDto);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//    }
//
//}
package com.mail_app_backend.controller;

import com.mail_app_backend.model.dto.LoginRequest;
import com.mail_app_backend.model.dto.UserDto;
import com.mail_app_backend.service.UserService;
import com.mail_app_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin // Allow frontend to access backend
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final long TOKEN_VALIDITY = 15 * 60 * 1000; // 15 minutes

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = jwtUtil.generateToken(loginRequest.getEmail());  // Assuming email is used as username
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        jwtUtil.invalidateToken(token);
        return ResponseEntity.ok("User logged out successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        if (jwtUtil.isTokenExpired(token) || jwtUtil.isTokenInvalidated(token)) {
            return ResponseEntity.status(403).body(null);
        }

        // Extract email from the token
        String email = jwtUtil.extractEmail(token);
        UserDto userDto = userService.getUserProfile(email);

        return ResponseEntity.ok(userDto);
    }


    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserDto userDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        if (jwtUtil.isTokenExpired(token) || jwtUtil.isTokenInvalidated(token)) {
            return ResponseEntity.status(403).body("Token expired or invalid");
        }
        userService.updateUserProfile(userDto);
        return ResponseEntity.ok("User profile updated successfully");
    }
}
