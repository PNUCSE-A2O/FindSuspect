import React, { useEffect, useState } from 'react';
import axios from 'axios';
import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Box, Card, CardContent, CardMedia, Table, TableBody, TableCell, TableRow, TableContainer, Paper } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const Result = () => {
    const [latestImagePath, setLatestImagePath] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get('http://localhost:5000/api/get/image')
            .then(response => {
                if (response.data.success) {
                    const paths = response.data.imagePaths;
                    if (paths.length > 0) {
                        setLatestImagePath(paths[paths.length - 1]);
                    }
                } else {
                    alert('이미지 경로를 가져오는 데 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('이미지 경로를 가져오는 중 오류 발생:', error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    return (
        <>
            <UserHeaderAppBar/>
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px' }}>
                <Box display="flex" flexDirection="column" alignItems="center" >
                    <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px'  }}>
                        {loading ? (
                            <Typography variant="h6">Loading...</Typography>
                        ) : latestImagePath ? (
                            <CardMedia
                                component="img"
                                height="100%"
                                image={`http://localhost:5000${latestImagePath}`}
                                alt="Uploaded"
                            />
                        ) : (
                            <Typography variant="h6">업로드 된 이미지가 없습니다.</Typography>
                        )}
                    </Card>
                    <TableContainer component={Paper} sx={{ maxWidth: 400 }}>
                        <Table>
                            <TableBody>
                                <TableRow>
                                    <TableCell align="center">Time</TableCell>
                                    <TableCell align="center">Accuracy</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell align="center">00:07:15</TableCell>
                                    <TableCell align="center">83%</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell align="center">00:07:15</TableCell>
                                    <TableCell align="center">83%</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell align="center">00:07:15</TableCell>
                                    <TableCell align="center">83%</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Container>
        </>
    );
};

export default Result;
