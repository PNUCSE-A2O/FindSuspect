# 필요한 라이브러리 임포트
import torch
import cv2
import numpy as np
from ultralytics import YOLO
import os

# 모델 로드 (사전 학습된 YOLOv8n 모델 사용)
model = YOLO('yolov9m_35_32.pt')  # 필요에 따라 'yolov8s.pt', 'yolov8m.pt' 등 다른 모델 사용 가능

# 이미지 로드
image_path = 'KakaoTalk_Photo_2024-10-13-15-44-21.png'  # 분석할 이미지 경로
image = cv2.imread(image_path)
if image is None:
    print(f"이미지를 찾을 수 없습니다: {image_path}")
    exit()

# 모델 추론
results = model.predict(source=image, save=False)

# 클래스 이름 가져오기
class_names = model.names

# 결과에서 사람 클래스만 선택
person_class_id = 0  # COCO 데이터셋에서 사람 클래스 ID는 0번입니다
detections = results[0]  # 첫 번째 이미지의 결과
boxes = detections.boxes.xyxy.cpu().numpy()  # 바운딩 박스 좌표 [x1, y1, x2, y2]
scores = detections.boxes.conf.cpu().numpy()  # 신뢰도 점수
class_ids = detections.boxes.cls.cpu().numpy().astype(int)  # 클래스 ID

# 사람 클래스만 필터링
person_indices = np.where(class_ids == person_class_id)[0]
person_boxes = boxes[person_indices]
person_scores = scores[person_indices]
person_class_ids = class_ids[person_indices]

# 바운딩 박스와 레이블 그리기
annotated_image = image.copy()
for idx, (box, score) in enumerate(zip(person_boxes, person_scores)):
    x1, y1, x2, y2 = map(int, box)
    label = f"Person {score:.2f}"
    # 바운딩 박스 그리기
    cv2.rectangle(annotated_image, (x1, y1), (x2, y2), (0, 255, 0), 2)
    # 레이블 배경 그리기
    (label_width, label_height), baseline = cv2.getTextSize(label, cv2.FONT_HERSHEY_SIMPLEX, 0.5, 2)
    cv2.rectangle(annotated_image, (x1, y1 - label_height - 10), (x1 + label_width, y1), (0, 255, 0), -1)
    # 레이블 그리기
    cv2.putText(annotated_image, label, (x1, y1 - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2)

# 결과 이미지 저장
output_image_path = 'output_image.jpg'
cv2.imwrite(output_image_path, annotated_image)
print(f"결과 이미지가 '{output_image_path}'로 저장되었습니다.")

# 개별 사람 이미지 크롭 및 저장
cropped_images_dir = 'cropped_persons'
os.makedirs(cropped_images_dir, exist_ok=True)

for idx, box in enumerate(person_boxes):
    x1, y1, x2, y2 = map(int, box)
    # 이미지 경계 밖으로 나가지 않도록 좌표 보정
    x1 = max(0, x1)
    y1 = max(0, y1)
    x2 = min(image.shape[1], x2)
    y2 = min(image.shape[0], y2)
    cropped_image = image[y1:y2, x1:x2]
    cropped_image_path = os.path.join(cropped_images_dir, f'person_{idx+1}.jpg')
    cv2.imwrite(cropped_image_path, cropped_image)
    print(f"크롭된 사람 이미지가 '{cropped_image_path}'로 저장되었습니다.")
