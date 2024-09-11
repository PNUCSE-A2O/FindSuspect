import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Box, Card, CardContent, Button } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import { useNavigate } from 'react-router-dom'; // useNavigate 훅 임포트

const History = () => {
    const [latestImagePath, setLatestImagePath] = useState(null);
    const {loading, setLoading} = useLoadingState();
    const [resultData, setResultData] = useState([]);
    const navigate = useNavigate(); // navigate 함수 사용
/*
    useEffect(() => {
        axios.get('/api/get/image')
            .then(response => {
                if(response && response.data.length > 0){
                    setLatestImagePath(response.data);
                } else {
                    alert('이미지 경로를 가져오는데 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('이미지 경로를 가져오는 중 오류 발생');
            })
    }, []);
*/
    useEffect(() => {
        setLoading(true);
        axios.get('/api/result')
            .then(response => {
                setLoading(false);
                setResultData(Object.entries(response.data)); 
                console.log(response.data);
            })
            .catch(error => {
                console.error('결과 데이터를 가져오는 중 오류 발생:', error);
            });
    }, []);

    return (
        <>
        <UserHeaderAppBar/>
        <Container maxWidth="lg" style={{ marginTop: '50px' }}>
            {resultData.map(([key, details], index) => (
                <Card sx={{ display: 'flex', mb: 2, justifyContent: 'space-between' }} key={index}>
                    <CardContent sx={{ flex: '1 0 auto', display: 'flex', alignItems: 'center' }}>
                        <img
                            src={`${latestImagePath}`}
                            alt="Latest"
                            style={{ width: 300, height: 300, objectFit: 'cover', marginRight: '20px' }}
                        />
                        <Box>
                            <Typography><strong>Result:</strong> </Typography>
                            <Typography variant="subtitle1"><strong>Video Source:</strong> {details.video_name}</Typography>
                            <Typography variant="subtitle1"><strong>Time:</strong> {details.time}</Typography>
                            <Typography variant="subtitle1"><strong>Detected Person:</strong></Typography>
                        </Box>
                    </CardContent>
                    <Button variant="contained" sx={{ bgcolor: 'skyblue', color: 'white', alignSelf: 'end', m: 2 }}
                    onClick={() => navigate('/detail', { state: { data: details } })}
                    >View</Button>
                </Card>
            ))}
        </Container>
        </>
    );
};

export default History;
