import React from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, CircularProgress, Box, Card, CardContent } from '@mui/material';

const UserLoading = () => {
    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" height="60vh">
            <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px' }}>
                        <CardContent>
                            <Typography variant="h6">업로드 한 이미지</Typography>
                        </CardContent>
                    </Card>
                <CircularProgress />
                <Typography variant="body1" style={{marginTop: '20px'}}>
                    Loading...
                </Typography>
            </Box>
        </Container>
        </>
    );
};

export default UserLoading;
