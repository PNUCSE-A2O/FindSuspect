import cv2
from ultralytics import YOLO
import os
import shutil
from full_body import is_full_body
import logging

logging.getLogger("ultralytics").setLevel(logging.ERROR)
# YOLOv8 모델 로드
model = YOLO('yolov10n.pt', verbose=False)

# 비디오 파일이 있는 폴더 경로
video_folder = '/data/FindSuspect/backend/src/main/frontend/public/video'

# 저장할 디렉토리 생성
output_folder = 'video_output'
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
for video_file in os.listdir(video_folder):
    if video_file.endswith(('.mp4', '.avi', '.mov')):  # 비디오 파일 확장자 추가 가능
        video_path = os.path.join(video_folder, video_file)
        
        # 비디오 파일 열기
        video = cv2.VideoCapture(video_path)

        frame_count = 0
        interval = 30  # 매 30프레임마다 처리

        while True:
            ret, frame = video.read()
            if not ret:
                break
            
            frame_count += 1
            if frame_count % interval == 0:
                # YOLOv8로 객체 감지
                image_height, image_width = frame.shape[:2]
                results = model(frame)
                
                person_count = 0
                for r in results:
                    boxes = r.boxes
                    for box in boxes :
                        if box.cls == 0 and is_full_body(box, image_height, image_width):  # 0은 'person' 클래스
                            person_count += 1
                            x1, y1, x2, y2 = map(int, box.xyxy[0])
                            cropped_human = frame[y1:y2, x1:x2]
                            
                            # 크롭된 이미지 저장
                            output_filename = f'{output_folder}/{os.path.splitext(video_file)[0]}_frame{frame_count}_person{person_count}.jpg'
                            cv2.imwrite(output_filename, cropped_human)

        video.release()