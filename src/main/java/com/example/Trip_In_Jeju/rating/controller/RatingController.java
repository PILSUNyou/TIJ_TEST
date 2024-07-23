package com.example.Trip_In_Jeju.rating.controller;

import com.example.Trip_In_Jeju.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/review/{category}/{itemId}")
    public String getReviewPage(@PathVariable String category, @PathVariable Long itemId, Model model) {
        // 수정된 부분: category와 itemId를 전달하여 모든 리뷰와 평균 점수를 가져옴
        model.addAttribute("allRatings", ratingService.getAllRatings(itemId, category));
        model.addAttribute("averageScore", ratingService.calculateAverageScore(itemId, category));
        return "rating/review";
    }

    @PostMapping("/review/save")
    public String saveRating(@RequestParam String category, @RequestParam Long itemId, @RequestParam Integer score,
                             @RequestParam String comment, Authentication authentication, @RequestParam("thumbnail") MultipartFile thumbnail) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return "redirect:/" + category + "/detail/" + itemId;
        }
        String nickname = ((UserDetails) authentication.getPrincipal()).getUsername();
        ratingService.saveRating(itemId, score, comment, nickname, thumbnail, category);
        return "redirect:/" + category + "/detail/" + itemId;
    }
}