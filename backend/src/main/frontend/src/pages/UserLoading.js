import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, CircularProgress, Box, Card, CardContent, CardMedia } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const UserLoading = () => {
    const [latestImagePath, setLatestImagePath] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get('/api/get/image')
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
            <UserHeaderAppBar />
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px' }}>
                <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" height="60vh">
                    {loading ? (
                        <>
                            <CircularProgress />
                            <Typography variant="body1" style={{ marginTop: '20px' }}>
                                Loading...
                            </Typography>
                        </>
                    ) : (
                        latestImagePath ? (
                            <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px' }}>
                                <CardMedia
                                    component="img"
                                    height="100%"
                                    image={`http://localhost:5000${latestImagePath}`}
                                    alt="Uploaded"
                                />
                            </Card>
                        ) : (
                            <Typography variant="body1">
                                업로드 된 이미지가 없습니다.
                            </Typography>
                        )
                    )}
                </Box>
            </Container>
        </>
    );
};

export default UserLoading;
