package com.example.Trip_In_Jeju.kategorie.food.service;

import com.example.Trip_In_Jeju.kategorie.food.entity.Food;
import com.example.Trip_In_Jeju.kategorie.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    @Value("${custom.genFileDirPath}")
    public String genFileDirPath;

    public Page<Food> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        return foodRepository.findAll(pageable);
    }
    public void create(String title, String content, String place, String closedDay, MultipartFile thumbnail) {

        String thumbnailRelPath = "food/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(genFileDirPath + "/" + thumbnailRelPath);

        thumbnailFile.mkdirs();

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Food p = Food.builder()
                .title(title)
                .content(content)
                .place(place)
                .closedDay(closedDay)
                .thumbnailImg(thumbnailRelPath)
                .build();

        foodRepository.save(p);
    }

    public Food getFood(Long id) {
        Optional<Food> food = foodRepository.findById(id);

        if (food.isPresent()) {
            return food.get();
        } else {
            throw new RuntimeException("food not found");
        }
    }

    public List<Food> getList() {
        return foodRepository.findAll();
    }
}