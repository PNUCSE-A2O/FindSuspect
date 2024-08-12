import React, { useState, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios';

const ImageUploader = () => {
  const [image, setImage] = useState(null);

  const onDrop = useCallback((acceptedFiles) => {
    const file = acceptedFiles[0];
    const formData = new FormData();
    formData.append('file', file);
    axios.post('http://localhost:5000/api/product/image', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((response) => {
      if (response.data.success) {
        setImage(response.data.fileurl);
      } else {
        alert('파일 저장 실패');
      }
    })
    .catch((error) => {
      console.error('파일 업로드 중 오류 발생:', error);
    });
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div {...getRootProps()} style={styles.dropzone}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>파일을 여기에 드래그 앤 드롭하세요...</p>
      ) : (
        <div style={styles.content}>
          <button style={styles.uploadButton}>Upload Image</button>
          <p>또는 파일을 여기에 드래그 앤 드롭하세요.</p>
          <p style={styles.paste}>이미지 또는 URL을 붙여넣기</p>
        </div>
      )}
      {image && <img src={image} alt="Uploaded" style={styles.image} />}
    </div>
  );
};

const styles = {
  dropzone: {
    width: '400px',
    height: '200px',
    border: '2px dashed #cccccc',
    borderRadius: '4px',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: 'pointer',
    marginBottom: '20px',
    padding: '10px',
  },
  content: {
    textAlign: 'center',
  },
  uploadButton: {
    width: '150px',
    borderRadius: '3.5px',
    backgroundColor: '#000000',
    color: '#F4F4F4',
    fontWeight: 'bold',
    boxShadow: 'none',
    marginBottom: '10px',
  },
  paste: {
    color: '#b0b0b0',
  },
  image: {
    width: '100%',
    height: 'auto',
    marginTop: '20px',
  },
};

export default ImageUploader;
