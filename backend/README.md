# FindSuspect

## 업로드

### 영상 업로드

post /api/upload/video

videoFile에 이미지 넣고 요청

저장경로 src/main/frontend/uploads/video

### 사진 업로드 

post /api/upload/image

imageFile에 이미지 넣고 요청

저장경로 src/main/frontend/server/uploads/image
<br>
기존 업로드에서 영상 하나만 넣고 전송하면됨 
<br>



### 결과

get /api/result
{
    "sample.mp4_frame1140_person1.jpg": {
        "video_name": "sample.mp4",
        "similarity": 95.7985952765769,
        "original_top5": [
            0.9172,
            0.5366,
            0.0535,
            0.653,
            0.4283
        ],
        "file_top5": [
            0.6205,
            0.9817,
            0.0091,
            0.9403,
            0.4634
        ],
        "attr_words": [
            "top short",
            "bottom short",
            "shoulder bag",
            "backpack",
            "hat"
        ],
        "time": "0:38"
    },
    ...
} 형식으로 나감

### 영상 삭제

delete /api/video

name으로 지우고 싶은 영상이름 넣어서 요청


