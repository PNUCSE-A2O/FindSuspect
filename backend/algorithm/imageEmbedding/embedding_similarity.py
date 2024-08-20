import numpy as np
import os

# 'output' 폴더 내의 .npy 파일 로드
def load_embeddings(directory):
    file_paths = [os.path.join(directory, f) for f in os.listdir(directory) if f.endswith('.npy')]
    if len(file_paths) < 2:
        raise ValueError("There should be at least two npy files in the directory.")
    return np.load(file_paths[0]), np.load(file_paths[1])

# 'output' 디렉토리 내에서 임베딩 로드
embedding1, embedding2 = load_embeddings('output')

# 코사인 유사도 계산
def cosine_similarity(embedding1, embedding2):
    dot_product = np.dot(embedding1, embedding2.T)
    norm_a = np.linalg.norm(embedding1)
    norm_b = np.linalg.norm(embedding2)
    return dot_product / (norm_a * norm_b)

# 두 임베딩 간의 유사도 계산
similarity = cosine_similarity(embedding1, embedding2)
print(f'Cosine Similarity: {similarity}')