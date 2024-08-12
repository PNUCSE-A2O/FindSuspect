import React from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import FileUpload from '../components/FileUpload';


const AdminUpload = () => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate('/adminLoading');
        setTimeout(() => {
            navigate('/');
        }, 5000); 
    };

    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Suspicious Video
            </Typography>
            <FileUpload/>
            <Button className="pulse" variant="contained" color="secondary" sx={{ borderRadius: 5 }} size="large"
                    onClick={handleClick} style={{ marginTop: '30px', textTransform: 'none', fontWeight: 'bold' }}>
                    check
            </Button>
        </Container>
        </>
    );
};

export default AdminUpload;