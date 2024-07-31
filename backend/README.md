# FindSuspect

## 업로드

### 영상 업로드

post /api/upload/video

videoFile에 이미지 넣고 요청

저장경로 src/main/frontend/uploads/video

### 사진 업로드 

post /api/upload/image

imageFile에 이미지 넣고 요청

저장경로 src/main/frontend/uploads/image
<br>
기존 업로드에서 영상 하나만 넣고 전송하면됨 
<br>
## 경로 요청

### 사진 경로 요청

get /api/get/image

### 영상 경로 요청

get /api/get/video

uploads로부터 시작하는 파일경로 return
<br>
react경로 수정 필요하면 말해주시면 됩니다.


## 결과
python에서 결과가 "시간:정확도" 를 한줄씩 출력해야함
"00:01:13:83"
"00:02:33:55"


get /api/result
[
  {
    "time": "00:01:13",
    "accuracy": 83
  },
  {
    "time": "00:02:33",
    "accuracy": 55
  }
]


