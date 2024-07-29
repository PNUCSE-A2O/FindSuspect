import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Button, CircularProgress } from '@mui/material';
import ImageSection from '../components/ImageSection';
import loading from '../picture/loading.png';

const AdminLoading = () => {
    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}> 
            <CircularProgress />     
            <Typography variant="body1" style={{marginTop: '20px'}}>
                    Loading...
            </Typography>
        </Container>
        
        </>
    );
};

export default AdminLoading;