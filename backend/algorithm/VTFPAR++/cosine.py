from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import json

def get_similarity(feature):
    file_name = "features.json"
    result = {}
    with open(file_name, 'r') as f:
        data = json.load(f)  # 파일의 내용을 딕셔너리로 로드

    # 딕셔너리 형태로 변환된 데이터 순회
    for key, value in data.items():
        # 벡터화
        vec1 = np.array(feature).reshape(1, -1)
        vec2 = np.array(value).reshape(1, -1)
        print(f'{key}: {value}')  # key와 배열 값 출력
        # 코사인 유사도 계산
        similarity = cosine_similarity(vec1, vec2)
        result[key] = similarity[0][0]
        
    top_5 = dict(sorted(result.items(), key=lambda item: item[1], reverse=True)[:5])    
    return top_5