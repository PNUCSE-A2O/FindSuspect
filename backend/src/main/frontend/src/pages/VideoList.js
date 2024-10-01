import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, Box, Card, CardContent, Button, Grid } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const VideoList = () => {
    const [videoPaths, setVideoPaths] = useState([]); // 비디오 경로 배열 상태

    // 비디오 목록을 가져오는 함수
    useEffect(() => {
        axios.get('/api/get/video')
        .then(response => {
            const paths = response.data;
            if (paths.length > 0) {
                setVideoPaths(paths); // 비디오 경로 설정
            } else {
                alert('비디오 경로를 가져오는데 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('비디오 경로를 가져오는 중 오류 발생', error);
        });
    }, []);
    
    const deleteVideo = (video_name) => {
        axios.delete(`/api/video`, {
            params: { video_name } // URL 파라미터로 비디오 이름 전송
        })
        .then(() => {
            alert(`${video_name}이(가) 성공적으로 삭제되었습니다.`);
            setVideoPaths(prevPaths => prevPaths.filter(path => path !== video_name));
        })
        .catch(error => {
            console.error('비디오 삭제 중 오류 발생', error);
            alert('비디오 삭제에 실패했습니다.');
        });
    };

    const getFullVideoPath = (videoName) => {
        console.log(`/video/${videoName}/${videoName}`);
        return `/video/${videoName}/${videoName}`;
    };

    return (
        <>
            <UserHeaderAppBar />
            <Container maxWidth="lg" style={{ marginTop: '50px' }}>
                <Typography variant="h4" component="h2" gutterBottom fontWeight="bold">
                    비디오 목록
                </Typography>
                {videoPaths.length === 0 ? (
                    <Typography variant="body1">비디오가 없습니다.</Typography>
                ) : (
                    <Grid container spacing={4}>
                        {videoPaths.map((video, index) => (
                            <Grid item xs={12} sm={6} md={4} key={index}>
                                <Card>
                                    <CardContent>
                                        <video width="100%" height="200" controls>
                                            <source src={getFullVideoPath(video)} type="video/mp4" />
                                            브라우저에서 비디오를 지원하지 않습니다.
                                        </video>
                                        <Box mt={2}>
                                            <Typography variant="subtitle1" fontWeight="bold">
                                                비디오 이름: {video}
                                            </Typography>
                                        </Box>
                                        <Box mt={2} display="flex" justifyContent="space-between">
                                            <Button 
                                                variant="contained" 
                                                color="error"
                                                onClick={() => deleteVideo(video)}
                                            >
                                                삭제
                                            </Button>
                                        </Box>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                )}
            </Container>
        </>
    );
};

export default VideoList;
