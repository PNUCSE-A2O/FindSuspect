# Image Embedding Test
- images에 있는 이미지를 image embedding(npy) 파일로 output에 저장

## Environment
- python 3.8.19, pip 패키지는 requirements.txt에 있습니다.

## generate_image_prompts.py
- 이미지에서 image embedding(npy)파일을 추출해내는 코드
```
$ python generate_image_prompts.py --image-dir images --out-dir output --out-file embeddings.npy
```

## embedding_similarity.py
- 뽑아낸 image embedding(npy) 파일 두 개 간의 유사도 비교(코사인 유사도)
- 실행 전, output 폴더에 npy 파일 두개가 있어야 합니다.
```
$ python embedding_similarity.py
```