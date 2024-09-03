import React from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { useLoadingState } from '../context/LoadingContext';
import { Container, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import FileUpload from '../components/FileUpload';


const AdminUpload = () => {
    const navigate = useNavigate();
    const {loading, setLoading} = useLoadingState();

    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Suspicious Video
            </Typography>
            <FileUpload />
        </Container>
        </>
    );
};

export default AdminUpload;