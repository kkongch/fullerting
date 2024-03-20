package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.model.entity.enums.CropType;
import com.ssafy.fullerting.crop.type.repository.CropTypeRepository;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetAllPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.NOT_EXISTS_CROP;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
    private final PackDiaryRepository packDiaryRepository;
    private final CropTypeRepository cropTypeRepository;

    @Override
    public void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest) {
        Crop crop = cropTypeRepository.findById(createPackDiaryRequest.getCropTypeId()).orElseThrow(()->new PackDiaryException(NOT_EXISTS_CROP));
        if(crop != null) {
            packDiaryRepository.save(PackDiary.builder()
                    .user(user)
                    .crop(crop)
                    .title(createPackDiaryRequest.getPackDiaryTitle())
                    .culStartAt(createPackDiaryRequest.getPackDiaryCulStartAt())
                    .culEndAt(null)
                    .growthStep(0)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build()
            );
        }
    }

    @Override
    public List<GetAllPackDiaryResponse> getAllPackDiary() {
        List<PackDiary> packDiaryList = packDiaryRepository.findAll();
        return packDiaryList.stream().map(GetAllPackDiaryResponse::fromResponse).collect(Collectors.toList());
    }
}