import React, { useState } from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Button, Slider, Box } from '@mui/material';
import axios from 'axios';
import FileUpload from '../components/FileUpload';
import AdminLoading from '../components/AdminLoading';

const AdminUpload = () => {
    const { loading, setLoading } = useLoadingState();
    const [frameInterval, setFrameInterval] = useState(30); // 기본 프레임 값

    const handleSliderChange = (event, newValue) => {
        setFrameInterval(newValue);
    };

    const handleApply = async () => {
        setLoading(true); 
        try {
            const response = await axios.post('/api/set-frame-interval', { frameInterval });
            console.log('프레임 간격 설정 성공:', response.data);
            setLoading(false); 
        } catch (error) {
            console.error('프레임 간격 설정 오류:', error);
            setLoading(false); 
        }
    };

    return (
        <>
            <HeaderAppBar />
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px', display: 'flex' }}>
                
                
                <Box style={{ width: '40%', marginRight: '20px', textAlign: 'left', padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
                    <Typography variant="h6" style={{ marginBottom: '20px', fontWeight: 'bold' }}>
                        Frame Detection Interval
                    </Typography>
                    <Slider
                        value={frameInterval}
                        onChange={handleSliderChange}
                        step={1}
                        min={1}
                        max={60}
                        valueLabelDisplay="auto"
                        sx={{
                            color: '#1976d2',  
                            height: 8,
                            '& .MuiSlider-thumb': {
                                height: 24,
                                width: 24,
                                backgroundColor: '#fff',
                                border: '2px solid currentColor',
                            },
                            '& .MuiSlider-track': {
                                height: 8,
                            },
                            '& .MuiSlider-rail': {
                                color: '#bfbfbf',
                                opacity: 1,
                                height: 8,
                            },
                        }}
                    />
                    <Typography variant="body1" style={{ marginTop: '10px', fontWeight: 'bold', textAlign: 'center' }}>
                        Every {frameInterval} Frame
                    </Typography>
                    <Button 
                        variant="contained" 
                        color="primary" 
                        onClick={handleApply} 
                        style={{ marginTop: '20px', width: '100%' }}
                    >
                        Apply
                    </Button>
                </Box>

                <Box style={{ width: '60%' }}>
                    {loading ? (
                        <AdminLoading />
                    ) : (
                        <>
                            <Typography className="landing-text" variant="h4" style={{ marginBottom: '20px', fontWeight: 'bold' }}>
                                Suspicious Video
                            </Typography>
                            <FileUpload />
                        </>
                    )}
                </Box>
            </Container>
        </>
    );
};

export default AdminUpload;
