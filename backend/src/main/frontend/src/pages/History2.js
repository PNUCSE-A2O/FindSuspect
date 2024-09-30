import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, Box, Card, CardContent, Grid, Paper, Divider } from '@mui/material';
import UserHeaderAppBar from '../components/UserHeaderAppBar';

const History2 = () => {
  const [historyData, setHistoryData] = useState([]);

  // 히스토리 데이터를 API에서 가져와 상태로 설정
  useEffect(() => {
    axios.get('/api/history')
      .then(response => {
        console.log(response.data);
        setHistoryData(response.data); 
      })
      .catch(error => {
        console.error('히스토리 데이터를 가져오는 중 오류 발생:', error);
      });
  }, []);

  return (
    <>
      <UserHeaderAppBar />
      <Container maxWidth="lg" style={{ marginTop: '30px', marginBottom: '30px' }}>
        <Typography variant="h4" align="center" gutterBottom style={{ fontWeight: 'bold', color: 'black' }}>History</Typography>

        {historyData.length > 0 ? (
          historyData.map((item, index) => {
            // JSX 바깥에서 자바스크립트 변수 선언
            const rectangle = (item.video_image).replace('.jpg', '_rectangle.jpg'); // 변환된 이미지 파일명
            const rectangleImagePath = `video/${item.video_name}/${rectangle}`; // 이미지 경로

            return (
              <Card key={index} style={{ marginBottom: '20px', backgroundColor: '#f9f9f9', boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)' }}>
                <CardContent>
                  <Typography variant="h6" gutterBottom style={{ fontWeight: 'bold', color: 'black', textAlign: 'left', marginBottom: '10px' }}>Record {index + 1}</Typography>
                  <Divider style={{ marginBottom: '20px' }} />

                  <Grid container spacing={2}>
                    
                    <Grid item xs={12} md={6} style={{ paddingLeft: '50px', paddingTop: '30px' }}>
                      <Typography variant="subtitle1" style={{ fontWeight: 'bold', color: 'black', textAlign: 'left', marginBottom: '5px' }}>
                        <strong>Video Name:</strong>
                      </Typography>
                      <Typography variant="body1" style={{ marginBottom: '10px', color: 'black', textAlign: 'left', paddingLeft: '10px' }}>
                        {item.video_name}
                      </Typography>

                      <Typography variant="subtitle1" style={{ fontWeight: 'bold', color: 'black', textAlign: 'left', marginBottom: '5px' }}>
                        <strong>Time:</strong>
                      </Typography>
                      <Typography variant="body1" style={{ marginBottom: '10px', color: 'black', textAlign: 'left', paddingLeft: '10px' }}>
                        {item.time}
                      </Typography>

                      <Typography variant="subtitle1" style={{ fontWeight: 'bold', color: 'black', textAlign: 'left', marginBottom: '5px' }}>
                        <strong>Similarity:</strong>
                      </Typography>
                      <Typography variant="body1" style={{ marginBottom: '10px', color: 'black', textAlign: 'left', paddingLeft: '10px' }}>
                        {item.similarity.toFixed(2)}%
                      </Typography>
                    </Grid>

                    {/* 두 번째 열: 이미지 데이터 */}
                    <Grid item xs={12} md={6}>
                      <Typography variant="subtitle1" style={{ fontWeight: 'bold', color: 'black', marginBottom: '10px', textAlign: 'left' }}>
                        <strong>Images:</strong>
                      </Typography>
                      <Box display="flex" justifyContent="space-around" alignItems="center">
                        {/* Input Image */}
                        <Paper variant="outlined" style={{ padding: '10px', textAlign: 'center', backgroundColor: '#ecf0f1', marginRight: '5px' }}>
                          <img src={item.imageName} alt="Original" style={{ width: '100%', maxWidth: '200px', height: 'auto', marginBottom: '10px' }} />
                          <Typography variant="caption" align="center" style={{ color: 'black' }}>Input Image</Typography>
                        </Paper>
                        {/* Cropped Image */}
                        <Paper variant="outlined" style={{ padding: '10px', textAlign: 'center', backgroundColor: '#ecf0f1', marginRight: '5px' }}>
                          <img src={item.imageCropped} alt="Cropped" style={{ width: '100%', maxWidth: '200px', height: 'auto', marginBottom: '10px' }} />
                          <Typography variant="caption" align="center" style={{ color: 'black' }}>Cropped</Typography>
                        </Paper>
                        {/* Rectangle Image */}
                        {console.log(`Rectangle Image src: ${rectangleImagePath}`)}
                        <Paper variant="outlined" style={{ padding: '10px', textAlign: 'center', backgroundColor: '#ecf0f1' }}>
                          <img src={rectangleImagePath} alt="Rectangle" style={{ width: '100%', maxWidth: '200px', height: 'auto', marginBottom: '10px' }} />
                          <Typography variant="caption" align="center" style={{ color: 'black' }}>Rectangle</Typography>
                        </Paper>
                      </Box>
                    </Grid>

                    
                    <Grid item xs={12}>
                      <Typography variant="subtitle1" style={{ fontWeight: 'bold', color: 'black', marginTop: '20px', textAlign: 'center' }}>
                        <strong>Attributes & Features:</strong>
                      </Typography>
                      <table style={{ width: '100%', border: '1px solid #bdc3c7', marginTop: '10px', textAlign: 'center', color: 'black' }}>
                        <thead style={{ backgroundColor: '#ecf0f1', color: 'black' }}>
                          <tr>
                            <th style={{ padding: '10px', textAlign: 'left' }}>Attribute</th>
                            <th style={{ padding: '10px', textAlign: 'left' }}>Original Top 5</th>
                            <th style={{ padding: '10px', textAlign: 'left' }}>File Top 5</th>
                          </tr>
                        </thead>
                        <tbody>
                          {item.attr_words.map((word, i) => (
                            <tr key={i}>
                              <td style={{ padding: '10px', borderBottom: '1px solid #bdc3c7', textAlign: 'left', color: 'black' }}>{word}</td>
                              <td style={{ padding: '10px', borderBottom: '1px solid #bdc3c7', textAlign: 'left', color: 'black' }}>{item.original_top5[i]}</td>
                              <td style={{ padding: '10px', borderBottom: '1px solid #bdc3c7', textAlign: 'left', color: 'black' }}>{item.file_top5[i]}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </Grid>
                  </Grid>
                </CardContent>
              </Card>
            );
          })
        ) : (
          <Typography align="center" style={{ color: 'black', marginTop: '50px' }}>No history available.</Typography>
        )}
      </Container>
    </>
  );
};

export default History2;
