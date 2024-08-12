import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, CircularProgress } from '@mui/material';

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