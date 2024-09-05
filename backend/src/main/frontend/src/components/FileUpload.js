import React, { useState } from 'react';
import axios from 'axios';
import MyDropzone from './dropzone';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useLoadingState } from '../context/LoadingContext';

function FileUpload() {
    const [imageFile, setImages] = useState([]);
    const [videoFile, setVideos] = useState([]);
    const {loading, setLoading} = useLoadingState();
    const [isDisabled, setIsDisabled] = useState(true);
    const [file2send, setFile2send] = useState([]);
    const navigate = useNavigate();
    let formData = new FormData();

    const handleClick = () => {
        
        setLoading(true);
        const config = {
            header: { 'content-type': 'multipart/form-data' }
        };
        formData.set("file", file2send[0]);

        const fileType = file2send[0].type.split('/')[0]; // Get the file type (image or video)
        const uploadUrl = fileType === 'image' ? '/api/upload/image' : '/api/upload/video';
        if(fileType == 'image'){
            navigate('/userLoading');
        } else {
            navigate('/adminLoading');
        }
        console.log(file2send);
        
        axios.post(uploadUrl, formData, config)
            .then(response => {
                console.log(response);
                if (response.statusText == 'OK') {
                    if (fileType === 'image') {
                        setImages([...imageFile, response.data.filePath]);
                    } else if (fileType === 'video') {
                        setVideos([...videoFile, response.data.filePath]);
                        alert('파일 업로드 완료');
                        navigate('/Home');
                    }
                    setLoading(false);
                    setIsDisabled(true);

                } else {
                    alert('파일 저장 실패');
                }
            })
            .catch(error => {
                console.error('파일 업로드 중 오류 발생:', error);
            });
    };

    const dropHandler = (files) => {
        setFile2send(files);
        console.log(files);
        setTimeout(() => {setIsDisabled(false);}, 1000);
        
    };

    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <MyDropzone onDrop={dropHandler} />
            </div>
            <Button className="pulse" variant="contained" color="secondary" sx={{ borderRadius: 5 }} size="large"
            onClick={handleClick} disabled={isDisabled} style={{ marginTop: '30px', textTransform: 'none', fontWeight: 'bold' }}>
            check
            </Button>
        </>
    );
}

export default FileUpload;
