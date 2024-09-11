import React from 'react';
import { Typography, Container, Card, CardContent, Grid, Paper, Table, TableBody, TableRow, TableCell, TableContainer } from '@mui/material';
import { useLocation } from 'react-router-dom';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const Detail = () => {
  const location = useLocation();
  const { data } = location.state || {}; 

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
              <Typography variant="subtitle1" gutterBottom><strong>Input Image:</strong></Typography>
              <Paper variant="outlined" sx={{ p: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <img src={data.imagePath || 'default-placeholder.png'} alt="Detected" style={{ width: '100%', maxWidth: '300px', height: 'auto' }} />
              </Paper>
              
            </Grid>

            <Grid item xs={12} md={6}>
              <Typography variant="subtitle1" gutterBottom><strong>Found Image:</strong></Typography>
              <Paper variant="outlined" sx={{ p: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <img src={data.foundImagePath || 'default-placeholder.png'} alt="Result" style={{ width: '100%', maxWidth: '300px', height: 'auto' }} />
              </Paper>

            </Grid>

            <Grid item xs={12}>
              <Typography variant="subtitle1"><strong>Video Source:</strong> {data.video_name || 'N/A'}</Typography>
              <Typography variant="subtitle1"><strong>Time:</strong> {data.time || 'N/A'}</Typography>
              <Typography variant="subtitle1"><strong>Overall Confidence:</strong> {data.similarity || '0'}%</Typography>
            </Grid>

            <Grid item xs={12}>
              <Typography variant="subtitle1" style={{ marginTop: '20px' }}><strong>Features:</strong></Typography> {/* 표 위에 공간 추가 */}
              <TableContainer component={Paper} sx={{ maxWidth: 400, margin: 'auto', marginBottom: '20px', marginTop: '20px' }}> {/* 표 아래 공간 추가 */}
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
      </Card>
    </Container>
    </>
  );
};

export default Detail;
