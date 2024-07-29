import React from 'react';
import HeaderAppBar from '../components/HeaderAppBar';
import { Container, Typography, Box, Card, CardContent, Table, TableBody, TableCell, TableRow, TableContainer, Paper } from '@mui/material';
import cctv from '../picture/cctv.png';

const Result = () => {
    return (
        <>
            <HeaderAppBar />
            <Container maxWidth="md" style={{ textAlign: 'center', marginTop: '50px' }}>
                <Box display="flex" flexDirection="column" alignItems="center">
                    <Card sx={{ width: 300, height: 300, display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '30px' }}>
                        <CardContent>
                            <Typography variant="h6">업로드 한 이미지</Typography>
                        </CardContent>
                    </Card>
                    <TableContainer component={Paper} sx={{ maxWidth: 400 }}>
                        <Table>
                            <TableBody>
                                <TableRow>
                                    <TableCell align="center">Time</TableCell>
                                    <TableCell align="center">Accuracy</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell align="center">00:07:15</TableCell>
                                    <TableCell align="center">83%</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Container>
        </>
    );
};

export default Result;
