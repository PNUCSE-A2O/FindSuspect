import React from 'react';
import { Routes, Route } from 'react-router-dom';
import AdminUpload from './pages/AdminUpload';
//import AdminLoading from './pages/AdminLoading';
import UserLoading from './pages/UserLoading';
import UserUpload from './pages/UserUpload';
import Result from './pages/Result';
import Landing from './pages/Landing';
import Home from './pages/Home';
import EnterPassword from './pages/EnterPassword';

const AppRoutes = () => {
    //<Route path="/adminLoading" element={<AdminLoading />} />
    return (
        <Routes>
            <Route path="/adminUpload" element={<AdminUpload />} />
            
            <Route path="/" element={<Landing />} />
            <Route path="/result" element={<Result />} />
            <Route path="/userLoading" element={<UserLoading />} />
            <Route path="/userUpload" element={<UserUpload />} />
            <Route path='/home' element={<Home/>} />
            <Route path='/password' element={<EnterPassword/>} />
        </Routes>
    );
};

export default AppRoutes;
