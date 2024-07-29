import './App.js';
import React from 'react';
import AdminUpload from "./pages/AdminUpload.js";
import Result from './pages/Result.js';
import Landing from './pages/Landing.js';
import {Routes, Route} from "react-router-dom";
import AdminLoading from './pages/AdminLoading.js';
import UserLoading from './pages/UserLoading.js';
import UserUpload from './pages/UserUpload.js';

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/adminUpload" element={<AdminUpload />} />
            <Route path="/adminLoading" element={<AdminLoading />} />
            <Route path="/landing" element={<Landing />} />
            <Route path="/result" element={<Result />} />
            <Route path="/userLoading" element={<UserLoading />} />
            <Route path="/userUpload" element={<UserUpload />} />
        </Routes>
    );
};

export default AppRoutes;