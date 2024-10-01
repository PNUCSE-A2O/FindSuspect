import React, { useState } from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Button, Slider, Box } from '@mui/material';
import axios from 'axios';
import FileUpload from '../components/FileUpload';
import AdminLoading from '../components/AdminLoading';

const AdminUpload = () => {
    const { loading, setLoading } = useLoadingState(); 

    return (
        <>
            <HeaderAppBar />
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px'}}>
                <Box>
                    {loading ? (
                        <AdminLoading />
                    ) : (
                        <>
                            <Typography className="landing-text" variant="h4" style={{ marginBottom: '20px', fontWeight: 'bold' }}>
                                Suspicious Video
                            </Typography>
                            <FileUpload/>
                        </>
                    )}
                </Box>
            </Container>
        </>
    );
};

export default AdminUpload;
