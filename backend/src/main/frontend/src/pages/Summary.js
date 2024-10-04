import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Card, CardContent, Button, Typography } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import { useNavigate } from 'react-router-dom';

const Summery = () => {
    const [summaryData, setSummaryData] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/result')
            .then(response => {
                const result = response.data;
                const groupedByVideo = {};

                // 데이터를 각 비디오 이름을 기준으로 그룹화
                Object.entries(result).forEach(([key, value]) => {
                    const firstKey = Object.keys(value)[0];
                    const videoName = value[firstKey].video_name;

                    if (!groupedByVideo[videoName]) {
                        groupedByVideo[videoName] = [];
                    }
                    groupedByVideo[videoName].push({ key, details: value[firstKey] });
                });

                setSummaryData(groupedByVideo);
            })
            .catch(error => {
                console.error('Error fetching summary data:', error);
            });
    }, []);

    const handleViewHistory = (videoName) => {
        navigate('/history', { state: { videoName } });
    };

    return (
        <>
            <UserHeaderAppBar />
            <Container maxWidth="lg" style={{ marginTop: '50px' }}>
                {Object.entries(summaryData).map(([videoName, videoResults], index) => (
                    <Card sx={{ mb: 2 }} key={index}>
                        <CardContent>
                            <Typography variant="h6">{videoName}</Typography>
                            <Typography variant="subtitle1">결과 수: {videoResults.length}</Typography>
                            <Button 
                                variant="contained" 
                                sx={{ bgcolor: 'skyblue', color: 'white', mt: 2 }} 
                                onClick={() => handleViewHistory(videoName)}
                            >
                                히스토리 보기
                            </Button>
                        </CardContent>
                    </Card>
                ))}
            </Container>
        </>
    );
};

export default Summery;
