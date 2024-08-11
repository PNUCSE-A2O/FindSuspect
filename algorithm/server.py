import subprocess
import os

def run_python_file(file_path):
    # 파일이 있는 디렉토리로 이동
    file_dir = os.path.dirname(file_path)
    original_dir = os.getcwd()
    os.chdir(file_dir)

    try:
        # Python 파일 실행
        result = subprocess.run(['python', file_path], capture_output=True, text=True, check=True)
        print(result.stdout)
    except subprocess.CalledProcessError as e:
        print(f"Error occurred: {e}")
        print(f"Error output: {e.output}")
    finally:
        # 원래 디렉토리로 돌아감
        os.chdir(original_dir)

# 현재 디렉토리 경로
current_dir = os.path.dirname(os.path.abspath(__file__))

# a.py와 b.py의 경로
a_path = os.path.join(current_dir, 'video_to_crop', 'crop.py')
b_path = os.path.join(current_dir, 'VTFPAR++', 'all_image_test.py')

# Python 파일 실행
run_python_file(a_path)
run_python_file(b_path)
