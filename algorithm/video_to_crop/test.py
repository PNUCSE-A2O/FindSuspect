import cv2
from ultralytics import YOLO
import os

# YOLOv8 모델 로드
model = YOLO('yolov8n.pt')

# 비디오 파일 열기
video = cv2.VideoCapture('/data/FindSuspect/algorithm/crop_human/video/sample.mp4')

# 저장할 디렉토리 생성
if not os.path.exists('output_images'):
    os.makedirs('output_images')

frame_count = 0
interval = 30  # 매 30프레임마다 처리

while True:
    ret, frame = video.read()
    if not ret:
        break
    
    frame_count += 1
    if frame_count % interval == 0:
        # 원본 프레임 저장
        cv2.imwrite(f'output_images/frame_{frame_count}.jpg', frame)
        
        # YOLOv8로 객체 감지
        results = model(frame)
        
        person_count = 0
        for r in results:
            boxes = r.boxes
            for box in boxes:
                if box.cls == 0:  # 0은 'person' 클래스
                    person_count += 1
                    x1, y1, x2, y2 = map(int, box.xyxy[0])
                    cropped_human = frame[y1:y2, x1:x2]
                    
                    # 크롭된 이미지 저장
                    cv2.imwrite(f'output_images/frame_{frame_count}_person_{person_count}.jpg', cropped_human)

video.release()
print("처리 완료!")