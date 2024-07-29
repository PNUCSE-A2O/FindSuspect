import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Button, Card, CardContent, Box } from '@mui/material';
import {Link} from 'react-router-dom';

const Landing = () => {
    return (
        <>
        <HeaderAppBar/>
        <Container maxWidth="md" style={{textAlign: 'center', marginTop: '50px'}}>  
            <Typography className="landing-text" variant="h4" style={{marginBottom: '20px', fontWeight: 'bold'}}>
                Suspicious video
            </Typography>
            <Box display="flex" justifyContent="center" alignItems="center" flexDirection="column" mb={3}>
                <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px' }}>
                    <CardContent>
                        <Typography variant="h6">업로드 한 이미지</Typography>
                    </CardContent>
                </Card>
                <Button className="pulse" variant="contained" color="secondary" sx={{ borderRadius: 5 }} size="large"
                    component={Link} to="/adminUpload" style={{ marginTop: '10px', textTransform: 'none', fontWeight: 'bold' }}>
                    start
                </Button>
            </Box>
        </Container>
        </>
    );
};

export default Landing;