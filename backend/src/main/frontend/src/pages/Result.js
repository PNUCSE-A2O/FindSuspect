import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLoadingState } from '../context/LoadingContext';
//import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Box, Card, CardContent, Table, TableBody, TableCell, TableRow, TableContainer, Paper } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const Result = () => {
    const {loading, setLoading} = useLoadingState();
    const [latestImagePath, setLatestImagePath] = useState(null);
    const [resultData, setResultData] = useState([]);

    useEffect(() => {
        setLoading(true);
        axios.get('/api/get/image')
        .then(response => {
            console.log(response.data);
            if(response){
                const paths = response.data;
                console.log(paths);
                if(paths.length > 0){
                    setLatestImagePath(paths);
                } else {
                    alert('이미지 경로를 가져오는데 실패했습니다.');
                }
            }
        })
        .catch(error => {
            console.error('이미지 경로를 가져오는 중 오류 발생');
        })
    }, []);
    
    
    useEffect(() => {
        setLoading(true);
        axios.get('/api/result')
            .then(response => {
                setLoading(false);
                setResultData(response.data); 
            })
            .catch(error => {
                console.error('결과 데이터를 가져오는 중 오류 발생:', error);
            });
    }, []);
    /*
    useEffect(() => {
        const testData = [
            { imageName: "image1.jpg", time: "2024-08-24T10:00:00", accuracy: 95 },
            { imageName: "image2.jpg", time: "2024-08-24T11:00:00", accuracy: 88 },
            { imageName: "image3.jpg", time: "2024-08-24T12:00:00", accuracy: 76 }
        ];
        setResultData(testData);
        setLoading(false); // 로딩 상태 종료
    }, []);
    */
    return (
        <>
            <UserHeaderAppBar/>
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px' }}>
                <Box display="flex" flexDirection="column" alignItems="center" >
                    <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px'  }}>
                        <CardContent sx={{ width: '100%', height: '100%', padding: 0, marginTop: '25px' }}>
                        {loading ? (
                            <Typography variant="h6">Loading...</Typography>
                        ) : latestImagePath ? (
                            <img
                                src={`${latestImagePath}`}
                                alt="Latest"
                                style={{ width: '100%', height: '100%', objectFit: 'scale-down' }}
                            />
                        ) : (
                            <Typography>No image available</Typography>
                        )}
                        </CardContent>
                    </Card>
                    <TableContainer component={Paper} sx={{ maxWidth: 400 }}>
                        <Table>
                            <TableBody>
                                <TableRow>
                                    <TableCell align="center">Time</TableCell>
                                    <TableCell align="center">Accuracy</TableCell>
                                </TableRow>
                                {resultData.length > 0 ? (
                                    resultData.map((result, index) => (
                                        <TableRow key={index}>
                                            <TableCell align="center">{result.time}</TableCell>
                                            <TableCell align="center">{result.accuracy}%</TableCell>
                                        </TableRow>
                                    ))
                                ) : (
                                    <TableRow>
                                        <TableCell align="center" colSpan={2}>No Results Available</TableCell>
                                    </TableRow>
                                )} 
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Container>
        </>
    );
};

export default Result;
