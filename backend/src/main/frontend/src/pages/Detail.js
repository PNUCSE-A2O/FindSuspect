import React from 'react';
import { Typography, Container, Card, CardContent, Grid, Paper, Table, TableBody, TableRow, TableCell, TableContainer, Button, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import UserHeaderAppBar from '../components/UserHeaderAppBar';
import axios from 'axios';
import qs from 'qs';
import History2 from './History2';

const Detail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { data, imagePath, rectanglePath, key } = location.state || {}; 
  console.log(data);
  console.log(imagePath);
  console.log(key);

  const handleSave = async () => {
    try {
      
      const saveData = {
        image_name: key, // 실제 이미지 이름으로 교체                 
      };
  
      
      const urlEncodedData = qs.stringify(saveData);
  
      
      await axios.post('/api/history', urlEncodedData, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });
  
      alert('History saved successfully!');
    } catch (error) {
      console.error('Error saving history:', error);
      alert('Failed to save history.');
    }
  };

  const handleShowHistory = async () => {
    navigate('/History2');
  };
    


  return (
    <>
    <UserHeaderAppBar/>
    <Container maxWidth="sm" style={{ marginTop: '50px', marginBottom: '20px' }}>
        <Card>
        <CardContent>
          <Typography variant="h5" component="div" gutterBottom>
            <strong>Result</strong>
          </Typography>

          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Typography variant="subtitle1" gutterBottom><strong>Input Crop:</strong></Typography>
              <Paper variant="outlined" sx={{ p: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <img src={imagePath || 'default-placeholder.png'} alt="Detected" style={{ width: '100%', maxWidth: '300px', height: 'auto' }} />
              </Paper>
              
            </Grid>

            <Grid item xs={12} md={6}>
              <Typography variant="subtitle1" gutterBottom><strong>Found Crop:</strong></Typography>
              <Paper variant="outlined" sx={{ p: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <img src={rectanglePath || 'default-placeholder.png'} alt="Result" style={{ width: '100%', maxWidth: '300px', height: 'auto' }} />
              </Paper>

            </Grid>

            <Grid item xs={12}>
              <Typography variant="subtitle1"><strong>Video Source:</strong> {data.video_name || 'N/A'}</Typography>
              <Typography variant="subtitle1"><strong>Time:</strong> {data.time || 'N/A'}</Typography>
              <Typography variant="subtitle1"><strong>Overall Confidence:</strong> {data.similarity || '0'}%</Typography>
            </Grid>

            <Grid item xs={12}>
              <Typography variant="subtitle1" style={{ marginTop: '20px' }}><strong>Features:</strong></Typography>
              <TableContainer component={Paper} sx={{ maxWidth: 400, margin: 'auto', marginBottom: '20px', marginTop: '20px' }}>
                <Table>
                    <TableBody>
                        <TableRow>
                        <TableCell align="center">Original Top5</TableCell>
                        <TableCell align="center">Feature</TableCell>
                        <TableCell align="center">File Top5</TableCell>
                        </TableRow>
                        {data.original_top5 && data.original_top5.map((feature, index) => (
                            <TableRow key={index}>
                                <TableCell align="center">{feature}</TableCell>
                                <TableCell align="center">{data.attr_words[index]}</TableCell>
                                <TableCell align="center">{data.file_top5[index]}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
              </TableContainer>
            </Grid>
          </Grid>
        </CardContent>

        
        <Box sx={{ display: 'flex', justifyContent: 'center', mb: 2 }}>
            <Button variant="contained" sx={{ bgcolor: 'skyblue', color: 'white', m: 2 }} onClick={handleSave}>
              Save
            </Button>
          <Button variant="contained" sx={{ bgcolor: 'skyblue', color: 'white', m: 2 }} onClick={handleShowHistory}>
            Show History
          </Button>
        </Box>
        
      </Card>
    </Container>
    </>
  );
};

export default Detail;
