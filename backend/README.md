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

기존 업로드에서 영상 하나만 넣고 전송하면됨 

### 영상 삭제

delete /api/video

name으로 지우고 싶은 영상이름 넣어서 요청

## 경로

### 이미지 경로

![alt text](image-1.png)

### 영상 경로

![alt text](image.png)

## 결과

get /api/result

```
{
    "2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame330_person1.jpg": {
        "video_name": "2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4",
        "similarity": 99.99999999999997,
        "original_top5": [
            0.7089,
            0.8367,
            0.744,
            0.9329,
            0.6406
        ],
        "file_top5": [
            0.7089,
            0.8367,
            0.744,
            0.9329,
            0.6406
        ],
        "attr_words": [
            "top short",
            "bottom short",
            "shoulder bag",
            "backpack",
            "hat"
        ],
        "time": "0:11"
    },
    ...
}
```

형식으로 나감

## 히스토리

```
[
    {
        "imageName": "image/sample.jpg/sample.jpg",
        "imageCropped": "image/sample.jpg/sample.jpg_cropped.jpg",
        "imageRectangle": "image/sample.jpg/sample.jpg_rectangle.jpg",
        "result": "{\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame330_person1.jpg\":{\"video_name\":\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:11\"},\"sample2.mp4_frame450_person6.jpg\":{\"video_name\":\"sample2.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:15\"},\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame450_person1.jpg\":{\"video_name\":\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:15\"},\"sample.mp4_frame930_person1.jpg\":{\"video_name\":\"sample.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:31\"},\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame480_person5.jpg\":{\"video_name\":\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:16\"},\"sample2.mp4_frame210_person2.jpg\":{\"video_name\":\"sample2.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:07\"},\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame450_person3.jpg\":{\"video_name\":\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:15\"},\"sample.mp4_frame990_person3.jpg\":{\"video_name\":\"sample.mp4\",\"similarity\":86.58740473605427,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7574,0.9176,0.967,0.9873,0.4349],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:33\"},\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4_frame360_person2.jpg\":{\"video_name\":\"2021-08-02_06-21-00_mon_sunny_out_ja-ma_C0041.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:12\"},\"sample.mp4_frame630_person1.jpg\":{\"video_name\":\"sample.mp4\",\"similarity\":97.90707025693796,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7436,0.638,0.6995,0.7862,0.5816],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:21\"},\"sample.mp4_frame1230_person2.jpg\":{\"video_name\":\"sample.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:41\"},\"sample2.mp4_frame570_person5.jpg\":{\"video_name\":\"sample2.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:19\"},\"sample.mp4_frame1290_person1.jpg\":{\"video_name\":\"sample.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:43\"},\"sample2.mp4_frame510_person4.jpg\":{\"video_name\":\"sample2.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:17\"},\"sample2.mp4_frame120_person3.jpg\":{\"video_name\":\"sample2.mp4\",\"similarity\":99.99999999999997,\"original_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"file_top5\":[0.7089,0.8367,0.744,0.9329,0.6406],\"attr_words\":[\"top short\",\"bottom short\",\"shoulder bag\",\"backpack\",\"hat\"],\"time\":\"0:04\"}}"
    }
]
```


