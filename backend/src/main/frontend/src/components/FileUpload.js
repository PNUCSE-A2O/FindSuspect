import React, { useState } from 'react';
import axios from 'axios';
import MyDropzone from './dropzone';

function FileUpload() {
    const [imageFile, setImages] = useState([]);
    const [videoFile, setVideos] = useState([]);

    const dropHandler = (files) => {
        let formData = new FormData();

        const config = {
            header: { 'content-type': 'multipart/form-data' }
        };
        formData.append("file", files[0]);

        const fileType = files[0].type.split('/')[0]; // Get the file type (image or video)
        const uploadUrl = fileType === 'image' ? '/api/upload/image' : '/api/upload/video';

        axios.post(uploadUrl, formData, config)
            .then(response => {
                if (response.data.success) {
                    if (fileType === 'image') {
                        setImages([...imageFile, response.data.filePath]);
                    } else if (fileType === 'video') {
                        setVideos([...videoFile, response.data.filePath]);
                    }
                } else {
                    alert('파일 저장 실패');
                }
            })
            .catch(error => {
                console.error('파일 업로드 중 오류 발생:', error);
            });
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <MyDropzone onDrop={dropHandler} />
        </div>
    );
}

export default FileUpload;
