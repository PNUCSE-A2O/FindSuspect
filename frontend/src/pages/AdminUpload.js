import React from 'react';
//import {Link} from 'react-router-dom';
import ImageSection from '../components/ImageSection';
import cctv from '../picture/cctv.png';
import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import FileUpload from '../components/FileUpload';

const AdminUpload = () => {
    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Suspicious Video
            </Typography>
            <FileUpload/>
            <Button className="pulse" variant="contained" color="secondary" sx={{borderRadius: 5}} size="large"
                component={Link} to="/userUpload" style={{marginTop: '30px', textTransform: 'none', fontWeight: 'bold'}}>
                start
            </Button>
        </Container>
        </>
    );
};

export default AdminUpload;