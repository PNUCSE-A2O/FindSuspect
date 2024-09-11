import os
import json
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

def get_similarity(feature, new_attr_words, image_name):
    # features 폴더에서 파일 목록을 가져오기
    input_folder = "features"
    output_folder = f"result/{image_name}"
    
    # 출력 폴더가 없으면 생성
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # features 폴더 내의 모든 JSON 파일에 대해 작업 수행
    for file_name in os.listdir(input_folder):
        if file_name.endswith('.json'):  # JSON 파일만 처리
            file_path = os.path.join(input_folder, file_name)
            
            with open(file_path, 'r') as f:
                data = json.load(f)  # 각 파일의 내용을 딕셔너리로 로드

            result = {}  # 최종 결과 저장
            # 딕셔너리 형태로 변환된 데이터 순회
            for key, value in data.items():
                # 벡터화
                vec1 = np.array(feature).reshape(1, -1)
                vec2 = np.array(value).reshape(1, -1)
                # 코사인 유사도 계산
                similarity = cosine_similarity(vec1, vec2)
                result[key] = similarity[0][0]

            # 유사도 상위 5개의 속성 추출
            top_5_similarities = dict(sorted(result.items(), key=lambda item: item[1], reverse=True)[:5])
            detailed_result = {}
            for key, overall_similarity in top_5_similarities.items():
                value = data[key]
                feature_similarities = []

                # 각 feature의 유사도를 계산 (속성 이름은 동일)
                for i in range(len(feature)):
                    feature_vec1 = np.array([feature[i]]).reshape(1, -1)
                    feature_vec2 = np.array([value[i]]).reshape(1, -1)
                    feature_similarity = cosine_similarity(feature_vec1, feature_vec2)[0][0]
                    feature_similarities.append((feature_similarity, feature[i], value[i], new_attr_words[i]))

                # 유사도 상위 5개의 feature와 속성 이름 추출
                top_5_features = sorted(feature_similarities, key=lambda x: x[0], reverse=True)[:5]
                top_5_input_values = [item[1] for item in top_5_features]  # 상위 5개의 원본 feature 값
                top_5_file_values = [item[2] for item in top_5_features]  # 상위 5개의 파일 feature 값
                top_5_names = [item[3] for item in top_5_features]  # 상위 5개의 속성 이름
                video_name_idx = key.find('mp4') + len('mp4')
                video_name = key[:video_name_idx]
                detailed_result[key] = {
                    "video_name": video_name,
                    "similarity": overall_similarity * 100,  # 백분율로 변환
                    "original_top5": top_5_input_values,
                    "file_top5": top_5_file_values,
                    "attr_words": top_5_names
                }

            output_path = f"/data/FindSuspect/backend/src/main/resources/data/{image_name}"
            if not os.path.exists(output_path):
                os.makedirs(output_path)

            # 결과를 key 이름으로 result 폴더에 저장
            output_file_path = os.path.join(output_folder, f"/data/FindSuspect/backend/src/main/resources/data/{image_name}/{file_name}")
            with open(output_file_path, 'w') as out_file:
                json.dump(detailed_result, out_file, indent=4)
