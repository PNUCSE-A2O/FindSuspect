import React, { useState } from 'react';
import axios from 'axios';
import MyDropzone from './dropzone';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useLoadingState } from '../context/LoadingContext';

function VideoUpload() {
    const [videoFile, setVideos] = useState([]);
    const { loading, setLoading } = useLoadingState();
    const [isDisabled, setIsDisabled] = useState(true);
    const [file2send, setFile2send] = useState([]);
    const navigate = useNavigate();
    let formData = new FormData();

    // 영상 업로드 처리 함수
    const handleVideoUpload = () => {
        setLoading(true);
        const config = {
            header: { 'content-type': 'multipart/form-data' }
        };
        formData.set("file", file2send[0]);

        axios.post('/api/upload/video', formData, config)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    setVideos([...videoFile, response.data.filePath]);
                    alert('비디오 업로드 완료');
                    navigate('/history');
                } else {
                    alert('파일 저장 실패');
                }
            })
            .catch(error => {
                console.error('비디오 업로드 중 오류 발생:', error);
            })
            .finally(() => {
                setLoading(false);
                setIsDisabled(true);
            });
    };

    const dropHandler = (files) => {
        setFile2send(files);
        console.log(files);
        setTimeout(() => { setIsDisabled(false); }, 1000);
    };

    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <MyDropzone onDrop={dropHandler} />
            </div>
            <Button
                className="pulse"
                variant="contained"
                color="secondary"
                sx={{ borderRadius: 5 }}
                size="large"
                onClick={handleVideoUpload}
                disabled={isDisabled}
                style={{ marginTop: '30px', textTransform: 'none', fontWeight: 'bold' }}
            >
                video upload
            </Button>
        </>
    );
}

export default VideoUpload;
