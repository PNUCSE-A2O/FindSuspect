import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, CircularProgress } from '@mui/material';
import { useLoadingState } from '../context/LoadingContext';
import { useNavigate } from 'react-router-dom';

const AdminLoading = () => {
    const navigate = useNavigate();
    const { loading, setLoading } = useLoadingState();

    

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