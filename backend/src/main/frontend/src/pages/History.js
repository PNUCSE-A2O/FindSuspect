import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Box, Card, CardContent, Button } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import { useNavigate } from 'react-router-dom'; 

const History = () => {
    const [latestImagePath, setLatestImagePath] = useState(null);
    const {loading, setLoading} = useLoadingState(null);
    const [resultData, setResultData] = useState([]);
    const navigate = useNavigate(); 

    useEffect(() => {
        axios.get('/api/get/image')
            .then(response => {
                console.log(response.data);
                if (response && response.data) {
                    let basePath = response.data;
                    setLatestImagePath(`${basePath}`);
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
                console.log(response.data); 
                setLoading(false);
                setResultData(Object.entries(response.data)); 
            })
            .catch(error => {
                console.error('결과 데이터를 가져오는 중 오류 발생:', error);
            });
    }, []);

    
    useEffect(() => {
        console.log("Updated resultData:", resultData); 
    }, [resultData]);

    return (
        <>
        <UserHeaderAppBar/>
        <Container maxWidth="lg" style={{ marginTop: '50px' }}>
            {resultData.map(([key, details], index) => {
                //console.log("Key:", key);
                //console.log("Details:", details);
                //console.log("Index:", index);
                
                const firstKey = Object.keys(details)[0];
                const videoDetails = details[firstKey];  // 실제 데이터 접근
                const rectangle = firstKey.replace('.jpg', '_rectangle.jpg'); 
                const rectangleImagePath = `video/${videoDetails.video_name}/${rectangle}`;
                console.log(rectangleImagePath);
                console.log(latestImagePath);
                
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
                                <Typography variant="subtitle1"><strong>Video Source:</strong> {videoDetails.video_name}</Typography>
                                <Typography variant="subtitle1"><strong>Time:</strong> {videoDetails.time}</Typography>
                                <Typography variant="subtitle1"><strong>Input Image:</strong></Typography>
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
                                data: videoDetails,
                                imagePath: latestImagePath,
                                rectanglePath: rectangleImagePath,
                                key: firstKey
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
