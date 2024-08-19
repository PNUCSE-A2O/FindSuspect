import cv2
from ultralytics import YOLO
import os
import shutil
from full_body import is_full_body
import logging

logging.getLogger("ultralytics").setLevel(logging.ERROR)
# YOLOv8 모델 로드
model = YOLO('yolov8n.pt', verbose=False)

# 비디오 파일이 있는 폴더 경로
image_folder = '/data/FindSuspect/algorithm/crop/image'

output_folder = 'image_output'

if os.path.exists(output_folder):
    # 폴더 내의 모든 파일 삭제
    for filename in os.listdir(output_folder):
        file_path = os.path.join(output_folder, filename)
        try:
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path)
        except Exception as e:
            print(f'Failed to delete {file_path}. Reason: {e}')
else:
    # 폴더가 없으면 새로 생성
    os.makedirs(output_folder)

# 비디오 폴더 내의 모든 파일에 대해 처리
for image_file in os.listdir(image_folder):
    if image_file.endswith(('.jpg', '.jpeg', '.png')):  # 비디오 파일 확장자 추가 가능
        image_path = os.path.join(image_folder, image_file)
        image = cv2.imread(image_path)

        image_height, image_width = image.shape[:2]
        results = model(image)
        person_count = 0
        for r in results:
            boxes = r.boxes
            for box in boxes:
                if box.cls == 0 and is_full_body(box, image_height, image_width):  # 0은 'person' 클래스
                    person_count += 1
                    x1, y1, x2, y2 = map(int, box.xyxy[0])
                    cropped_human = image[y1:y2, x1:x2]
                    
                    # 크롭된 이미지 저장
                    output_filename = f'{os.path.splitext(image_file)[0]}_person{person_count}.jpg'
                    output_path = os.path.join(output_folder, output_filename)
                    cv2.imwrite(output_path, cropped_human)