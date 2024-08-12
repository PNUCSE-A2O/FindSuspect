import React from 'react';
import { Routes, Route } from 'react-router-dom';
import AdminUpload from './pages/AdminUpload';
import AdminLoading from './pages/AdminLoading';
import UserLoading from './pages/UserLoading';
import UserUpload from './pages/UserUpload';
import Result from './pages/Result';
import Landing from './pages/Landing';

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/adminUpload" element={<AdminUpload />} />
            <Route path="/adminLoading" element={<AdminLoading />} />
            <Route path="/" element={<Landing />} />
            <Route path="/result" element={<Result />} />
            <Route path="/userLoading" element={<UserLoading />} />
            <Route path="/userUpload" element={<UserUpload />} />
        </Routes>
    );
};

export default AppRoutes;
