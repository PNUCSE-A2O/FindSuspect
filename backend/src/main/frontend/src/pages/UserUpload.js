import { Container, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useLoadingState } from '../context/LoadingContext';
import FileUpload from '../components/FileUpload';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import ImageUploader from '../components/ImageUploader';

const UserUpload = () => {
    const navigate = useNavigate();
    const {loading, setLoading} = useLoadingState();

    const handleClick = () => {
        setLoading(true);
        navigate('/userLoading');
    };
    
    return (
        <>
        <UserHeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Put Suspect's Image
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

export default UserUpload;
