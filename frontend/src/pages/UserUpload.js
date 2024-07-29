import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import FileUpload from '../components/FileUpload';

const UserUpload = () => {
    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Put Suspect's Image
            </Typography>
            <FileUpload/>
            <Button className="pulse" variant="contained" color="secondary" sx={{borderRadius: 5}} size="large"
                component={Link} to="/adminUpload" style={{marginTop: '30px', textTransform: 'none', fontWeight: 'bold'}}>
                check
            </Button>
        </Container>
        </>
    );
};

export default UserUpload;