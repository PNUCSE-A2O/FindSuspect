import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [selectedImage, setSelectedImage] = useState(null);
  const [selectedVideo, setSelectedVideo] = useState(null);

  const handleImageChange = (event) => {
    setSelectedImage(event.target.files[0]);
  };

  const handleVideoChange = (event) => {
    setSelectedVideo(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append('imageFile', selectedImage);
    formData.append('videoFile', selectedVideo);

    axios.post('/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(response => {
      console.log('Upload success:', response.data);
    })
    .catch(error => {
      console.error('Upload error:', error);
    });
  };
  
  return (
    <div className="App">
      <h1>Upload Image and Video</h1>
      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <label htmlFor="imageFile">Image:</label>
        <input type="file" id="imageFile" name="imageFile" accept="image/*" onChange={handleImageChange} required /><br /><br />

        <label htmlFor="videoFile">Video:</label>
        <input type="file" id="videoFile" name="videoFile" accept="video/*" onChange={handleVideoChange} required /><br /><br />

        <button type="submit">Upload</button>
      </form>
    </div>
  );
}

export default App;
