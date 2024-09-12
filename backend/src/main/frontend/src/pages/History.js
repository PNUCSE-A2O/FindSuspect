import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Box, Card, CardContent, Button } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import { useNavigate } from 'react-router-dom'; 

const History = () => {
    const [latestImagePath, setLatestImagePath] = useState(null);
    const {loading, setLoading} = useLoadingState();
    const [resultData, setResultData] = useState([]);
    const navigate = useNavigate(); 

    useEffect(() => {
        axios.get('/api/get/image')
            .then(response => {
                console.log(response.data);
                if (response && response.data) {
                    let basePath = response.data;
    
                    const baseImagePath = basePath.replace(/^public\//, '');
                    
                    setLatestImagePath(`/${baseImagePath}`);
                }
            })
            .catch(error => {
                console.error('이미지 경로를 가져오는 중 오류 발생', error);
            });
    }, []);

    useEffect(() => {
        setLoading(true);
        axios.get('/api/result')
            .then(response => {
                setLoading(false);
                setResultData(Object.entries(response.data)); 
            })
            .catch(error => {
                console.error('결과 데이터를 가져오는 중 오류 발생:', error);
            });
    }, []);

    return (
        <>
        <UserHeaderAppBar/>
        <Container maxWidth="lg" style={{ marginTop: '50px' }}>
            {resultData.map(([key, details], index) => {
                // 변수는 JSX 안에 들어가기 전에 선언되어야 합니다.
                const rectangleImagePath = `/video/${details.video_name}/${key}`;

                return (
                    <Card sx={{ display: 'flex', mb: 2, justifyContent: 'space-between' }} key={index}>
                        <CardContent sx={{ flex: '1 0 auto', display: 'flex', alignItems: 'center' }}>
                            <img
                                src={rectangleImagePath}
                                alt="Latest"
                                style={{ width: 300, height: 300, objectFit: 'scale-down', marginRight: '20px' }}
                            />
                            <Box>
                                <Typography><strong>Result:</strong> </Typography>
                                <Typography variant="subtitle1"><strong>Video Source:</strong> {details.video_name}</Typography>
                                <Typography variant="subtitle1"><strong>Time:</strong> {details.time}</Typography>
                                <Typography variant="subtitle1"><strong>Detected Person:</strong></Typography>
                                <img
                                    src={`${latestImagePath}`}
                                    alt="Rectangle"
                                    style={{ width: 100, height: 100, objectFit: 'scale-down', marginRight: '20px' }}
                                />
                            </Box>
                        </CardContent>
                        <Button variant="contained" sx={{ bgcolor: 'skyblue', color: 'white', alignSelf: 'end', m: 2 }}
                         onClick={() => navigate('/detail', { 
                            state: { 
                                data: details,
                                imagePath: latestImagePath,
                                foundImagePath: rectangleImagePath
                            } 
                        })}
                        >View</Button>
                    </Card>
                );
            })}
        </Container>
        </>
    );
};

export default History;
